package com.katsunet.bungee.cmds.lobby;

import com.katsunet.common.Global;
import com.katsunet.spkproxysuite.bungee.Main;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class LobbyCmd extends Command{

	private Main plugin;
	
	public LobbyCmd(Main plugin){
		super(plugin.getCf().getYaml().getString(Global.CONFNODE_LOBBY_COMMAND));
		this.plugin = plugin;
	}
	
	@Override
	public void execute(CommandSender arg0, String[] arg1) {
		if(arg0 instanceof ProxiedPlayer)	{
			ProxiedPlayer p = (ProxiedPlayer)arg0;
			if (!p.getServer().getInfo().getName().equals(this.plugin.getCf().getYaml().getString(Global.CONFNODE_LOBBY_SERVER))){
				if (ProxyServer.getInstance().getServers().containsKey(this.plugin.getCf().getYaml().getString(Global.CONFNODE_LOBBY_SERVER))) {
					p.connect(ProxyServer.getInstance().getServerInfo(this.plugin.getCf().getYaml().getString(Global.CONFNODE_LOBBY_SERVER)));
				} else {
					p.sendMessage(new TextComponent("[§bLobby§f] Error: No puedo encontrar el lobby. Contacta al Admin."));
				}
			} else {
				p.sendMessage(new TextComponent("[§bLobby§f] Ya estás conectado al lobby."));
			}
		} else {
			arg0.sendMessage(new TextComponent("Console-tan no puede ir al lobby."));
		}
	}

}
