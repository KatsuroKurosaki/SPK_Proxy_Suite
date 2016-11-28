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
		super("reply", "", new String[]{"r"});
		this.plugin=plugin;
	}

	@Override
	public void execute(CommandSender arg0, String[] arg1) {
		if(arg0 instanceof ProxiedPlayer){
			if(arg1.length==0){
				arg0.sendMessage(new TextComponent("[§bMsg§f] Formato incorrecto. Usa: /r <mensaje>"));
			} else {
				SpkPlayer p = this.plugin.getPlayerList().get(arg0.getName());
				if(p.getLastMsgSentTo() == null){
					arg0.sendMessage(new TextComponent("[§bMsg§f] No hay nadie para responder un mensaje privado."));
				} else {
					ProxiedPlayer receiver = this.plugin.getProxy().getPlayer(p.getLastMsgSentTo());
					if(receiver == null) {
						arg0.sendMessage(new TextComponent("[§bMsg§f] El jugador esta desconectado, no puede recibir tu mensaje."));
						p.setLastMsgSentFrom(null);
					} else if(this.plugin.getNoRcvList().contains(receiver.getName()) && !(arg0.hasPermission(Global.PERM_MSGTOGGLEIGN) && receiver.hasPermission(Global.PERM_MSGTOGGLEIGN))){
						arg0.sendMessage(new TextComponent("[§bMsg§f] El jugador "+receiver.getName()+" no puede recibir mensajes privados."));
					} else if(this.plugin.getNoRcvList().contains(arg0.getName()) && !(arg0.hasPermission(Global.PERM_MSGTOGGLEIGN) && receiver.hasPermission(Global.PERM_MSGTOGGLEIGN))){
						arg0.sendMessage(new TextComponent("[§bMsg§f] Tienes los mensajes privados desactivados. Usa /msgtoggle."));
					} else {
						if(p.getLastMessageTime()+this.plugin.getCf().getYaml().getInt(Global.CONFNODE_MSGCOOLDOWN)<=Global.getCurrentTimeSeconds() || arg0.hasPermission(Global.PERM_COOLDOWNIGN)){
							StringBuilder strb = new StringBuilder();
							for(int i=0;i<arg1.length;i++) {
								strb.append(" "+arg1[i]);
							}
							
							receiver.sendMessage(new TextComponent("[§b"+arg0.getName()+" §e>> §bYo§f]"+strb.toString()));
							arg0.sendMessage(new TextComponent("[§bYo §e>> §b"+p.getLastMsgSentTo()+"§f]"+strb.toString()));
							
							for (ProxiedPlayer player : ProxyServer.getInstance().getPlayers()) {
								if (player.hasPermission(Global.PERM_CHATSPY) &&
										this.plugin.getChatspyList().contains(player.getName()) &&
										this.plugin.getPlayerList().get(player.getName()).getIsLoggedIn() &&
										!player.getName().equals(arg0.getName()) &&
										!player.getName().equals(receiver.getName())
									){
									player.sendMessage(new TextComponent("§c(ChatSpy)§f [§b"+arg0.getName()+" §c>> §b"+p.getLastMsgSentTo()+"§f]"+strb.toString()));
								}
							}
							strb=null;
							p.setLastMsgNow();
							
							SpkPlayer rec = this.plugin.getPlayerList().get(receiver.getName());
							rec.setLastMsgSentFrom(arg0.getName());
							rec=null;
						} else {
							arg0.sendMessage(new TextComponent("[§bMsg§f] Espera "+(p.getLastMessageTime()+this.plugin.getCf().getYaml().getInt(Global.CONFNODE_MSGCOOLDOWN)-Global.getCurrentTimeSeconds())+" segundos, aún no puedes enviar mas mensajes privados."));
						}
					}
				}
			}
		} else {
			arg0.sendMessage(new TextComponent("Console-tan no puede responder mensajes privados."));
		}
	}
}
