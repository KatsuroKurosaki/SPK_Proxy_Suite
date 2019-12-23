package com.katsunet.spigot.cmds.gc;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class GcCmd implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] arg3) {
		/*
		 * if (sender instanceof Player) { Player player = (Player) sender; if(
		 * player.hasPermission("spkproxysuite.gc") || player.isOp()) { } } else {
		 * sender.sendMessage("Console"); }
		 */

		this.gcCall(sender);
		return true;
	}

	private void gcCall(CommandSender sender) {
		int freeMemory = (int) (Runtime.getRuntime().freeMemory() / 1000000L);
		int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1000000L);
		sender.sendMessage("Uso de RAM:");
		sender.sendMessage("RAM libre: " + freeMemory + " MB.");
		sender.sendMessage("RAM maxima: " + maxMemory + " MB.");
		sender.sendMessage("Uso de RAM actual: " + (maxMemory - freeMemory) + " MB.");
	}

}
