package com.katsunet.spkproxysuite.bungee;

import java.io.File;
import java.io.IOException;
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
import com.katsunet.bungee.cmds.gtitle_wip.GtitleCmd;
import com.katsunet.bungee.cmds.helpop.HelpOpCmd;
import com.katsunet.bungee.cmds.helpop.ReplyOpCmd;
import com.katsunet.bungee.cmds.info.ICmd;
import com.katsunet.bungee.cmds.ping.PingCmd;
import com.katsunet.bungee.cmds.privatemessage.ChatspyCmd;
import com.katsunet.bungee.cmds.privatemessage.MessageCmd;
import com.katsunet.bungee.cmds.privatemessage.MsgtoggleCmd;
import com.katsunet.bungee.cmds.privatemessage.ReplyCmd;
import com.katsunet.bungee.cmds.staff.StaffCmd;
import com.katsunet.bungee.evts.ChangePasswordCustomEvt;
import com.katsunet.bungee.evts.ChatEvt;
import com.katsunet.bungee.evts.LoginCustomEvt;
import com.katsunet.bungee.evts.PermissionCheckEvt;
import com.katsunet.bungee.evts.PostLoginEvt;
import com.katsunet.bungee.evts.PlayerDisconnectEvt;
import com.katsunet.bungee.evts.PostLoginCustomEvt;
import com.katsunet.bungee.evts.ProxyPingEvt;
import com.katsunet.bungee.evts.RegisterCustomEvt;
import com.katsunet.bungee.evts.TabCompleteEvt;
import com.katsunet.bungee.scheduler.AnnouncementsSch;
import com.katsunet.bungee.scheduler.PlayerKickerSch;
import com.katsunet.classes.SpkPlayer;
import com.katsunet.common.BungeeYamlFile;
import com.katsunet.common.GeoIP;
import com.katsunet.common.Global;
import com.katsunet.common.MySQLConn;
import com.maxmind.geoip.LookupService;

import net.md_5.bungee.api.plugin.Plugin;

public class Main extends Plugin {
	private BungeeYamlFile _bungeeCnf, _mainCnf, _dbCnf;
	private MySQLConn _mysql;
	private List<String> _noRecvList;
	private List<String> _chatspyList;
	private ConcurrentHashMap<String, SpkPlayer> _playerList;
	private LookupService _lookupService;
	
	public BungeeYamlFile getMainCnf(){
		return this._mainCnf;
	}
	
