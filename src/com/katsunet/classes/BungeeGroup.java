package com.katsunet.classes;

import java.util.ArrayList;

public class BungeeGroup {
	private ArrayList<String> _permissionList;
	private String _groupName;
	
	public BungeeGroup(String groupname){
		this._permissionList = new ArrayList<String>();
		this._groupName = groupname;
	}
	
	public void addPermission(String permission){
		this._permissionList.add(permission);
	}
	
	public String getGroupName(){
		return this._groupName;
	}
	
	public boolean permissionExists(String perm){
		boolean exists = false;
		for (String permission : this._permissionList){
			if(perm.equals(permission)){
				exists=true;
			}
		}
		return exists;
	}
	
	@Override
	public String toString(){
		return "Name: "+this._groupName+" Permission list: "+this._permissionList.toString();
	}
}
