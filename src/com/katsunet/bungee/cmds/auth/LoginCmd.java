package com.katsunet.bungee.cmds.auth;

import com.katsunet.bungee.async.LoginAsync;
import com.katsunet.spkproxysuite.bungee.Main;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class LoginCmd extends Command{
	private Main plugin;
	
	public LoginCmd(Main plugin){
		super("login");
		this.plugin = plugin;
	}

	@Override
	public void execute(CommandSender arg0, String[] arg1) {
		if(arg0 instanceof ProxiedPlayer){
			if(this.plugin.getPlayerList().get(arg0.getName()).getIsLoggedIn()){
				arg0.sendMessage(new TextComponent("[§bAuth§f] Ya estas conectado, no es necesareo el comando."));
			} else if(arg1.length==0){
				arg0.sendMessage(new TextComponent("[§bAuth§f] Formato incorrecto. Usa: /login <contraseña>"));
			} else {
				arg0.sendMessage(new TextComponent("[§bAuth§f] Conectando a tu cuenta, un momento..."));
				ProxiedPlayer p = (ProxiedPlayer) arg0;
				this.plugin.getProxy().getScheduler().runAsync(this.plugin, new LoginAsync(this.plugin,p,arg1[0]));
				p=null;
			}
		} else {
			arg0.sendMessage(new TextComponent("Console-tan no puede hacer login."));
		}
	}
}
