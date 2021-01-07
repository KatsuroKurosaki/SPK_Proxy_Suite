package com.katsunet.bungee.evts;

import com.katsunet.spkproxysuite.bungee.Main;

import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

public class ChatEvt implements Listener {

	private Main plugin;

	public ChatEvt(Main plugin) {
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onChatEvent(ChatEvent event) {
		ProxiedPlayer p = (ProxiedPlayer) event.getSender();
		String cmd = event.getMessage().split(" ")[0];
		if (!this.plugin.getPlayerList().get(p.getName()).getIsLoggedIn() && !cmd.equalsIgnoreCase("/login")
				&& !cmd.equalsIgnoreCase("/register")) {
			event.setCancelled(true);
			p.sendMessage(new TextComponent("You can't chat or use commands yet. Type /register or /login to begin."));
		}
		cmd = null;
		p = null;
	}
}
