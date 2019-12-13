package com.katsunet.bungee.motd;

import com.katsunet.common.Global;
import com.katsunet.spkproxysuite.bungee.Main;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

public class MotdCmd extends Command {

	//private Main plugin;
	
	public MotdCmd(Main plugin){
		super("gmotd",Global.PERM_MOTDPERMISSION_COMMAND);
		//this.plugin=plugin;
	}

	@Override
	public void execute(CommandSender arg0, String[] arg1) {
		if(arg1.length<2){
			this.info(arg0);
		} else {
			this.edit(arg0,arg1);
		}
	}
	
	private void info(CommandSender arg0){
		arg0.sendMessage(new TextComponent("**********************"));
		arg0.sendMessage(new TextComponent(" -=: GMotd :=-"));
		arg0.sendMessage(new TextComponent("/gmotd :: Muestra el MOTD actual"));
		arg0.sendMessage(new TextComponent("/gmotd <1/2> <motd> :: Cambia la linea 1 o 2 del MOTD"));
		arg0.sendMessage(new TextComponent("**********************"));
	}
	
	private void edit(CommandSender arg0, String[] arg1){
		if(arg1.length==2){
			arg0.sendMessage(new TextComponent("[§bGMotd§f] No has escrito el texto del MOTD."));
		} else {
			
		}
	}
}
