package com.katsunet.bungee.cmds.bgc;

import com.katsunet.common.Global;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

public class BgcCmd extends Command {

	public BgcCmd() {
		super("bgc", Global.PERM_BGCVIEW);
	}

	@Override
	public void execute(CommandSender arg0, String[] arg1) {
		if (arg1.length == 0) {
			this.gcCall(arg0);
		} else {
			if (arg1[0].equals("free")) {
				if (arg0.hasPermission(Global.PERM_BGCFREE)) {
					this.gcCall(arg0);
					arg0.sendMessage(new TextComponent("After RAM cleanup"));
					System.gc();
					this.gcCall(arg0);
				} else {
					arg0.sendMessage(new TextComponent("No permission to clear server RAM."));
				}
			} else {
				this.gcCall(arg0);
			}
		}
	}

	private void gcCall(CommandSender sender) {
		int freeMemory = (int) (Runtime.getRuntime().freeMemory() / 1000000L);
		int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1000000L);
		sender.sendMessage(new TextComponent("RAM usage:"));
		sender.sendMessage(new TextComponent("Free: " + freeMemory + " MB."));
		sender.sendMessage(new TextComponent("Maximum: " + maxMemory + " MB."));
		sender.sendMessage(new TextComponent("Current: " + (maxMemory - freeMemory) + " MB."));
	}
}
