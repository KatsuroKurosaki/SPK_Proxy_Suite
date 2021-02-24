package com.katsunet.bungee.cmds.privatemessage;

import com.katsunet.bungee.async.MessagingAsync;
import com.katsunet.common.Global;
import com.katsunet.spkproxysuite.bungee.Main;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class MsgtoggleCmd extends Command {

	private Main _plugin;

	public MsgtoggleCmd(Main plugin) {
		super("msgtoggle", Global.PERM_MSGTOGGLE);
		this._plugin = plugin;
	}

	@Override
	public void execute(CommandSender arg0, String[] arg1) {
		if (arg0 instanceof ProxiedPlayer) {
			if (this._plugin.getPlayerList().get(arg0.getName()).getMsgDisable()) {
				this._plugin.getPlayerList().get(arg0.getName()).setMsgDisable(false);
				arg0.sendMessage(new TextComponent("Private messages enabled. You can chat with everybody."));
				this._plugin.getProxy().getScheduler().runAsync(
					this._plugin,
					new MessagingAsync(
						this._plugin,
						this._plugin.getPlayerList().get(arg0.getName()),
						MessagingAsync.MSGTOGGLE
					)
				);
			} else {
				this._plugin.getPlayerList().get(arg0.getName()).setMsgDisable(true);
				arg0.sendMessage(new TextComponent("Private messages disabled. You can only chat with staff members."));
				this._plugin.getProxy().getScheduler().runAsync(
						this._plugin,
						new MessagingAsync(
							this._plugin,
							this._plugin.getPlayerList().get(arg0.getName()),
							MessagingAsync.MSGTOGGLE
						)
					);
			}
		}
	}
}
