package com.katsunet.bungee.cmds.auth;

import com.katsunet.bungee.async.ChangePwAsync;
import com.katsunet.spkproxysuite.bungee.Main;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class ChangePwCmd extends Command{
	private Main plugin;
	
	public ChangePwCmd(Main plugin){
		super("changepw");
		this.plugin = plugin;
	}

	@Override
	public void execute(CommandSender arg0, String[] arg1) {
		if(arg0 instanceof ProxiedPlayer){
			if(arg1.length==0 || arg1.length==1){
				arg0.sendMessage(new TextComponent("[§bAuth§f] Formato incorrecto. Usa: /changepw <contraseña anterior> <contraseña nueva>"));
			} else {
				arg0.sendMessage(new TextComponent("[§bAuth§f] Cambiando contraseña, un momento..."));
				ProxiedPlayer p = (ProxiedPlayer) arg0;
				this.plugin.getProxy().getScheduler().runAsync(this.plugin, new ChangePwAsync(this.plugin,p, arg1[0], arg1[1]));
				p=null;
			}
		} else {
			arg0.sendMessage(new TextComponent("Console-tan no puede cambiar su contraseña."));
		}
	}
}
