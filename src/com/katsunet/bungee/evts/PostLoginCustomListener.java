package com.katsunet.bungee.evts;

import com.katsunet.bungee.evts.custom.PostLoginCustomEvent;

import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PostLoginCustomListener implements Listener {

	@EventHandler
	public void onPostLoginCusEvt(PostLoginCustomEvent e) {
		if (e.getKickPlayer()) {
			e.getPlayer().disconnect(new TextComponent(e.getMsg()));
		} else {
			e.getPlayer().sendMessage(new TextComponent(e.getMsg()));
			if(e.getPlayerId() != 0) {
			  e.getPlugin().getPlayerList().get(e.getPlayer().getName()).setPlayerId(e.getPlayerId());
			}
		}
	}
}
