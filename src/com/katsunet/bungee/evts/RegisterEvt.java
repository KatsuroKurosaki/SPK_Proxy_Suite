package com.katsunet.bungee.evts;

import com.katsunet.bungee.evts.custom.RegisterCusEvt;
import com.katsunet.common.Global;
import com.katsunet.spkproxysuite.bungee.Main;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class RegisterEvt implements Listener {
	
	private Main plugin;
	
	public RegisterEvt(Main plugin){
		this.plugin=plugin;
	}
	
	@EventHandler
	public void onRegisterEvent(RegisterCusEvt e){
		if(e.getSuccess()){
			this.plugin.getPlayerList().get(e.getPlayer().getName()).setLoggedIn(true);
			e.getPlayer().sendMessage(new TextComponent("[§bAuth§f] Te has registrado correctamente! Entrando al lobby..."));
			if (ProxyServer.getInstance().getServers().containsKey(this.plugin.getCf().getYaml().getString(Global.CONFNODE_LOBBY_SERVER))) {
				e.getPlayer().connect(ProxyServer.getInstance().getServerInfo(this.plugin.getCf().getYaml().getString(Global.CONFNODE_LOBBY_SERVER)));
			} else {
				e.getPlayer().sendMessage(new TextComponent("[§bAuth§f] Error: No puedo encontrar el lobby. Contacta al Admin."));
			}
		} else {
			e.getPlayer().sendMessage(new TextComponent("[§bAuth§f] Error al registrarte: "+e.getMsg()));
		}
	}

}
