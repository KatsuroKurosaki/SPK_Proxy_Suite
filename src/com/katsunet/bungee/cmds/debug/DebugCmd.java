package com.katsunet.bungee.cmds.debug;

import org.mindrot.jbcrypt.BCrypt;

import com.katsunet.common.Global;
import com.katsunet.spkproxysuite.bungee.Main;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class DebugCmd extends Command {

	private Main _plugin;

	public DebugCmd(Main plugin) {
		super("debug", Global.PERM_DEBUGGER);
		this._plugin = plugin;
	}

	public void execute(CommandSender sender, String[] args) {
		if (sender instanceof ProxiedPlayer) {
			ProxiedPlayer p = (ProxiedPlayer) sender;
			System.out.println(Global.extractIpAddress(p.getSocketAddress().toString()));
		}
		sender.sendMessage(new TextComponent(BCrypt.hashpw("testpw", BCrypt.gensalt())));
		/*
		 * if (BCrypt.checkpw("user input", "hashed")) System.out.println("It matches");
		 * else System.out.println("It does not match"); sender.sendMessage(new
		 * TextComponent(this._plugin.getPlayerList().toString()));
		 */
	}
}
