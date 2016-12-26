package com.katsunet.bungee.evts;

import com.katsunet.classes.BungeeGroup;
import com.katsunet.spkproxysuite.bungee.Main;

import net.md_5.bungee.api.event.PermissionCheckEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PermissionCheckEvt implements Listener {
	
	private Main _plugin;
	
	public PermissionCheckEvt(Main plugin){
		this._plugin=plugin;
	}
	
	@EventHandler
	public void onPermissionCheckEvent(PermissionCheckEvent e){
		boolean hasPermission = false;
		for (String group : this._plugin.getPlayerList().get(e.getSender().getName()).getBungeeGroups()){
			BungeeGroup bg = this._plugin.getBungeeGroups().get(group);
			if(!hasPermission){
				hasPermission = bg.permissionExists(e.getPermission());
			}
			bg = null;
		}
		e.setHasPermission(hasPermission);
	}

}