	public BungeeYamlFile getBungeeCnf(){
		return this._bungeeCnf;
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
	
	public LookupService getLookupService(){
		return this._lookupService;
	}
	
	@Override
	public void onEnable() {
		// BungeeCord config file
		this._bungeeCnf = new BungeeYamlFile(this,Global.BUNGEE_MAIN_CONFIG_FILE);
		
		// Proxy Suite config file
		this._mainCnf = new BungeeYamlFile(this,Global.BUNGEE_MAIN_PLUGIN_CONFIG_FILE);
		if(this._mainCnf.getYaml().getInt(Global.CONFNODE_VERSION) != Global.BUNGEE_MAIN_CONFIG_VERSION){
			this.getLogger().info("Detected old "+Global.BUNGEE_MAIN_PLUGIN_CONFIG_FILE+" config file; overwriting with current.");
			this._mainCnf.updateYamlFile(Global.BUNGEE_MAIN_PLUGIN_CONFIG_FILE);
		}
		
		// Database
		this._dbCnf = new BungeeYamlFile(this,Global.DATABASE_CONFIG_FILE);
		if(this._dbCnf.getYaml().getInt(Global.CONFNODE_VERSION) != Global.DATABASE_CONFIG_VERSION){
			this.getLogger().info("Detected old "+Global.DATABASE_CONFIG_FILE+" config file; overwriting with current.");
			this._dbCnf.updateYamlFile(Global.DATABASE_CONFIG_FILE);
		}
		this._mysql = new MySQLConn(this._dbCnf.getYaml().getString(Global.CONFNODE_DB_DRIVER), this._dbCnf.getYaml().getString(Global.CONFNODE_DB_CONN), this._dbCnf.getYaml().getString(Global.CONFNODE_DB_USER), this._dbCnf.getYaml().getString(Global.CONFNODE_DB_PASS));
		if(this._mysql.connect(true)){
			this.getLogger().info("MySQL test connection worked.");
			this._mysql.disconnect();
		} else {
			this.getLogger().severe("Error while connecting to MySQL.");
		}
		
		// GeoIP Data file
		GeoIP.bungeeProcess(this);
		try {
			this._lookupService = new LookupService(this.getDataFolder().getPath()+File.separator+Global.GEOIP_DATABASE_FILE,LookupService.GEOIP_MEMORY_CACHE);
		} catch (IOException e) {
			this.getLogger().warning("Error loading "+Global.GEOIP_DATABASE_FILE+" file.");
			e.printStackTrace();
		}
		
		// Variable values
		this._playerList = new ConcurrentHashMap<String, SpkPlayer>();
		this._noRecvList = this._mainCnf.getYaml().getStringList(Global.CONFNODE_NORCVLST);
		this._chatspyList = this._mainCnf.getYaml().getStringList(Global.CONFNODE_CHATSPYLST);
		
		
		
		// Debug Commands
		this.getProxy().getPluginManager().registerCommand(this, new DebugCmd(this));
		
		// Blocked commands
		this.getProxy().getPluginManager().registerCommand(this, new CommandHider(this));
		
		// Ping Commands
		this.getProxy().getPluginManager().registerCommand(this, new PingCmd());
		
		// Information command with trick
		this.getProxy().getPluginManager().registerCommand(this, new ICmd());
		
		// Staff members online
		this.getProxy().getPluginManager().registerCommand(this, new StaffCmd());
		
		// Memory management and information
		this.getProxy().getPluginManager().registerCommand(this, new BgcCmd());
		
		// Back to lobby command
		//this.getProxy().getPluginManager().registerCommand(this, new LobbyCmd(this));
		
		// Announcements admin
		this.getProxy().getPluginManager().registerCommand(this, new AnnouncementsCmd(this));
		
		// Help topics
		//register command
		
		// Gtitle: Send global titles and subtitles.
		this.getProxy().getPluginManager().registerCommand(this, new GtitleCmd());
		
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
		this.getProxy().getPluginManager().registerListener(this, new TabCompleteEvt(this));
		this.getProxy().getPluginManager().registerListener(this, new PostLoginEvt(this));
		this.getProxy().getPluginManager().registerListener(this, new PlayerDisconnectEvt(this));
		this.getProxy().getPluginManager().registerListener(this, new ProxyPingEvt(this));
		this.getProxy().getPluginManager().registerListener(this, new PermissionCheckEvt(this));
		
		// Custom Events
		this.getProxy().getPluginManager().registerListener(this, new PostLoginCustomEvt());
		this.getProxy().getPluginManager().registerListener(this, new RegisterCustomEvt(this));
		this.getProxy().getPluginManager().registerListener(this, new LoginCustomEvt(this));
		this.getProxy().getPluginManager().registerListener(this, new ChangePasswordCustomEvt());
		//idea: Change tab header and footer.
		
		// Scheduled repeating tasks
		this.getProxy().getScheduler().schedule(this, new AnnouncementsSch(this), this._mainCnf.getYaml().getInt(Global.CONFNODE_ANN_DELAY), _mainCnf.getYaml().getInt(Global.CONFNODE_ANN_DELAY),TimeUnit.SECONDS);
		this.getProxy().getScheduler().schedule(this, new PlayerKickerSch(this), 60, 60, TimeUnit.SECONDS);
		
		// Complete!
		this.getLogger().info("SPK Proxy Suite started.");
	}
	
	@Override
	public void onDisable(){
		this.getLogger().info("SPK Proxy Suite stopped.");
	}
}
