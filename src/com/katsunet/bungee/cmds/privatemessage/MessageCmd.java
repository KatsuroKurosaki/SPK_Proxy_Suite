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
		super("message", "", new String[]{"tell","msg","pm","w","whisper","m"});
		this.plugin=plugin;
	}

	@Override
	public void execute(CommandSender arg0, String[] arg1) {
		if(arg0 instanceof ProxiedPlayer){
			if(arg1.length==0){
				arg0.sendMessage(new TextComponent("[§bMsg§f] Formato incorrecto. Usa: /m <jugador> <mensaje>"));
			} else {
				ProxiedPlayer receiver = this.plugin.getProxy().getPlayer(arg1[0]);
				if(arg1.length==1) {
					arg0.sendMessage(new TextComponent("[§bMsg§f] Te olvidaste escribir tu mensaje a "+arg1[0]+"."));
				} else if(arg0.getName().equalsIgnoreCase(arg1[0])){
					arg0.sendMessage(new TextComponent("[§bMsg§f] No puedes enviarte un mensaje privado a ti mismo."));
				} else if(receiver == null){
					arg0.sendMessage(new TextComponent("[§bMsg§f] El jugador esta desconectado, no puede recibir tu mensaje."));
				} else if(Global.containsNoCase(this.plugin.getNoRcvList(), arg1[0]) && !(arg0.hasPermission(Global.PERM_MSGTOGGLEIGN) && receiver.hasPermission(Global.PERM_MSGTOGGLEIGN))){
					arg0.sendMessage(new TextComponent("[§bMsg§f] El jugador "+arg1[0]+" no puede recibir mensajes privados."));
				} else if(this.plugin.getNoRcvList().contains(arg0.getName()) && !(arg0.hasPermission(Global.PERM_MSGTOGGLEIGN) && receiver.hasPermission(Global.PERM_MSGTOGGLEIGN))) {
					arg0.sendMessage(new TextComponent("[§bMsg§f] Tienes los mensajes privados desactivados. Usa /msgtoggle."));
				} else if (!this.plugin.getPlayerList().containsKey(arg1[0])){
					arg0.sendMessage(new TextComponent("[§bMsg§f] El nombre de jugador que has escrito no es correcto. Comprueba las mayúsculas."));
				} else if (!this.plugin.getPlayerList().get(arg1[0]).getIsLoggedIn()){
					arg0.sendMessage(new TextComponent("[§bMsg§f] El jugador esta conectado pero aún no ha escrito su contraseña. Espera que termine."));
				} else {
					SpkPlayer p = this.plugin.getPlayerList().get(arg0.getName());
					if(p.getLastMessageTime()+this.plugin.getMainCnf().getYaml().getInt(Global.CONFNODE_MSGCOOLDOWN)<=Global.getCurrentTimeSeconds() || arg0.hasPermission(Global.PERM_COOLDOWNIGN)){
						StringBuilder strb = new StringBuilder();
						for(int i=1;i<arg1.length;i++) {
							strb.append(" "+arg1[i]);
						}
						
						this.plugin.getProxy().getPlayer(arg1[0]).sendMessage(new TextComponent("[§b"+arg0.getName()+" §e-> §bYo§f]"+strb.toString()));
						arg0.sendMessage(new TextComponent("[§bYo §e-> §b"+arg1[0]+"§f]"+strb.toString()));
						
						for (ProxiedPlayer player : ProxyServer.getInstance().getPlayers()) {
							if (player.hasPermission(Global.PERM_CHATSPY) &&
									this.plugin.getPlayerList().get(player.getName()).getIsLoggedIn() &&
									this.plugin.getChatspyList().contains(player.getName()) &&
									!player.getName().equals(arg0.getName()) &&
									!player.getName().equals(arg1[0])
								){
								player.sendMessage(new TextComponent("§c(ChatSpy)§f[§b"+arg0.getName()+" §c-> §b"+arg1[0]+"§f]"+strb.toString()));
							}
						}
						
						strb=null;
						p.setLastMsgNow();
						
						this.plugin.getPlayerList().get(arg1[0]).setLastMsgSentFrom(arg0.getName());
					} else {
						arg0.sendMessage(new TextComponent("[§bMsg§f] Espera "+(p.getLastMessageTime()+this.plugin.getMainCnf().getYaml().getInt(Global.CONFNODE_MSGCOOLDOWN)-Global.getCurrentTimeSeconds())+" segundos, aún no puedes enviar mas mensajes privados."));
					}
					p=null;
				}
			}
		} else {
			arg0.sendMessage(new TextComponent("Console-tan no puede enviar mensajes privados."));
		}
	}
}
