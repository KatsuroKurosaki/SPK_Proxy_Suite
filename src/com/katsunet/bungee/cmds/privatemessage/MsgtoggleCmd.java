package com.katsunet.bungee.cmds.privatemessage;

import com.katsunet.common.Global;
import com.katsunet.spkproxysuite.bungee.Main;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class MsgtoggleCmd extends Command {
	
	private Main _plugin;
	
	public MsgtoggleCmd(Main plugin) {
		super("msgtoggle", Global.PERM_MSGTOGGLE);
		this._plugin=plugin;
	}

	@Override
	public void execute(CommandSender arg0, String[] arg1) {
		if(arg0 instanceof ProxiedPlayer){
			if(!this._plugin.getNoRcvList().contains(arg0.getName())){
				this._plugin.getNoRcvList().add(arg0.getName());
				arg0.sendMessage(new TextComponent("[§bMsgToggle§f] Mensajes privados desactivados, solo puedes hablar entre miembros del Staff."));
			} else {
				this._plugin.getNoRcvList().remove(arg0.getName());
				arg0.sendMessage(new TextComponent("[§bMsgToggle§f] Mensajes privados activados, puedes hablar con todo el mundo."));
			}
			this._plugin.getCf().getYaml().set(Global.CONFNODE_NORCVLST, this._plugin.getNoRcvList());
			this._plugin.getCf().saveYamlFile();
		} else {
			arg0.sendMessage(new TextComponent("Console-tan no puede gestionar la recepción de privados."));
		}
	}
}
