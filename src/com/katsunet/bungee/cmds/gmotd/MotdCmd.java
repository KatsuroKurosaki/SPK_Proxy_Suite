package com.katsunet.bungee.cmds.gmotd;

import java.util.Arrays;

import com.katsunet.common.Global;
import com.katsunet.spkproxysuite.bungee.Main;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

public class MotdCmd extends Command {

	private Main plugin;

	public MotdCmd(Main plugin) {
		super("gmotd", Global.PERM_MOTDPERMISSION_COMMAND);
		this.plugin = plugin;
	}

	@Override
	public void execute(CommandSender sender, String[] arg1) {
		if (arg1.length == 0) {
			sender.sendMessage(new TextComponent(" -=: GMotd :=-"));
			sender.sendMessage(new TextComponent("/gmotd get :: Prints current MOTD."));
			sender.sendMessage(new TextComponent("/gmotd set <motd> :: Replaces MOTD with the one written."));
		} else {
			MotdManager.processMotd(sender, this.plugin, arg1[0], Arrays.copyOfRange(arg1, 1, arg1.length));
		}
	}
}
