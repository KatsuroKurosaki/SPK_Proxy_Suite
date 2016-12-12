package com.katsunet.bungee.evts;

import com.katsunet.spkproxysuite.bungee.Main;

import net.md_5.bungee.api.event.PermissionCheckEvent;
import net.md_5.bungee.api.event.ServerKickEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ServerKickEvt implements Listener {
	
	private Main _plugin;
	
	public ServerKickEvt(Main plugin){
		this._plugin=plugin;
	}
	
	@EventHandler
	public void onServerKickEvent(ServerKickEvent e){
		System.out.println("Server kick");
		System.out.println(e.toString());
		
	}

}
