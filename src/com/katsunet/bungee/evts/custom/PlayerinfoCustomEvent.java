package com.katsunet.bungee.evts.custom;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Event;

public class PlayerinfoCustomEvent extends Event {

	private boolean success;
	private String message, playername, email, rank, rankuntil, registerdate,lastlogin, lastip;
	private int connections;
	private CommandSender cmds;
	
	public PlayerinfoCustomEvent(boolean success, String message, CommandSender cmds){
		this.success=success;
		this.message=message;
		this.cmds=cmds;
	}
	
	public PlayerinfoCustomEvent(boolean success, CommandSender cmds, String playername,
			String email, String rank, String rankuntil,
			int connections,String registerdate,String lastlogin,
			String lastIp){
		this.success=success;
		this.cmds=cmds;
		this.playername=playername;
		this.email=email;
		this.rank=rank;
		this.rankuntil = rankuntil;
		this.connections=connections;
		this.registerdate=registerdate;
		this.lastlogin=lastlogin;
		this.lastip=lastIp;
	}
	
	public boolean getSuccess(){
		return this.success;
	}
	
	public String getMessage(){
		return this.message;
	}
	
	public String getPlayername(){
		return this.playername;
	}
	
	public CommandSender getSender(){
		return this.cmds;
	}
	
	public String getEmail(){
		return this.email;
	}
	
	public String getRankUntil(){
		return this.rankuntil;
	}
	
	public String getRegisterDate(){
		return this.registerdate;
	}
	
	public String getLastLogin(){
		return this.lastlogin;
	}
	
	public String getRank(){
		return this.rank;
	}
	
	public String getLastIp(){
		return this.lastip;
	}
	
	public int getConnections(){
		return this.connections;
	}
}
