package com.katsunet.bungee.cmds.register;

import com.katsunet.bungee.async.RegisterAsync;
import com.katsunet.common.Global;
import com.katsunet.spkproxysuite.bungee.Main;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class RegisterCmd extends Command {

	private Main plugin;

	public RegisterCmd(Main plugin) {
		super("register");
		this.plugin = plugin;
	}

	@Override
	public void execute(CommandSender arg0, String[] arg1) {
		if (arg0 instanceof ProxiedPlayer) {
			if (this.plugin.getPlayerList().get(arg0.getName()).getIsLoggedIn()) {
				arg0.sendMessage(new TextComponent("You are already logged in."));
			} else if (arg1.length == 0) {
				arg0.sendMessage(new TextComponent("Not enough arguments. Type: /register <password> <your email>"));
			} else if (arg1.length == 1) {
				arg0.sendMessage(new TextComponent("You forgot to type an email address. Type: /register <password> <your email>"));
			} else if (!Global.emailValidate(arg1[1])) {
				arg0.sendMessage(new TextComponent("The provided email '" + arg1[1] + "' is not valid."));
			} else {
				arg0.sendMessage(new TextComponent("Registering account, please wait..."));
				this.plugin.getProxy().getScheduler().runAsync(
					this.plugin,
					new RegisterAsync(this.plugin, (ProxiedPlayer) arg0, arg1[0], arg1[1])
				);
			}
		} else {
			arg0.sendMessage(new TextComponent("Command not available on console."));
		}
	}
}
