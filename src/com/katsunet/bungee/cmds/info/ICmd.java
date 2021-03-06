package com.katsunet.bungee.cmds.info;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class ICmd extends Command {
	public ICmd() {
		super("i");
	}

	@Override
	public void execute(CommandSender arg0, String[] arg1) {
		if (!(arg0 instanceof ProxiedPlayer)) {
			if (arg1.length > 1) {
				ProxiedPlayer player = ProxyServer.getInstance().getPlayer(arg1[0]);
				if (player != null) {
					StringBuilder builder = new StringBuilder();
					for (int i = 1; i < arg1.length; i++) {
						builder.append(" " + arg1[i]);
					}
					player.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', builder.toString())));
					player.sendMessage(ChatMessageType.ACTION_BAR,new TextComponent(ChatColor.translateAlternateColorCodes('&', builder.toString())));
					builder = null;
					player = null;
				}
			}
		}
	}
}
