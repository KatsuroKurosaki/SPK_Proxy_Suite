package com.katsunet.bungee.cmds.helpop;

import com.katsunet.bungee.async.HelpReplyOpAsync;
import com.katsunet.classes.SpkPlayer;
import com.katsunet.common.Global;
import com.katsunet.spkproxysuite.bungee.Main;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class HelpOpCmd extends Command {

	private Main plugin;
	
	public HelpOpCmd(Main plugin){
		super("helpop", "", new String[]{"report"});
		this.plugin=plugin;
	}
	
	@Override
	public void execute(CommandSender arg0, String[] arg1) {
		if (arg0 instanceof ProxiedPlayer) {
			if(arg1.length==0){
				arg0.sendMessage(new TextComponent("[§cHelp§eOP§f] Te olvidaste el motivo. Usa: /helpop <motivo>"));
			} else if (arg1.length<3){
				arg0.sendMessage(new TextComponent("[§cHelp§eOP§f] Debes escribir almenos 3 palabras para enviar el helpop."));
			} else if(arg0.hasPermission(Global.PERM_HELPOP)){
				arg0.sendMessage(new TextComponent("[§cHelp§eOP§f] Ya eres parte del staff, no puedes enviarte ayuda a ti mismo."));
			} else {
				SpkPlayer p = this.plugin.getPlayerList().get(arg0.getName());
				if(p.getLastHelpopMsgTime()+this.plugin.getCf().getYaml().getInt(Global.CONFNODE_HELPOPCLDWN)<Global.getCurrentTimeSeconds()){
					ProxiedPlayer sender = (ProxiedPlayer) arg0;
					sender.sendMessage(new TextComponent("[§cHelp§eOP§f] Has solicitado ayuda al Staff."));
					
					StringBuilder strb = new StringBuilder();
					for(int i=0;i<arg1.length;i++) {
						strb.append(" "+arg1[i]);
					}
					for (ProxiedPlayer player : ProxyServer.getInstance().getPlayers()) {
						if (player.hasPermission(Global.PERM_HELPOP)){
							player.sendMessage(new TextComponent("§c§l[HelpOP] §r'"+arg0.getName()+"' en '"+sender.getServer().getInfo().getName()+"' dice:"+strb.toString()));
						}
					}
					
					this.plugin.getProxy().getScheduler().runAsync(this.plugin, new HelpReplyOpAsync(this.plugin,HelpReplyOpAsync.HELPOP, arg0.getName(), sender.getServer().getInfo().getName(), strb.toString()));
					
					strb=null;
					sender=null;
					p.setLastHelpOpNow();
				} else {
					arg0.sendMessage(new TextComponent("[§cHelp§eOP§f] Espera "+(p.getLastHelpopMsgTime()+this.plugin.getCf().getYaml().getInt(Global.CONFNODE_HELPOPCLDWN)-Global.getCurrentTimeSeconds()+1)+" segundos para solicitar ayuda."));
				}
				p=null;
			}
		} else {
			arg0.sendMessage(new TextComponent("Console-tan no puede solicitar ayuda."));
		}
	}
}
