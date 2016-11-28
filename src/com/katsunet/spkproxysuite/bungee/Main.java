package com.katsunet.spkproxysuite.bungee;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import com.katsunet.bungee.cmds.announcements.AnnouncementsCmd;
import com.katsunet.bungee.cmds.auth.ChangePwCmd;
import com.katsunet.bungee.cmds.auth.LoginCmd;
import com.katsunet.bungee.cmds.auth.RegisterCmd;
import com.katsunet.bungee.cmds.bgc.BgcCmd;
import com.katsunet.bungee.cmds.commandhider.CommandHider;
import com.katsunet.bungee.cmds.debug.DebugCmd;
import com.katsunet.bungee.cmds.helpop.HelpOpCmd;
import com.katsunet.bungee.cmds.helpop.ReplyOpCmd;
import com.katsunet.bungee.cmds.info.ICmd;
import com.katsunet.bungee.cmds.lobby.LobbyCmd;
import com.katsunet.bungee.cmds.ping.PingCmd;
import com.katsunet.bungee.cmds.privatemessage.ChatspyCmd;
import com.katsunet.bungee.cmds.privatemessage.MessageCmd;
import com.katsunet.bungee.cmds.privatemessage.MsgtoggleCmd;
import com.katsunet.bungee.cmds.privatemessage.ReplyCmd;
import com.katsunet.bungee.cmds.staff.StaffCmd;
import com.katsunet.bungee.evts.ChangePwEvt;
import com.katsunet.bungee.evts.ChatEvt;
import com.katsunet.bungee.evts.LoginEvt;
import com.katsunet.bungee.evts.PlayerConnectEvt;
import com.katsunet.bungee.evts.PlayerDisconnectEvt;
import com.katsunet.bungee.evts.PostLoginEvt;
import com.katsunet.bungee.evts.RegisterEvt;
import com.katsunet.bungee.evts.TabEvt;
import com.katsunet.bungee.scheduler.AnnouncementsSch;
import com.katsunet.classes.SpkPlayer;
import com.katsunet.common.BungeeYamlFile;
import com.katsunet.common.Global;
import com.katsunet.common.MySQLConn;

import net.md_5.bungee.api.plugin.Plugin;

public class Main extends Plugin {
	private BungeeYamlFile _cf;
	private MySQLConn _mysql;
	private List<String> _noRecvList;
	private List<String> _chatspyList;
	private ConcurrentHashMap<String, SpkPlayer> _playerList;
	
	@Deprecated
	public BungeeYamlFile getCf(){
		return this._cf;
	}
	
	public MySQLConn getMysql(){
		return this._mysql;
	}
	
	public List<String> getNoRcvList(){
		return this._noRecvList;
	}
	
	public List<String> getChatspyList(){
		return this._chatspyList;
	}
	
	public ConcurrentHashMap<String, SpkPlayer> getPlayerList(){
		return this._playerList;
	}
	
	@Override
	public void onEnable() {
		// Config file
		this._cf = new BungeeYamlFile(this,Global.MAIN_CONFIG_FILE);
		if(this._cf.getYaml().getInt(Global.CONFNODE_VERSION) != Global.MAIN_CONFIG_VERSION){
			this.getLogger().info("Detected old config file; overwriting to current.");
			this._cf.updateYamlFile(Global.MAIN_CONFIG_FILE);
		}
		
		// Variable values
		this._playerList = new ConcurrentHashMap<String, SpkPlayer>();
		this._noRecvList = this._cf.getYaml().getStringList(Global.CONFNODE_NORCVLST);
		this._chatspyList = this._cf.getYaml().getStringList(Global.CONFNODE_CHATSPYLST);
		
		// Database
		this._mysql = new MySQLConn(this._cf.getYaml().getString(Global.CONFNODE_DB_DRIVER), this._cf.getYaml().getString(Global.CONFNODE_DB_CONN), this._cf.getYaml().getString(Global.CONFNODE_DB_USER), this._cf.getYaml().getString(Global.CONFNODE_DB_PASS));
		if(this._mysql.connect(true)){
			this.getLogger().info("MySQL test connection worked.");
			this._mysql.disconnect();
		} else {
			this.getLogger().severe("Error while connecting to MySQL.");
		}
		
		// Debug Commands
		this.getProxy().getPluginManager().registerCommand(this, new DebugCmd(this));
		
		// Blocked commands
		this.getProxy().getPluginManager().registerCommand(this, new CommandHider());
		
		// Ping Commands
		this.getProxy().getPluginManager().registerCommand(this, new PingCmd());
		
		// Information command with trick
		this.getProxy().getPluginManager().registerCommand(this, new ICmd());
		
		// Staff members online
		this.getProxy().getPluginManager().registerCommand(this, new StaffCmd());
		
		// Memory management and information
		this.getProxy().getPluginManager().registerCommand(this, new BgcCmd());
		
		// Back to lobby command
		this.getProxy().getPluginManager().registerCommand(this, new LobbyCmd(this));
		
		// Announcements admin
		this.getProxy().getPluginManager().registerCommand(this, new AnnouncementsCmd(this));
		
		// HelpOp commands
		this.getProxy().getPluginManager().registerCommand(this, new HelpOpCmd(this));
		this.getProxy().getPluginManager().registerCommand(this, new ReplyOpCmd(this));
		
		// Private message Commands
		this.getProxy().getPluginManager().registerCommand(this, new MessageCmd(this));
		this.getProxy().getPluginManager().registerCommand(this, new ReplyCmd(this));
		this.getProxy().getPluginManager().registerCommand(this, new ChatspyCmd(this));
		this.getProxy().getPluginManager().registerCommand(this, new MsgtoggleCmd(this));
		
		// Auth Commands
		this.getProxy().getPluginManager().registerCommand(this, new RegisterCmd(this));
		this.getProxy().getPluginManager().registerCommand(this, new LoginCmd(this));
		this.getProxy().getPluginManager().registerCommand(this, new ChangePwCmd(this));
		
		// Events
		this.getProxy().getPluginManager().registerListener(this, new ChatEvt(this));
		this.getProxy().getPluginManager().registerListener(this, new TabEvt(this));
		this.getProxy().getPluginManager().registerListener(this, new PlayerConnectEvt(this));
		this.getProxy().getPluginManager().registerListener(this, new PlayerDisconnectEvt(this));
		// ProxyPingEvent OR PlayerHandshakeEvent
		// PermissionCheckEvent
		
		// Custom Events
		this.getProxy().getPluginManager().registerListener(this, new PostLoginEvt());
		this.getProxy().getPluginManager().registerListener(this, new RegisterEvt(this));
		this.getProxy().getPluginManager().registerListener(this, new LoginEvt(this));
		this.getProxy().getPluginManager().registerListener(this, new ChangePwEvt());
		
		// Scheduled repeating tasks
		this.getProxy().getScheduler().schedule(this, new AnnouncementsSch(this), this._cf.getYaml().getInt(Global.CONFNODE_ANN_DELAY), _cf.getYaml().getInt(Global.CONFNODE_ANN_DELAY),TimeUnit.SECONDS);
		
		// Complete!
		this.getLogger().info("SPK Proxy Suite started.");
	}
	
	@Override
	public void onDisable(){
		this.getLogger().info("SPK Proxy Suite stopped.");
	}
}
