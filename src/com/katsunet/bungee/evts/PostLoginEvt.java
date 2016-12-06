package com.katsunet.bungee.evts;

import com.katsunet.bungee.async.PostLoginAsync;
import com.katsunet.classes.SpkPlayer;
import com.katsunet.spkproxysuite.bungee.Main;

import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PostLoginEvt implements Listener {
private Main _plugin;
	
	public PostLoginEvt(Main plugin){
		this._plugin=plugin;
	}

	@EventHandler
	public void onPostLoginEvent(PostLoginEvent event) {
		//this._plugin.getPlayerList().put(event.getPlayer().getName(),new SpkPlayer(ProtocolSupportAPI.getProtocolVersion(event.getPlayer()).getId(),event.getPlayer().getAddress().getAddress().getHostAddress()));
		this._plugin.getPlayerList().put(event.getPlayer().getName(),new SpkPlayer(event.getPlayer().getPendingConnection().getVersion(),event.getPlayer().getAddress().getAddress().getHostAddress()));
		this._plugin.getProxy().getScheduler().runAsync(this._plugin, new PostLoginAsync(this._plugin,event.getPlayer()));
		//event.getPlayer().resetTabHeader();
        //event.getPlayer().setTabHeader(new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', "&5HEADER HERE")).create(),new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', "&4FOOTER HERE")).create());
	}
}
