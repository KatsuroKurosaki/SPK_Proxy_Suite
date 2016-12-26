package com.katsunet.bungee.cmds.playerinfo;

import com.katsunet.bungee.async.PlayerinfoAsync;
import com.katsunet.common.Global;
import com.katsunet.spkproxysuite.bungee.Main;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

public class PlayerinfoCmd extends Command {

	private Main plugin;
	
	public PlayerinfoCmd(Main plugin){
		super("playerinfo",Global.PERM_PLAYERINFO_COMMAND,new String[]{"pi"});
		this.plugin=plugin;
	}

	@Override
	public void execute(CommandSender arg0, String[] arg1) {
		if(arg1.length==0){
			arg0.sendMessage(new TextComponent("[§bPlayerInfo§f] Te olvidaste escribir el nombre del jugador. Usa: /playerinfo <Nombre jugador>"));
		} else {
			arg0.sendMessage(new TextComponent("[§bPlayerInfo§f] Buscando datos, espera..."));
			this.plugin.getProxy().getScheduler().runAsync(this.plugin, new PlayerinfoAsync(this.plugin,arg0,arg1[0]));
		}
	}
}
