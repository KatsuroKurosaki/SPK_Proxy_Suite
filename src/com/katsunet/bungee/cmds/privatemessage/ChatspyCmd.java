package com.katsunet.bungee.cmds.privatemessage;

import com.katsunet.common.Global;
import com.katsunet.spkproxysuite.bungee.Main;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class ChatspyCmd extends Command {

	private Main plugin;
	
	public ChatspyCmd(Main plugin) {
		super("chatspy", Global.PERM_CHATSPY);
		this.plugin=plugin;
	}

	@Override
	public void execute(CommandSender arg0, String[] arg1) {
		if(arg0 instanceof ProxiedPlayer){
			if(!this.plugin.getChatspyList().contains(arg0.getName())){
				this.plugin.getChatspyList().add(arg0.getName());
				arg0.sendMessage(new TextComponent("[§bSpy§f] Has activado el ChatSpy"));
			} else {
				this.plugin.getChatspyList().remove(arg0.getName());
				arg0.sendMessage(new TextComponent("[§bSpy§f] Has desactivado el ChatSpy"));
			}
			this.plugin.getCf().getYaml().set(Global.CONFNODE_CHATSPYLST, this.plugin.getChatspyList());
			this.plugin.getCf().saveYamlFile();
		} else {
			arg0.sendMessage(new TextComponent("Console-tan no puede espiar mensajes privados."));
		}
	}
}
