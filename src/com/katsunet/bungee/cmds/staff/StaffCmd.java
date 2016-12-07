package com.katsunet.bungee.cmds.staff;

import com.katsunet.common.Global;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class StaffCmd extends Command{

	public StaffCmd() {
		super("staff", Global.PERM_STAFF_CMD);
	}

	@Override
	public void execute(CommandSender arg0, String[] arg1) {
		arg0.sendMessage(new TextComponent("[§bSTAFF§f] Miembros del Staff online:"));
		for (ProxiedPlayer player : ProxyServer.getInstance().getPlayers()) {
			if (player.hasPermission(Global.PERM_STAFF_MEMBER)){
				arg0.sendMessage(new TextComponent("§c"+Global.fixedLengthString(player.getName(),16)+" §f-> §9"+player.getServer().getInfo().getName()));
			}
		}
	}

	
}
