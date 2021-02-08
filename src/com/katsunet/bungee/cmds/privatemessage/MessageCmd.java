package com.katsunet.bungee.cmds.privatemessage;

import com.katsunet.classes.SpkPlayer;
import com.katsunet.common.Global;
import com.katsunet.spkproxysuite.bungee.Main;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class MessageCmd extends Command {

	private Main plugin;

	public MessageCmd(Main plugin) {
		super("message", "", new String[] { "tell", "msg", "pm", "w", "whisper", "m" });
		this.plugin = plugin;
	}

	@Override
	public void execute(CommandSender arg0, String[] arg1) {
		if (arg0 instanceof ProxiedPlayer) {
			if (arg1.length == 0) {
				arg0.sendMessage(new TextComponent("Incorrect format. Type: /m <player> <message>"));
			} else if (arg1.length == 1) {
				arg0.sendMessage(new TextComponent("You forgot to type the message to  " + arg1[0] + "."));
			} else {
				ProxiedPlayer receiver = this.plugin.getProxy().getPlayer(arg1[0]);
				if (arg0.getName().equalsIgnoreCase(arg1[0])) {
					arg0.sendMessage(new TextComponent("You can't send a private message to yourself."));
				} else if (receiver == null) {
					arg0.sendMessage(new TextComponent("Player " + arg1[0] + " is offline."));
				} else if (!this.plugin.getPlayerList().containsKey(arg1[0])) {
					arg0.sendMessage(new TextComponent("The written playername is wrong. Check the spelling."));
				} else {
					SpkPlayer spksend = this.plugin.getPlayerList().get(arg0.getName()),
							spkreceive = this.plugin.getPlayerList().get(arg1[0]);

					if (!spkreceive.getIsLoggedIn()) {
						arg0.sendMessage(
								new TextComponent("The player is typing their password. Wait until they finish."));
					} else if (spkreceive.getMsgDisable() && !(arg0.hasPermission(Global.PERM_MSGTOGGLEIGN)
							&& receiver.hasPermission(Global.PERM_MSGTOGGLEIGN))) {
						arg0.sendMessage(new TextComponent("Player " + arg1[0] + " has private messages disabled."));
					} else if (spksend.getMsgDisable() && !(arg0.hasPermission(Global.PERM_MSGTOGGLEIGN)
							&& receiver.hasPermission(Global.PERM_MSGTOGGLEIGN))) {
						arg0.sendMessage(new TextComponent(
								"You have private messages disabled. Enable them typing /msgtoggle."));
					} else if (spksend.getLastMessageTime()
							+ this.plugin.getMainCnf().getYaml().getInt(Global.CONFNODE_MSGCOOLDOWN) > Global
									.getCurrentTimeSeconds()
							|| !arg0.hasPermission(Global.PERM_COOLDOWNIGN)) {
						arg0.sendMessage(new TextComponent("Wait "
								+ (spksend.getLastMessageTime()
										+ this.plugin.getMainCnf().getYaml().getInt(Global.CONFNODE_MSGCOOLDOWN)
										- Global.getCurrentTimeSeconds())
								+ " seconds, there is a message sending cooldown."));
					} else {
						StringBuilder strb = new StringBuilder();
						for (int i = 1; i < arg1.length; i++) {
							strb.append(" " + arg1[i]);
						}

						this.plugin.getProxy().getPlayer(arg1[0])
								.sendMessage(new TextComponent("[" + arg0.getName() + " -> Me]" + strb.toString()));
						arg0.sendMessage(new TextComponent("[Me -> " + arg1[0] + "]" + strb.toString()));

						for (ProxiedPlayer player : ProxyServer.getInstance().getPlayers()) {
							if (player.hasPermission(Global.PERM_CHATSPY)
									&& this.plugin.getPlayerList().get(player.getName()).getIsLoggedIn()
									&& this.plugin.getPlayerList().get(player.getName()).getChatspyEnable()
									&& !player.getName().equals(arg0.getName()) && !player.getName().equals(arg1[0])) {
								player.sendMessage(new TextComponent(
										"[" + arg0.getName() + " -> " + arg1[0] + "]" + strb.toString()));
							}
						}

						strb = null;
						spksend.setLastMsgNow();

						this.plugin.getPlayerList().get(arg1[0]).setLastMsgSentFrom(arg0.getName());

					}
					spksend = null;
					spkreceive = null;
				}
			}
		}
	}
}
