package com.katsunet.bungee.cmds.gtitle_wip;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.Title;

public class GtitleCmd extends Command {

	public GtitleCmd(){
		super("gtitle");
	}

	@Override
	public void execute(CommandSender arg0, String[] arg1) {
		
		Title t = ProxyServer.getInstance().createTitle()
				.title(new ComponentBuilder("Oh dios se me pone la verga")
				.color(ChatColor.GOLD).create())
				.subTitle(new ComponentBuilder("asi de pequeña :v")
				.color(ChatColor.YELLOW).create())
				.fadeIn(20)
				.stay(100)
				.fadeOut(20);
		
		t.send((ProxiedPlayer) arg0);
		
		ProxiedPlayer p = (ProxiedPlayer) arg0;
		p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("[§4S.§6P.§2K.§f] Avisa al staff directamente de los hackers y bugs con /helpop o /report"));
		//p.sendMessage(ChatMessageType.CHAT, new TextComponent("CHAT"));
		//p.sendMessage(ChatMessageType.SYSTEM, new TextComponent("SYSTEM"));
		/*ProxyServer.getInstance().createTitle()
		.title(new ComponentBuilder("Oh dios se me pone la verga")
		.color(ChatColor.GOLD).create())
		.subTitle(new ComponentBuilder("asi de pequeña :v")
		.color(ChatColor.YELLOW).create())
		.fadeIn(20)
		.stay(100)
		.fadeOut(20)
		.send((ProxiedPlayer) arg0);*/
	}
}
