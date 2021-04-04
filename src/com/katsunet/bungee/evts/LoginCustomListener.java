package com.katsunet.bungee.evts;

import com.katsunet.bungee.evts.custom.LoginCustomEvent;
import com.katsunet.common.Global;
import com.katsunet.spkproxysuite.bungee.Main;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class LoginCustomListener implements Listener {
	private Main _plugin;

	public LoginCustomListener(Main plugin) {
		this._plugin = plugin;
	}

	@EventHandler
	public void onLogin(LoginCustomEvent e) {
		if (e.getSuccess()) {
			this._plugin.getPlayerList().get(e.getPlayer().getName()).setLoggedIn(true);
			this._plugin.getPlayerList().get(e.getPlayer().getName()).setChatspyEnable(e.getChatspyEnable());
			this._plugin.getPlayerList().get(e.getPlayer().getName()).setMsgDisable(e.getMsgDisable());
			e.getPlayer().sendMessage(new TextComponent("Logged-in successfully. Redirecting to the lobby..."));
			if (ProxyServer.getInstance().getServers().containsKey(this._plugin.getMainCnf().getYaml().getString(Global.CONFNODE_LOBBY_SERVER))) {
				e.getPlayer().connect(ProxyServer.getInstance().getServerInfo(this._plugin.getMainCnf().getYaml().getString(Global.CONFNODE_LOBBY_SERVER)));
			} else {
				e.getPlayer().sendMessage(new TextComponent("Error: Lobby server not found. Contact with the admin"));
			}
		} else {
			if (this._plugin.getMainCnf().getYaml().getInt(Global.CONFNODE_LOGIN_ATTEMPS) > this._plugin.getPlayerList().get(e.getPlayer().getName()).getLoginAttemps()) {
				e.getPlayer().sendMessage(new TextComponent("Error logging-in: " + e.getMsg()));
			} else {
				e.getPlayer().disconnect(new TextComponent("You have reached the maximum login attempts."));
			}
		}
	}
}
