package com.katsunet.bungee.cmds.helpop;

import com.katsunet.bungee.async.HelpReplyOpAsync;
import com.katsunet.common.Global;
import com.katsunet.spkproxysuite.bungee.Main;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class ReplyOpCmd extends Command {

	private Main plugin;
	
	public ReplyOpCmd(Main plugin){
		super("replyop", Global.PERM_RECEIVEHELPOP);
		this.plugin=plugin;
	}
	
	@Override
	public void execute(CommandSender arg0, String[] arg1) {
		if (arg0 instanceof ProxiedPlayer) {
			if(arg1.length==0){
				arg0.sendMessage(new TextComponent("[§bReply§eOP§f] Te olvidaste el jugador. Usa: /replyop <jugador> <mensaje opcional>"));
			} else {
				ProxiedPlayer receiver = this.plugin.getProxy().getPlayer(arg1[0]);
				if(receiver != null){
					if(!receiver.hasPermission(Global.PERM_RECEIVEHELPOP)){
						StringBuilder strb = new StringBuilder();
						for(int i=1;i<arg1.length;i++) {
							strb.append(" "+arg1[i]);
						}
						receiver.sendMessage(new TextComponent("[§bReply§eOP§f] Te ayuda '"+arg0.getName()+"'."+strb.toString()));
						this.plugin.getPlayerList().get(receiver.getName()).setLastHelpOpTime(Global.getCurrentTimeSeconds()-this.plugin.getMainCnf().getYaml().getInt(Global.CONFNODE_HELPOPCLDWN));
						
						for (ProxiedPlayer player : ProxyServer.getInstance().getPlayers()) {
							if (player.hasPermission(Global.PERM_RECEIVEHELPOP)){
								player.sendMessage(new TextComponent("[§bReply§eOP§f] '"+arg0.getName()+"' ayuda a '"+receiver.getName()+"'."+strb.toString()));
							}
						}
						
						this.plugin.getProxy().getScheduler().runAsync(this.plugin, new HelpReplyOpAsync(this.plugin,HelpReplyOpAsync.REPLYOP, arg0.getName(), arg1[0], strb.toString()));
						
						strb=null;
					} else {
						arg0.sendMessage(new TextComponent("[§bReply§eOP§f] No puedes ayudar a "+arg1[0]+" por ser miembro del staff."));
					}
				} else {
					arg0.sendMessage(new TextComponent("[§bReply§eOP§f] El jugador "+arg1[0]+" está desconectado."));
				}
			}
		} else {
			arg0.sendMessage(new TextComponent("Console-tan no puede responder ayudas."));
		}
	}
}
