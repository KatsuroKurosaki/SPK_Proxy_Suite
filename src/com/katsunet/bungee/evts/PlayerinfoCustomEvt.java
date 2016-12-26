package com.katsunet.bungee.evts;

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
			e.getSender().sendMessage(new TextComponent("§b***** §fDATOS DE "+e.getPlayername()+" §b*****"));
			e.getSender().sendMessage(new TextComponent("§bE-mail: §f"+e.getEmail()));
			e.getSender().sendMessage(new TextComponent("§bRango: §f"+e.getRank()));
			e.getSender().sendMessage(new TextComponent("§bCaducidad rango: §f"+e.getRankUntil()+" (GMT-0)"));
			if (player != null) {
				int timeonline = Global.getCurrentTimeSeconds() - this._plugin.getPlayerList().get(e.getPlayername()).getConnectTime();
				e.getSender().sendMessage(new TextComponent("§bEstado: "+ChatColor.GREEN+"ONLINE §f("+Global.formatSeconds(timeonline)+")"));
				e.getSender().sendMessage(new TextComponent("§bVersión: §f"+Global.getMinecraftVersion(this._plugin.getPlayerList().get(e.getPlayername()).getMcVersion())));
				e.getSender().sendMessage(new TextComponent("§bModalidad: §f"+player.getServer().getInfo().getName()));
				e.getSender().sendMessage(new TextComponent("§bPING: "+PingCmd.responseColor(player.getPing()) + player.getPing() + " ms"));
			} else {
				e.getSender().sendMessage(new TextComponent("§bEstado: "+ChatColor.RED+"OFFLINE"));
				e.getSender().sendMessage(new TextComponent("§bVersión: §fN/A"));
				e.getSender().sendMessage(new TextComponent("§bModalidad: §fN/A"));
				e.getSender().sendMessage(new TextComponent("§bÚltima conexión: §f"+e.getLastLogin()+" (GMT-0)"));
			}
			e.getSender().sendMessage(new TextComponent("§bConexiones: §f"+e.getConnections()));
			e.getSender().sendMessage(new TextComponent("§bFecha registro: §f"+e.getRegisterDate()));
			e.getSender().sendMessage(new TextComponent("§b***** §fDATOS DE "+e.getPlayername()+" §b*****"));
			player=null;
		} else {
			e.getSender().sendMessage(new TextComponent("[§bPlayerInfo§f] "+e.getMessage()));
		}
	}
}
