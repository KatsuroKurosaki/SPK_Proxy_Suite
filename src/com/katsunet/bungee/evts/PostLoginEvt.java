package com.katsunet.bungee.evts;

import com.katsunet.bungee.async.PostLoginAsync;
import com.katsunet.classes.SpkPlayer;
import com.katsunet.spkproxysuite.bungee.Main;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
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
		event.getPlayer().resetTabHeader();
		event.getPlayer().setTabHeader(new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', "&2¡BIENVENIDO!")).create(),new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', "&4Usa /register o /login para empezar.")).create());
		this._plugin.getPlayerList().put(event.getPlayer().getName(),new SpkPlayer(event.getPlayer().getPendingConnection().getVersion(),event.getPlayer().getAddress().getAddress().getHostAddress()));
		this._plugin.getProxy().getScheduler().runAsync(this._plugin, new PostLoginAsync(this._plugin,event.getPlayer()));
	}
}
