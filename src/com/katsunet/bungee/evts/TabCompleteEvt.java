package com.katsunet.bungee.evts;

import com.katsunet.spkproxysuite.bungee.Main;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.TabCompleteEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class TabCompleteEvt implements Listener {

	private Main plugin;

	public TabCompleteEvt(Main plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onTabComplete(TabCompleteEvent ev) {
		String partialPlayerName = ev.getCursor().toLowerCase();

		int lastSpaceIndex = partialPlayerName.lastIndexOf(' ');
		if (lastSpaceIndex >= 0) {
			partialPlayerName = partialPlayerName.substring(lastSpaceIndex + 1);
		}

		for (ProxiedPlayer p : this.plugin.getProxy().getPlayers()) {
			if (p.getName().toLowerCase().startsWith(partialPlayerName)) {
				ev.getSuggestions().add(p.getName());
			}
		}
	}
}
