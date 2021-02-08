package com.katsunet.bungee.cmds.privatemessage;

import com.katsunet.classes.SpkPlayer;
import com.katsunet.common.Global;
import com.katsunet.spkproxysuite.bungee.Main;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class ReplyCmd extends Command {

	private Main plugin;

	public ReplyCmd(Main plugin) {
		super("reply", "", new String[] { "r" });
		this.plugin = plugin;
	}

	@Override
	public void execute(CommandSender arg0, String[] arg1) {
		if (arg0 instanceof ProxiedPlayer) {
			if (arg1.length == 0) {
				arg0.sendMessage(new TextComponent("Incorrect format. Type: /r <message>"));
			} else {
				SpkPlayer p = this.plugin.getPlayerList().get(arg0.getName());
				if (p.getLastMsgSentFrom() == null) {
					arg0.sendMessage(new TextComponent("There is nobody to reply a private message."));
				} else {
					ProxiedPlayer receiver = this.plugin.getProxy().getPlayer(p.getLastMsgSentFrom());
					if (receiver == null) {
						arg0.sendMessage(new TextComponent("Player " + p.getLastMsgSentFrom() + " is offline."));
						p.setLastMsgSentFrom(null);
					} else if (this.plugin.getPlayerList().get(receiver.getName()).getMsgDisable()
							&& !(arg0.hasPermission(Global.PERM_MSGTOGGLEIGN)
									&& receiver.hasPermission(Global.PERM_MSGTOGGLEIGN))) {
						arg0.sendMessage(
								new TextComponent("Player " + receiver.getName() + " has private messages disabled."));
					} else if (this.plugin.getPlayerList().get(arg0.getName()).getMsgDisable()
							&& !(arg0.hasPermission(Global.PERM_MSGTOGGLEIGN)
									&& receiver.hasPermission(Global.PERM_MSGTOGGLEIGN))) {
						arg0.sendMessage(
								new TextComponent("You have private messages disabled. Enable typing /msgtoggle."));
					} else {
						if (p.getLastMessageTime()
								+ this.plugin.getMainCnf().getYaml().getInt(Global.CONFNODE_MSGCOOLDOWN) > Global
										.getCurrentTimeSeconds()
								|| !arg0.hasPermission(Global.PERM_COOLDOWNIGN)) {
							arg0.sendMessage(
									new TextComponent("[§bMsg§f] Espera "
											+ (p.getLastMessageTime()
													+ this.plugin.getMainCnf().getYaml()
															.getInt(Global.CONFNODE_MSGCOOLDOWN)
													- Global.getCurrentTimeSeconds())
											+ " segundos, aún no puedes enviar mas mensajes privados."));
						} else {
							StringBuilder strb = new StringBuilder();
							for (int i = 0; i < arg1.length; i++) {
								strb.append(" " + arg1[i]);
							}

							this.plugin.getProxy().getPlayer(arg1[0])
									.sendMessage(new TextComponent("[" + arg0.getName() + " -> Me]" + strb.toString()));
							arg0.sendMessage(new TextComponent("[Me -> " + arg1[0] + "]" + strb.toString()));

							for (ProxiedPlayer player : ProxyServer.getInstance().getPlayers()) {
								if (player.hasPermission(Global.PERM_CHATSPY)
										&& this.plugin.getPlayerList().get(player.getName()).getIsLoggedIn()
										&& this.plugin.getPlayerList().get(player.getName()).getChatspyEnable()
										&& !player.getName().equals(arg0.getName())
										&& !player.getName().equals(arg1[0])) {
									player.sendMessage(new TextComponent(
											"[" + arg0.getName() + " -> " + arg1[0] + "]" + strb.toString()));
								}
							}
							strb = null;
							p.setLastMsgNow();

							SpkPlayer rec = this.plugin.getPlayerList().get(receiver.getName());
							rec.setLastMsgSentFrom(arg0.getName());
							rec = null;

						}
					}
				}
			}
		} else {
			arg0.sendMessage(new TextComponent("Console-tan no puede responder mensajes privados."));
		}
	}
}
