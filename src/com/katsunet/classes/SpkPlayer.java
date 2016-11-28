package com.katsunet.classes;

import com.katsunet.common.Global;

public class SpkPlayer {

	private int _connectTime;
	private int _mcversion;
	private int _lastMessageTime;
	private String _lastMsgSentFrom;
	private int _lastHelpopMsgTime;
	private boolean _isLoggedIn;
	private String _ipaddress;
	

	public int getConnectTime(){
		return this._connectTime;
	}
	
	public int getLastMessageTime() {
		return _lastMessageTime;
	}
	
	public int getMcVersion(){
		return this._mcversion;
	}
	
	public String getLastMsgSentTo(){
		return this._lastMsgSentFrom;
	}
	
	public int getLastHelpopMsgTime(){
		return this._lastHelpopMsgTime;
	}
	
	public boolean getIsLoggedIn(){
		return this._isLoggedIn;
	}
	
	public String getIpAddress(){
		return this._ipaddress;
	}
	

	public void setLastMsgNow() {
		this._lastMessageTime = Global.getCurrentTimeSeconds();
	}
	
	public void setLastMsgSentFrom(String name){
		this._lastMsgSentFrom=name;
	}
	
	public void setLastHelpOpNow(){
		this._lastHelpopMsgTime = Global.getCurrentTimeSeconds();
	}
	
	public void setLastHelpOpTime(int time){
		this._lastHelpopMsgTime = time;
	}
	
	public void setLoggedIn(boolean value){
		this._isLoggedIn=value;
	}
	
	public SpkPlayer(int mcversion, String ipaddress){
		this._mcversion=mcversion;
		this._lastMessageTime = Global.getCurrentTimeSeconds();
		this._lastHelpopMsgTime = Global.getCurrentTimeSeconds();
		this._connectTime = Global.getCurrentTimeSeconds();
		this._isLoggedIn=false;
		this._ipaddress=ipaddress;
	}
	
	@Override
	public String toString(){
		return "{"
			+ "connectTime: "+this._connectTime+", "
			+ "mcversion: "+this._mcversion+", "
			+ "lastMessageTime:"+this._lastMessageTime+", "
			+ "lastMsgSentFrom: "+this._lastMsgSentFrom+", "
			+ "lastHelpOpMsgTime: "+this._lastHelpopMsgTime+", "
			+ "isLoggedIn: "+this._isLoggedIn+", "
			+ "ipaddress: "+this._ipaddress+" "
		+ "}";
	}
}
