package com.katsunet.bungee.evts;

import com.katsunet.spkproxysuite.bungee.Main;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ProxyPingEvt implements Listener {

	private Main _plugin;

	public ProxyPingEvt(Main plugin) {
		this._plugin = plugin;
	}

	@EventHandler
	public void onProxyPingEvent(ProxyPingEvent e) {
		e.getResponse().setDescriptionComponent(
			new TextComponent(
				ChatColor.translateAlternateColorCodes('&', this._plugin.getServerMotd())
			)
		);
	}

}
