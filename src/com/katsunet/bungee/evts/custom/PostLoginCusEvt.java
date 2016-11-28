package com.katsunet.bungee.evts.custom;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Event;

public class PostLoginCusEvt extends Event{
	
	private ProxiedPlayer player;
	private String msg;
	
	public PostLoginCusEvt(ProxiedPlayer player, String msg){
		this.player = player;
		this.msg=msg;
	}
	
	public ProxiedPlayer getPlayer(){
		return this.player;
	}
	
	public String getMsg(){
		return this.msg;
	}

}
