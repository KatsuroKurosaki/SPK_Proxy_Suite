package com.katsunet.classes;

import java.util.ArrayList;

import com.katsunet.common.Global;

public class SpkPlayer {

	private int _playerId;
	private int _connectTime;
	private int _mcversion;
	private int _lastMessageTime;
	private String _lastMsgSentFrom;
	private int _lastHelpopMsgTime;
	private boolean _isLoggedIn;
	private int _loginAttempts;
	private String _ipaddress;
	private ArrayList<String> _bungeeGroupMember;

	public int getPlayerId() {
		return this._playerId;
	}

	public void setPlayerId(int playerId) {
		this._playerId = playerId;
	}

	public int getConnectTime() {
		return this._connectTime;
	}

	public int getMcVersion() {
		return this._mcversion;
	}

	public int getLastMessageTime() {
		return _lastMessageTime;
	}

	public void setLastMsgNow() {
		this._lastMessageTime = Global.getCurrentTimeSeconds();
	}

	public String getLastMsgSentFrom() {
		return this._lastMsgSentFrom;
	}

	public void setLastMsgSentFrom(String name) {
		this._lastMsgSentFrom = name;
	}

	public int getLastHelpopMsgTime() {
		return this._lastHelpopMsgTime;
	}

	public void setLastHelpOpTime(int time) {
		this._lastHelpopMsgTime = time;
	}

	public void setLastHelpOpNow() {
		this._lastHelpopMsgTime = Global.getCurrentTimeSeconds();
	}

	public boolean getIsLoggedIn() {
		return this._isLoggedIn;
	}

	public void setLoggedIn(boolean value) {
		this._isLoggedIn = value;
	}

	public int getLoginAttemps() {
		return this._loginAttempts;
	}

	public void increaseLoginAttempt() {
		this._loginAttempts++;
	}

	public String getIpAddress() {
		return this._ipaddress;
	}

	public void addBungeeGroup(String group) {
		this._bungeeGroupMember.add(group);
	}

	public void removeBungeeGroup(String group) {
		this._bungeeGroupMember.remove(group);
	}

	public ArrayList<String> getBungeeGroups() {
		return this._bungeeGroupMember;
	}

	public SpkPlayer(int mcversion, String ipaddress) {
		this._connectTime = Global.getCurrentTimeSeconds();
		this._mcversion = mcversion;
		this._lastMessageTime = 0;
		this._lastHelpopMsgTime = 0;
		this._isLoggedIn = false;
		this._loginAttempts = 0;
		this._ipaddress = ipaddress;
		this._bungeeGroupMember = new ArrayList<String>();
		this._bungeeGroupMember.add(Global.BUNGEE_DEFAULT_GROUP_NAME);
	}

	@Override
	public String toString() {
		return	" _playerId: " + this._playerId +
				" _connectTime: " + this._connectTime +
				" _mcversion: " + this._mcversion +
				" _lastMessageTime:" + this._lastMessageTime +
				" _lastMsgSentFrom: " + this._lastMsgSentFrom +
				" _lastHelpopMsgTime: " + this._lastHelpopMsgTime +
				" _isLoggedIn: " + this._isLoggedIn +
				" _loginAttempts: " + this._loginAttempts + 
				" _ipaddress: " + this._ipaddress +
				" _bungeeGroupMember: " + this._bungeeGroupMember;
	}
}
