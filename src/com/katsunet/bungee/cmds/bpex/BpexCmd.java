package com.katsunet.bungee.cmds.bpex;

import com.katsunet.common.Global;
import com.katsunet.spkproxysuite.bungee.Main;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

public class BpexCmd extends Command {

	private Main plugin;
	
	public BpexCmd(Main plugin){
		super("bpex",Global.PERM_BPERMISSIONS_COMMAND);
		this.plugin=plugin;
	}

	@Override
	public void execute(CommandSender arg0, String[] arg1) {
		if(arg1.length==0){
			this.info(arg0);
		} else if(arg1[0].equals("user")){
			if(arg1.length==1){
				this.info(arg0);
			} else if (arg1.length==2){
				arg0.sendMessage(new TextComponent("/bpex user "+arg1[1]+" <groupAdd/groupDel> <Nombre grupo>"));
			} else if (arg1.length==3){
				arg0.sendMessage(new TextComponent("/bpex user "+arg1[1]+" "+arg1[2]+" <Nombre grupo>"));
			} else {
				BpexUser.processUser(this.plugin,arg0,arg1[1], arg1[2], arg1[3]);
			}
		} else if(arg1[0].equals("group")){
			// TODO: Implement group management
			arg0.sendMessage(new TextComponent("No Implementado"));
			/*if(arg1.length==1){
				this.info(arg0);
			} else if (arg1.length==2){
				arg0.sendMessage(new TextComponent("/bpex group "+arg1[1]+" <add/del> <Nombre grupo>"));
			} else if (arg1.length==3){
				arg0.sendMessage(new TextComponent("/bpex group "+arg1[1]+" "+arg1[2]+" <Nombre grupo>"));
			} else {
				BpexGroup.processGroupUser(this.plugin,arg0,arg1[1], arg1[2], arg1[3]);
			}*/
		} else {
			this.info(arg0);
		}
	}
	
	private void info(CommandSender arg0){
		arg0.sendMessage(new TextComponent("**********************"));
		arg0.sendMessage(new TextComponent(" -=: BPex :=-"));
		arg0.sendMessage(new TextComponent("/bpex user :: Gestión de usuarios"));
		arg0.sendMessage(new TextComponent("/bpex group :: Gestión de grupos"));
		arg0.sendMessage(new TextComponent("**********************"));
	}
	
	
}
