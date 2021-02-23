package com.katsunet.bungee.scheduler;

import com.katsunet.classes.SpkPlayer;
import com.katsunet.common.Global;
import com.katsunet.spkproxysuite.bungee.Main;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;

public class PlayerKickerSch implements Runnable {

	private Main plugin;

	public PlayerKickerSch(Main plugin) {
		this.plugin = plugin;
	}

	@Override
	public void run() {
		for (String playername : this.plugin.getPlayerList().keySet()) {
			SpkPlayer spk = this.plugin.getPlayerList().get(playername);
			if (!spk.getIsLoggedIn() && Global.getCurrentTimeSeconds() > spk.getConnectTime() + this.plugin.getMainCnf().getYaml().getInt(Global.CONFNODE_LOGIN_GRACE_TIME)) {
				ProxyServer.getInstance().getPlayer(playername).disconnect(
					new TextComponent(
						"You did not sign-in in less than " + this.plugin.getMainCnf().getYaml().getInt(Global.CONFNODE_LOGIN_GRACE_TIME) + " seconds."
					)
				);
			}
		}
	}
}
