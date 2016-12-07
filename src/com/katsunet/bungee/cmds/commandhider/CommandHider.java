package com.katsunet.bungee.cmds.commandhider;

import com.katsunet.common.Global;
import com.katsunet.spkproxysuite.bungee.Main;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

public class CommandHider extends Command {
	
	private TextComponent _errormsg;
	
	public CommandHider(Main plugin){
		super("commandhider", "", plugin.getMainCnf().getYaml().getStringList(Global.CONFNODE_CMDHIDE_CMDLIST).stream().toArray(String[]::new));
		this._errormsg = new TextComponent(plugin.getMainCnf().getYaml().getString(Global.CONFNODE_CMDHIDE_UNKNOWN));
	}
	
	@Override
	public void execute(CommandSender arg0, String[] arg1) {
		arg0.sendMessage(this._errormsg);
	}
}
