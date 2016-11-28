package com.katsunet.bungee.cmds.ping;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class PingCmd extends Command {

	public PingCmd() {
		super("ping");
	}

	public void execute(CommandSender sender, String[] args) {
		TextComponent answer = new TextComponent();
		answer.setBold(Boolean.valueOf(true));
		if (args.length == 0) {
			if ((sender instanceof ProxiedPlayer)) {
				ProxiedPlayer player = (ProxiedPlayer) sender;
				sender.sendMessage(new TextComponent("[§bPING§f] PING: "+responseColor(player.getPing()) + player.getPing() + " ms."));
			} else {
				sender.sendMessage(new TextComponent("[§bPING§f] Console-tan no tiene ping."));
			}
		} else {
			ProxiedPlayer player = ProxyServer.getInstance().getPlayer(args[0]);
			if (player != null) {
				sender.sendMessage(new TextComponent("[§bPING§f] PING de " + args[0] + ": " + responseColor(player.getPing()) + player.getPing() + " ms."));
			} else {
				sender.sendMessage(new TextComponent("[§bPING§f] PING de " + args[0] + ": Desconectado."));
			}
		}
	}

	private ChatColor responseColor(int ping) {
		if (ping < 370) {
			return ChatColor.GREEN;
		} else if (ping < 1100) {
			return ChatColor.YELLOW;
		} else {
			return ChatColor.RED;
		}
	}
}
