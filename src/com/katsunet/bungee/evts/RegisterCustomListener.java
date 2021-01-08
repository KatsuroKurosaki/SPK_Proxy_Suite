package com.katsunet.bungee.evts;

import com.katsunet.bungee.evts.custom.RegisterCustomEvent;
import com.katsunet.common.Global;
import com.katsunet.spkproxysuite.bungee.Main;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class RegisterCustomListener implements Listener {

	private Main plugin;

	public RegisterCustomListener(Main plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onRegisterEvent(RegisterCustomEvent e) {
		if (e.getSuccess()) {
			this.plugin.getPlayerList().get(e.getPlayer().getName()).setLoggedIn(true);
			this.plugin.getPlayerList().get(e.getPlayer().getName()).setPlayerId(e.getUserId());
			e.getPlayer().sendMessage(new TextComponent("Registered successfully! Sending you to the lobby..."));
			if (ProxyServer.getInstance().getServers().containsKey(this.plugin.getMainCnf().getYaml().getString(Global.CONFNODE_LOBBY_SERVER))) {
				e.getPlayer().connect(ProxyServer.getInstance().getServerInfo(this.plugin.getMainCnf().getYaml().getString(Global.CONFNODE_LOBBY_SERVER)));
			} else {
				e.getPlayer().sendMessage(new TextComponent("Error: Can't find the lobby, contact the admin."));
			}
		} else {
			e.getPlayer().sendMessage(new TextComponent("Error: " + e.getMsg()));
		}
	}

}
