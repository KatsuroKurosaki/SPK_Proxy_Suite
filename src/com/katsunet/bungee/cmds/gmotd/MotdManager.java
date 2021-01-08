package com.katsunet.bungee.cmds.gmotd;

import com.katsunet.common.Global;
import com.katsunet.common.TextFile;
import com.katsunet.spkproxysuite.bungee.Main;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;

public class MotdManager {

	public static void processMotd(CommandSender sender, Main plugin, String operation, String[] args) {
		if (operation.equals("get")) {
			sender.sendMessage(new TextComponent("Current RAW MOTD:\n" + plugin.getServerMotd()));
			sender.sendMessage(new TextComponent("Current formatted MOTD:\n" + ChatColor.translateAlternateColorCodes('&', plugin.getServerMotd())));
		} else if (operation.equals("set")) {
			if (args.length > 0) {
				// System.getProperty("line.separator")
				plugin.setServerMotd(String.join(" ", args).replaceAll("\\\\n", "\n"));

				TextFile motd = new TextFile(plugin, Global.MOTD_CONFIG_FILE);
				motd.writeTextFile(plugin.getServerMotd());
				motd = null;

				sender.sendMessage(new TextComponent("Server MOTD replaced successfully:"));
				sender.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', plugin.getServerMotd())));
			} else {
				sender.sendMessage(new TextComponent("MOTD can't be empty."));
			}
		} else {
			sender.sendMessage(new TextComponent("Specified operation is not valid: " + args[0] + "."));
		}
	}
}
