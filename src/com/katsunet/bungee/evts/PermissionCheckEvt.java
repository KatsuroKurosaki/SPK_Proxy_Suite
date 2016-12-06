package com.katsunet.bungee.evts;

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
		System.out.println("Permission check");
		System.out.println(e.toString());
		
	}

}
