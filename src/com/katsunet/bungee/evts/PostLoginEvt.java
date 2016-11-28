package com.katsunet.bungee.evts;

import com.katsunet.bungee.evts.custom.PostLoginCusEvt;

import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PostLoginEvt implements Listener{
	
	@EventHandler
	public void onPostLoginCusEvt(PostLoginCusEvt e){
		e.getPlayer().sendMessage(new TextComponent("[§bAuth§f] "+e.getMsg()));
	}
}
