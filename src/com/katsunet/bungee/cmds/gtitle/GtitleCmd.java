package com.katsunet.bungee.cmds.gtitle;

import com.katsunet.common.Global;
import com.katsunet.spkproxysuite.bungee.Main;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.Title;

public class GtitleCmd extends Command {
	
	private Main _plugin;
	
	public GtitleCmd(Main plugin){
		super("gtitle",Global.PERM_GTITLE_COMMAND,new String[]{"gt"});
		this._plugin=plugin;
	}

	@Override
	public void execute(CommandSender arg0, String[] arg1) {
		if(arg1.length>0){
			StringBuilder strb = new StringBuilder();
			for(int i=0;i<arg1.length;i++) {
				strb.append(" "+arg1[i]);
			}
			String[] message = strb.toString().split("//");
			
			Title titleToPlayer = ProxyServer.getInstance().createTitle()
				.title(new ComponentBuilder( ChatColor.translateAlternateColorCodes('&',message[0]) ).create() );
			if(message.length>1){
				titleToPlayer.subTitle(new ComponentBuilder( ChatColor.translateAlternateColorCodes('&',message[1]) ).create() );
			}
			titleToPlayer.fadeIn(this._plugin.getMainCnf().getYaml().getInt(Global.CONFNODE_GTITLE_FADE)*20)
				.stay(this._plugin.getMainCnf().getYaml().getInt(Global.CONFNODE_GTITLE_STAY)*20)
				.fadeOut(this._plugin.getMainCnf().getYaml().getInt(Global.CONFNODE_GTITLE_FADE)*20);
			
			for (ProxiedPlayer player : ProxyServer.getInstance().getPlayers()) {
				titleToPlayer.send(player);
			}
			
			strb=null;
			message=null;
		} else {
			arg0.sendMessage(new TextComponent("[§bGTitle§f] No has escrito texto a enviar. Uso: /gt <titulo>//<subtitulo>."));
		}
	}
}
