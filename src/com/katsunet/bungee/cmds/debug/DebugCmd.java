package com.katsunet.bungee.cmds.debug;

import org.mindrot.jbcrypt.BCrypt;

import com.katsunet.common.Global;
import com.katsunet.spkproxysuite.bungee.Main;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

public class DebugCmd extends Command {

	private Main _plugin;
	
	public DebugCmd(Main plugin) {
		super("debug",Global.PERM_DEBUGGER);
		this._plugin=plugin;
	}

	public void execute(CommandSender sender, String[] args) {
		sender.sendMessage(new TextComponent(BCrypt.hashpw("testpw", BCrypt.gensalt())));
		sender.sendMessage(new TextComponent(this._plugin.getPlayerList().toString()));
	}
}
