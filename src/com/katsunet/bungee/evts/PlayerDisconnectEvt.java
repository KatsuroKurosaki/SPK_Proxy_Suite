package com.katsunet.bungee.evts;

import com.katsunet.bungee.async.PlayerDisconnectAsync;
import com.katsunet.spkproxysuite.bungee.Main;

import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PlayerDisconnectEvt implements Listener {

	private Main _plugin;

	public PlayerDisconnectEvt(Main plugin) {
		this._plugin = plugin;
	}

	@EventHandler
	public void onPlayerDisconnectEvent(PlayerDisconnectEvent event) {
		if (this._plugin.getPlayerList().containsKey(event.getPlayer().getName())) {
			if (this._plugin.getPlayerList().get(event.getPlayer().getName()).getIsLoggedIn()) {
				this._plugin.getProxy().getScheduler().runAsync(
					this._plugin,
					new PlayerDisconnectAsync(
						this._plugin,
						this._plugin.getPlayerList().get(event.getPlayer().getName())
					)
				);
			}
			this._plugin.getPlayerList().remove(event.getPlayer().getName());
		}
	}
}
