package com.katsunet.bungee.evts.custom;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Event;

public class PostLoginCusEvt extends Event{
	
	private ProxiedPlayer player;
	private String msg;
	private boolean _kickPlayer;
	
	public PostLoginCusEvt(ProxiedPlayer player, String msg, boolean kickPlayer){
		this.player = player;
		this.msg=msg;
		this._kickPlayer=kickPlayer;
	}
	
	public ProxiedPlayer getPlayer(){
		return this.player;
	}
	
	public String getMsg(){
		return this.msg;
	}
	
	public boolean getKickPlayer(){
		return this._kickPlayer;
	}

}
