package com.katsunet.spkproxysuite.bungee;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import com.katsunet.bungee.cmds.announcements.AnnouncementsCmd;
import com.katsunet.bungee.cmds.auth.ChangePwCmd;
import com.katsunet.bungee.cmds.auth.LoginCmd;
import com.katsunet.bungee.cmds.auth.RegisterCmd;
import com.katsunet.bungee.cmds.bgc.BgcCmd;
import com.katsunet.bungee.cmds.bpex.BpexCmd;
import com.katsunet.bungee.cmds.bserver.BserverCmd;
import com.katsunet.bungee.cmds.commandhider.CommandHider;
import com.katsunet.bungee.cmds.debug.DebugCmd;
import com.katsunet.bungee.cmds.gtitle.GtitleCmd;
import com.katsunet.bungee.cmds.help.HelpCmd;
import com.katsunet.bungee.cmds.helpop.HelpOpCmd;
import com.katsunet.bungee.cmds.helpop.ReplyOpCmd;
import com.katsunet.bungee.cmds.info.ICmd;
import com.katsunet.bungee.cmds.lobby.LobbyCmd;
import com.katsunet.bungee.cmds.ping.PingCmd;
import com.katsunet.bungee.cmds.playerinfo.PlayerinfoCmd;
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
import com.katsunet.bungee.evts.ProxyPingEvt;
import com.katsunet.bungee.evts.PlayerDisconnectEvt;
import com.katsunet.bungee.evts.PlayerinfoCustomEvt;
import com.katsunet.bungee.evts.PostLoginCustomEvt;
import com.katsunet.bungee.evts.RegisterCustomEvt;
import com.katsunet.bungee.evts.ServerKickEvt;
import com.katsunet.bungee.evts.TabCompleteEvt;
import com.katsunet.bungee.motd.MotdCmd;
import com.katsunet.bungee.scheduler.AnnouncementsSch;
import com.katsunet.bungee.scheduler.PlayerKickerSch;
import com.katsunet.classes.BungeeGroup;
import com.katsunet.classes.SpkPlayer;
import com.katsunet.common.BungeeYamlFile;
import com.katsunet.common.Global;
import com.katsunet.common.MySQLConn;
import com.katsunet.common.TextFile;

import net.md_5.bungee.api.plugin.Plugin;

public class Main extends Plugin {
	private BungeeYamlFile _bungeeCnf, _mainCnf, _dbCnf;
	private MySQLConn _mysql;
	private ConcurrentHashMap<String, SpkPlayer> _playerList;
	private ConcurrentHashMap<String, BungeeGroup> _bungeeGroups;
	private String _serverMotd;

	
	public BungeeYamlFile getMainCnf() {
		return this._mainCnf;
	}

	public BungeeYamlFile getBungeeCnf() {
		return this._bungeeCnf;
	}

	public MySQLConn getMysql() {
		return this._mysql;
	}
	
	public ConcurrentHashMap<String, SpkPlayer> getPlayerList() {
		return this._playerList;
	}
	
	public String getServerMotd() {
		return this._serverMotd;
	}

	public ConcurrentHashMap<String, BungeeGroup> getBungeeGroups() {
		return this._bungeeGroups;
	}

