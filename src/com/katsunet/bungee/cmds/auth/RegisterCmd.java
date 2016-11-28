package com.katsunet.bungee.cmds.auth;

import com.katsunet.bungee.async.RegisterAsync;
import com.katsunet.common.Global;
import com.katsunet.spkproxysuite.bungee.Main;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class RegisterCmd extends Command{

	private Main plugin;
	
	public RegisterCmd(Main plugin){
		super("register");
		this.plugin = plugin;
	}

	@Override
	public void execute(CommandSender arg0, String[] arg1) {
		if(arg0 instanceof ProxiedPlayer){
			if(this.plugin.getPlayerList().get(arg0.getName()).getIsLoggedIn()){
				arg0.sendMessage(new TextComponent("[§bAuth§f] Ya estas registrado, no es necesareo el comando."));
			} else if(arg1.length==0){
				arg0.sendMessage(new TextComponent("[§bAuth§f] Formato incorrecto. Usa: /register <contraseña> <email>"));
			} else if (arg1.length==1){
				arg0.sendMessage(new TextComponent("[§bAuth§f] Te olvidaste de poner un e-mail de recuperación. Usa: /register <contraseña> <email>"));
			} else if(!Global.emailValidate(arg1[1])) {
				arg0.sendMessage(new TextComponent("[§bAuth§f] El email escrito '"+arg1[1]+"' no es válido."));
			} else {
				arg0.sendMessage(new TextComponent("[§bAuth§f] Registrando su cuenta, un momento..."));
				ProxiedPlayer p = (ProxiedPlayer) arg0;
				this.plugin.getProxy().getScheduler().runAsync(this.plugin, new RegisterAsync(this.plugin,p, arg1[0], arg1[1]));
				p=null;
			}
		} else {
			arg0.sendMessage(new TextComponent("Console-tan no puede registrarse."));
		}
	}
}
