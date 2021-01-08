package com.katsunet.bungee.evts.custom;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Event;

public class RegisterEvent extends Event {

	private boolean success;
	private ProxiedPlayer player;
	private String msg;
	private int userId;

	public RegisterEvent(boolean success, ProxiedPlayer player, String msg) {
		this.success = success;
		this.player = player;
		this.msg = msg;
	}

	public RegisterEvent(boolean success, ProxiedPlayer player, int userId) {
		this.success = success;
		this.player = player;
		this.userId = userId;
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

	public int getUserId() {
		return this.userId;
	}

}
