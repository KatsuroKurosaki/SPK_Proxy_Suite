package com.katsunet.bungee.evts.custom;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Event;

public class LoginCustomEvent extends Event {

	private boolean success;
	private ProxiedPlayer player;
	private String msg;
	private boolean msgDisable;
	private boolean chatspyEnable;

	public LoginCustomEvent(boolean success, ProxiedPlayer player, String msg) {
		this.success = success;
		this.player = player;
		this.msg = msg;
	}

	public LoginCustomEvent(boolean success, ProxiedPlayer player, boolean msgDisable, boolean chatspyEnable) {
		this.success = success;
		this.player = player;
		this.msgDisable = msgDisable;
		this.chatspyEnable = chatspyEnable;
	}

	public boolean getSuccess() {
		return this.success;
	}

	public ProxiedPlayer getPlayer() {
		return this.player;
	}

	public String getMsg() {
		return this.msg;
	}
	
	public boolean getMsgDisable() {
		return this.msgDisable;
	}
	
	public boolean getChatspyEnable() {
		return this.chatspyEnable;
	}
}
