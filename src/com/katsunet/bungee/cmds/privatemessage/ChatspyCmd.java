package com.katsunet.bungee.cmds.privatemessage;

import com.katsunet.bungee.async.MessagingAsync;
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
		this.plugin = plugin;
	}

	@Override
	public void execute(CommandSender arg0, String[] arg1) {
		if (arg0 instanceof ProxiedPlayer) {
			if (!this.plugin.getPlayerList().get(arg0.getName()).getChatspyEnable()) {
				this.plugin.getPlayerList().get(arg0.getName()).setChatspyEnable(true);
				arg0.sendMessage(new TextComponent("ChatSpy has been enabled."));
				this.plugin.getProxy().getScheduler().runAsync(
						this.plugin,
						new MessagingAsync(
							this.plugin,
							this.plugin.getPlayerList().get(arg0.getName()),
							MessagingAsync.CHATSPY
						)
					);
			} else {
				this.plugin.getPlayerList().get(arg0.getName()).setChatspyEnable(false);
				arg0.sendMessage(new TextComponent("ChatSpy has been disabled."));
				this.plugin.getProxy().getScheduler().runAsync(
						this.plugin,
						new MessagingAsync(
							this.plugin,
							this.plugin.getPlayerList().get(arg0.getName()),
							MessagingAsync.CHATSPY
						)
					);
			}
		}
	}
}
