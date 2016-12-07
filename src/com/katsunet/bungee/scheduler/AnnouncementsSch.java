package com.katsunet.bungee.scheduler;

import com.katsunet.common.Global;
import com.katsunet.spkproxysuite.bungee.Main;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class AnnouncementsSch implements Runnable {

	private Main _plugin;
	private int i;
	
	public AnnouncementsSch(Main plugin){
		this.i=0;
		this._plugin = plugin;
	}
	
	@Override
	public void run(){
		if(this._plugin.getMainCnf().getYaml().getStringList(Global.CONFNODE_ANN_MSGS).size()>0){
			if(this._plugin.getMainCnf().getYaml().getStringList(Global.CONFNODE_ANN_MSGS).size()<=i){
				i=0;
			}
			// TextComponent.fromLegacyText();
			TextComponent msg = new TextComponent(ChatColor.translateAlternateColorCodes('&',this._plugin.getMainCnf().getYaml().getString(Global.CONFNODE_ANN_PREFIX)+this._plugin.getMainCnf().getYaml().getStringList(Global.CONFNODE_ANN_MSGS).get(i)));
			for (ProxiedPlayer player : ProxyServer.getInstance().getPlayers()) {
				player.sendMessage(msg);
			}
			i++;
			msg = null;
		}
	}
}
