package com.katsunet.bungee.cmds.login;

import com.katsunet.bungee.async.LoginAsync;
import com.katsunet.spkproxysuite.bungee.Main;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class LoginCmd extends Command {
	private Main plugin;

	public LoginCmd(Main plugin) {
		super("login");
		this.plugin = plugin;
	}

	@Override
	public void execute(CommandSender arg0, String[] arg1) {
		if (arg0 instanceof ProxiedPlayer) {
			if (this.plugin.getPlayerList().get(arg0.getName()).getIsLoggedIn()) {
				arg0.sendMessage(new TextComponent("You are already logged in."));
			} else if (arg1.length == 0) {
				arg0.sendMessage(new TextComponent("Not enough arguments. Type: /login <your password>"));
			} else {
				arg0.sendMessage(new TextComponent("Authenticating, please wait..."));
				this.plugin.getProxy().getScheduler().runAsync(
					this.plugin,
					new LoginAsync(
						this.plugin,
						(ProxiedPlayer) arg0,
						arg1[0]
					)
				);
			}
		}
	}
}
