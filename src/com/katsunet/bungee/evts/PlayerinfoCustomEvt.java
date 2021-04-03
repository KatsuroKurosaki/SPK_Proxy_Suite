package com.katsunet.bungee.evts;

import java.time.LocalTime;

import com.katsunet.bungee.cmds.ping.PingCmd;
import com.katsunet.bungee.evts.custom.PlayerinfoCustomEvent;
import com.katsunet.common.Global;
import com.katsunet.spkproxysuite.bungee.Main;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PlayerinfoCustomEvt implements Listener {
	private Main _plugin;
	
	public PlayerinfoCustomEvt(Main plugin){
		this._plugin=plugin;
	}
	
	@EventHandler
	public void onLogin(PlayerinfoCustomEvent e){
	  if(e.getSuccess()){
			ProxiedPlayer player = ProxyServer.getInstance().getPlayer(e.getPlayername());
			e.getSender().sendMessage(new TextComponent("***** PLAYER INFO "+e.getPlayername()+" *****"));
			e.getSender().sendMessage(new TextComponent("Player UUID: "+e.getPlayerUuid()));
			if (player != null) {
				e.getSender().sendMessage(new TextComponent("Status: "+ChatColor.GREEN+"ONLINE "+ChatColor.RESET+"("+LocalTime.MIN.plusSeconds( Global.getCurrentTimeSeconds() - this._plugin.getPlayerList().get(e.getPlayername()).getConnectTime() )+")"));
				e.getSender().sendMessage(new TextComponent("Version: "+Global.getMinecraftVersion(this._plugin.getPlayerList().get(e.getPlayername()).getMcVersion())));
				e.getSender().sendMessage(new TextComponent("Server: "+player.getServer().getInfo().getName()));
				e.getSender().sendMessage(new TextComponent("Ping: "+PingCmd.responseColor(player.getPing()) + player.getPing() + " ms"));
				e.getSender().sendMessage(new TextComponent("IP: "+this._plugin.getPlayerList().get(e.getPlayername()).getIpAddress()));
			} else {
				e.getSender().sendMessage(new TextComponent("Status: "+ChatColor.RED+"OFFLINE"));
				e.getSender().sendMessage(new TextComponent("Last login: "+e.getLastLogin()+" (UTC)"));
				e.getSender().sendMessage(new TextComponent("Last IP: "+e.getLastIp()));
			}
			e.getSender().sendMessage(new TextComponent("Connections: "+e.getConnections()));
			e.getSender().sendMessage(new TextComponent("Register date: "+e.getRegisterDate()+" (UTC)"));
			e.getSender().sendMessage(new TextComponent("***** PLAYER INFO "+e.getPlayername()+" *****"));
			player=null;
		} else {
			e.getSender().sendMessage(new TextComponent("[PlayerInfo] "+e.getMessage()));
		}
	}
}
