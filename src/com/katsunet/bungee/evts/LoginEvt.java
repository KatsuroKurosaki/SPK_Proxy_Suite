package com.katsunet.bungee.evts;

import com.katsunet.bungee.evts.custom.LoginCusEvt;
import com.katsunet.common.Global;
import com.katsunet.spkproxysuite.bungee.Main;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class LoginEvt implements Listener {
	private Main _plugin;
	
	public LoginEvt(Main plugin){
		this._plugin=plugin;
	}
	
	@EventHandler
	public void onLogin(LoginCusEvt e){
		if(e.getSuccess()){
			this._plugin.getPlayerList().get(e.getPlayer().getName()).setLoggedIn(true);
			e.getPlayer().sendMessage(new TextComponent("[§bAuth§f] Te has conectado correctamente! Entrando al lobby..."));
			if (ProxyServer.getInstance().getServers().containsKey(this._plugin.getCf().getYaml().getString(Global.CONFNODE_LOBBY_SERVER))) {
				e.getPlayer().connect(ProxyServer.getInstance().getServerInfo(this._plugin.getCf().getYaml().getString(Global.CONFNODE_LOBBY_SERVER)));
			} else {
				e.getPlayer().sendMessage(new TextComponent("[§bAuth§f] Error: No puedo encontrar el lobby. Contacta al Admin."));
			}
		} else {
			e.getPlayer().sendMessage(new TextComponent("[§bAuth§f] Error al conectarte: "+e.getMsg()));
		}
	}
}
