package com.katsunet.bungee.evts.custom;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Event;

public class PlayerinfoCustomEvent extends Event {

	private boolean success;
	private String message, playername, playeruuid, registerdate, lastlogin, lastip;
	private int connections;
	private CommandSender cmds;

	public PlayerinfoCustomEvent(boolean success, CommandSender cmds, String message) {
		this.success = success;
		this.message = message;
		this.cmds = cmds;
	}

	public PlayerinfoCustomEvent(boolean success, CommandSender cmds, String playername, String playeruuid,
	    int connections, String registerdate, String lastlogin, String lastIp) {
		this.success = success;
		this.cmds = cmds;
		this.playername = playername;
		this.playeruuid = playeruuid;
		this.connections = connections;
		this.registerdate = registerdate;
		this.lastlogin = lastlogin;
		this.lastip = lastIp;
	}

	public boolean getSuccess() {
		return this.success;
	}

	public String getMessage() {
		return this.message;
	}

	public String getPlayername() {
		return this.playername;
	}

	public CommandSender getSender() {
		return this.cmds;
	}

	public String getPlayerUuid() {
		return this.playeruuid;
	}

	public String getRegisterDate() {
		return this.registerdate;
	}

	public String getLastLogin() {
		return this.lastlogin;
	}

	public String getLastIp() {
		return this.lastip;
	}

	public int getConnections() {
		return this.connections;
	}
}
