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
				arg0.sendMessage(new TextComponent("You forgot to type the player name. Type: /replyop <player name> <message>"));
			} else {
				ProxiedPlayer receiver = this.plugin.getProxy().getPlayer(arg1[0]);
				if(receiver == null){
					arg0.sendMessage(new TextComponent("Player "+arg1[0]+" is offline."));
				} else if(!this.plugin.getPlayerList().get(arg1[0]).getIsLoggedIn()) {
				  arg0.sendMessage(new TextComponent("Player "+arg1[0]+" is currently typing their password."));
				} else if(receiver.hasPermission(Global.PERM_RECEIVEHELPOP)){
                    arg0.sendMessage(new TextComponent("You can't help "+arg1[0]+". They are a staff member."));
				} else {
				  StringBuilder strb = new StringBuilder();
                  for(int i=1;i<arg1.length;i++) {
                      strb.append(" "+arg1[i]);
                  }
                  receiver.sendMessage(new TextComponent("§c§l[HelpOP] §r'"+arg0.getName()+"' is going to assist you."+strb.toString()));
                  this.plugin.getPlayerList().get(receiver.getName()).setLastHelpOpNow();
                  
                  for (ProxiedPlayer player : ProxyServer.getInstance().getPlayers()) {
                      if (player.hasPermission(Global.PERM_RECEIVEHELPOP)){
                          player.sendMessage(new TextComponent("§c§l[HelpOP] §r'"+arg0.getName()+"' is assisting '"+receiver.getName()+"'."+strb.toString()));
                      }
                  }
                  
                  this.plugin.getProxy().getScheduler().runAsync(
                      this.plugin,
                      new HelpReplyOpAsync(
                          this.plugin,
                          HelpReplyOpAsync.REPLYOP,
                          this.plugin.getPlayerList().get(arg0.getName()).getPlayerId(),
                          this.plugin.getPlayerList().get(arg1[0]).getPlayerId(),
                          strb.toString()
                      )
                  );
                  
                  strb=null;
				}
			}
		}
	}
}