	@Override
	public void onEnable() {
		// Database
		this._dbCnf = new BungeeYamlFile(this, Global.DATABASE_CONFIG_FILE);
		this._mysql = new MySQLConn(
			this._dbCnf.getYaml().getString(Global.CONFNODE_DB_DRIVER),
			this._dbCnf.getYaml().getString(Global.CONFNODE_DB_CONN),
			this._dbCnf.getYaml().getString(Global.CONFNODE_DB_USER),
			this._dbCnf.getYaml().getString(Global.CONFNODE_DB_PASS)
		);
		if (this._mysql.connect(false)) {
			this.getLogger().info("MySQL test connection worked.");
			this._mysql.disconnect();
		} else {
			this.getLogger().severe("MySQL test connection failed.");
		}

		// BungeeCord config file
		this._bungeeCnf = new BungeeYamlFile(this, Global.BUNGEECORD_CONFIG_FILE);

		// Proxy Suite config file
		this._mainCnf = new BungeeYamlFile(this, Global.PLUGIN_CONFIG_FILE);
		
		// MOTD text file
		TextFile motd = new TextFile(this, Global.MOTD_CONFIG_FILE);
		this._serverMotd=motd.readTextFile();
		System.out.println("MOTD:\n"+this._serverMotd);
		motd=null;

		// Variable values
		this._playerList = new ConcurrentHashMap<String, SpkPlayer>();
		//this._noRecvList = this._mainCnf.getYaml().getStringList(Global.CONFNODE_NORCVLST);
		//this._chatspyList = this._mainCnf.getYaml().getStringList(Global.CONFNODE_CHATSPYLST);
		//this._bungeeGroups = new ConcurrentHashMap<String, BungeeGroup>();
		/*for (String item : this._bungeeCnf.getYaml().getSection(Global.CONFNODE_GROUPS).getKeys()) {
			BungeeGroup bg = new BungeeGroup(item);
			this._bungeeGroups.put(item, bg);
			for (String perm : this._bungeeCnf.getYaml().getStringList(Global.CONFNODE_GROUPS + "." + item)) {
				bg.addPermission(perm);
			}
			bg = null;
		}*/

		// Debug Command
		this.getProxy().getPluginManager().registerCommand(this, new DebugCmd(this));
		
		// Blocked commands
		//this.getProxy().getPluginManager().registerCommand(this, new CommandHider(this));

		// Ping Commands
		this.getProxy().getPluginManager().registerCommand(this, new PingCmd());
		// TODO: Alert players with more than 1000ms ping every X minutes
		// TODO: Configure ping colors via config file
		// TODO: Pinglist: view ping of everybody

		// Information command with trick
		//this.getProxy().getPluginManager().registerCommand(this, new ICmd());

		// Staff members online
		//this.getProxy().getPluginManager().registerCommand(this, new StaffCmd());

		// Memory management and information
		//this.getProxy().getPluginManager().registerCommand(this, new BgcCmd());

		// Back to lobby command
		//this.getProxy().getPluginManager().registerCommand(this, new LobbyCmd(this));

		// Announcements admin
		//this.getProxy().getPluginManager().registerCommand(this, new AnnouncementsCmd(this));

		// Gtitle: Send global titles and subtitles.
		//this.getProxy().getPluginManager().registerCommand(this, new GtitleCmd(this));

		// Help topics
		//this.getProxy().getPluginManager().registerCommand(this, new HelpCmd(this));

		// Playerinfo
		//this.getProxy().getPluginManager().registerCommand(this, new PlayerinfoCmd(this));

		// Server MOTD
		this.getProxy().getPluginManager().registerCommand(this, new MotdCmd(this));

		// Permissions
		//this.getProxy().getPluginManager().registerCommand(this, new BpexCmd(this));

		// Spigot servers management on the fly
		//this.getProxy().getPluginManager().registerCommand(this, new BserverCmd(this));

		// HelpOp commands
		//this.getProxy().getPluginManager().registerCommand(this, new HelpOpCmd(this));
		//this.getProxy().getPluginManager().registerCommand(this, new ReplyOpCmd(this));

		// Private message Commands
		//this.getProxy().getPluginManager().registerCommand(this, new MessageCmd(this));
		//this.getProxy().getPluginManager().registerCommand(this, new ReplyCmd(this));
		//this.getProxy().getPluginManager().registerCommand(this, new ChatspyCmd(this));
		//this.getProxy().getPluginManager().registerCommand(this, new MsgtoggleCmd(this));

		// Auth Commands
		this.getProxy().getPluginManager().registerCommand(this, new RegisterCmd(this));
		this.getProxy().getPluginManager().registerCommand(this, new LoginCmd(this));
		this.getProxy().getPluginManager().registerCommand(this, new ChangePwCmd(this));

		// Events
		this.getProxy().getPluginManager().registerListener(this, new ChatEvt(this));
		//this.getProxy().getPluginManager().registerListener(this, new TabCompleteEvt(this));
		this.getProxy().getPluginManager().registerListener(this, new PostLoginEvt(this));
		//this.getProxy().getPluginManager().registerListener(this, new PlayerDisconnectEvt(this));
		this.getProxy().getPluginManager().registerListener(this, new ProxyPingEvt(this));
		//this.getProxy().getPluginManager().registerListener(this, new PermissionCheckEvt(this));
		//this.getProxy().getPluginManager().registerListener(this, new ServerKickEvt(this));

		// Custom Events
		//this.getProxy().getPluginManager().registerListener(this, new PostLoginCustomEvt());
		this.getProxy().getPluginManager().registerListener(this, new RegisterCustomEvt(this));
		//this.getProxy().getPluginManager().registerListener(this, new LoginCustomEvt(this));
		//this.getProxy().getPluginManager().registerListener(this, new ChangePasswordCustomEvt());
		//this.getProxy().getPluginManager().registerListener(this, new PlayerinfoCustomEvt(this));
		// Idea: Change tab header and footer.
		// Idea: Add scoreboards, use Spigot-Bungee communication channel to get extra
		// info.

		// Scheduled repeating tasks
		/*this.getProxy().getScheduler().schedule(this, new AnnouncementsSch(this),
				this._mainCnf.getYaml().getInt(Global.CONFNODE_ANN_DELAY),
				_mainCnf.getYaml().getInt(Global.CONFNODE_ANN_DELAY), TimeUnit.SECONDS);*/
		/*this.getProxy().getScheduler().schedule(this, new PlayerKickerSch(this),
				this._mainCnf.getYaml().getInt(Global.CONFNODE_LOGIN_GRACE_TIME),
				this._mainCnf.getYaml().getInt(Global.CONFNODE_LOGIN_GRACE_TIME), TimeUnit.SECONDS);*/

		// Complete!
		this.getLogger().info("SPK Proxy Suite started.");
	}

}
