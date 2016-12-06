package com.katsunet.bungee.scheduler;

import com.katsunet.spkproxysuite.bungee.Main;

public class PlayerKickerSch implements Runnable {

	private Main plugin;
	//private int i;
	
	public PlayerKickerSch(Main plugin){
		//this.i=0;
		this.plugin = plugin;
	}
	
	@Override
	public void run(){
		System.out.println("Player kicker executed.");
		/*if(this.plugin.getMainCnf().getYaml().getStringList(Global.CONFNODE_ANN_MSGS).size()>0){
			if(this.plugin.getMainCnf().getYaml().getStringList(Global.CONFNODE_ANN_MSGS).size()<=i){
				i=0;
			}
			TextComponent msg = new TextComponent(this.plugin.getMainCnf().getYaml().getString(Global.CONFNODE_ANN_PREFIX)+this.plugin.getMainCnf().getYaml().getStringList(Global.CONFNODE_ANN_MSGS).get(i));
			for (ProxiedPlayer player : ProxyServer.getInstance().getPlayers()) {
				player.sendMessage(msg);
			}
			i++;
			msg = null;
		}*/
	}
}
