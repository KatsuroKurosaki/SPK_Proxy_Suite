package com.katsunet.bungee.cmds.commandhider;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

public class CommandHider extends Command {
	
	private TextComponent errormsg;
	
	public CommandHider(){
		super("cmdhide", "", new String[]{
				"?",
				"about",
				"ehelp",
				"help",
				"pl",
				"plugins",
				"ver",
				"version",
				"minecraft:help",
				"minecraft:me",
				"bukkit:?",
				"bukkit:about",
				"bukkit:help",
				"bukkit:pl",
				"bukkit:plugins",
				"bukkit:ver",
				"bukkit:version"
			});
		this.errormsg = new TextComponent("Error 404, comando no encontrado.");
	}
	
	@Override
	public void execute(CommandSender arg0, String[] arg1) {
		arg0.sendMessage(this.errormsg);
	}
}
