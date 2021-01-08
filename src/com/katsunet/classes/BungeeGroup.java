package com.katsunet.classes;

import java.util.ArrayList;

public class BungeeGroup {
	private ArrayList<String> _permissionList;
	private String _groupName;

	public BungeeGroup(String groupname) {
		this._permissionList = new ArrayList<String>();
		this._groupName = groupname;
	}

	public void addPermission(String permission) {
		this._permissionList.add(permission);
	}

	public void delPermission(String permission) {
		this._permissionList.remove(permission);
	}

	public String getGroupName() {
		return this._groupName;
	}

	public boolean permissionExists(String perm) {
		return this._permissionList.contains(perm);
	}

	@Override
	public String toString() {
		return "Name: " + this._groupName + " Permission list: " + this._permissionList.toString();
	}
}
