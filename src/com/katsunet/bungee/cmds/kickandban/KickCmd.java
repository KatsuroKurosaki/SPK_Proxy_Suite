package com.katsunet.bungee.cmds.kickandban;

import com.katsunet.bungee.async.KickAndBanAsync;
import com.katsunet.common.Global;
import com.katsunet.spkproxysuite.bungee.Main;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class KickCmd extends Command {

  private Main plugin;

  public KickCmd(Main plugin) {
    super("kick", Global.PERM_KICK_COMMAND);
    this.plugin = plugin;
  }

  @Override
  public void execute(CommandSender arg0, String[] arg1) {
    if (arg0 instanceof ProxiedPlayer) {
      if (arg1.length == 0) {
        arg0.sendMessage(new TextComponent("Incorrect format. Type: /kick <player> <reason>"));
      } else if (arg1.length == 1) {
        arg0.sendMessage(
            new TextComponent("You forgot to type a reason before kicking " + arg1[0] + "."));
      } else {
        ProxiedPlayer receiver = this.plugin.getProxy().getPlayer(arg1[0]);
        if (receiver == null) {
          arg0.sendMessage(new TextComponent("Player " + arg1[0] + " is offline."));
        } else if (receiver.hasPermission(Global.PERM_KICK_COMMAND)) {
          arg0.sendMessage(
              new TextComponent("Can't kick " + arg1[0] + ": also has kick permission."));
        } else {
          StringBuilder strb = new StringBuilder();
          for (int i = 1; i < arg1.length; i++) {
            strb.append(" " + arg1[i]);
          }
          
          // Trigger event
          System.out.println(this.plugin.getPlayerList());
          this.plugin.getProxy().getScheduler().runAsync(this.plugin,
              new KickAndBanAsync(this.plugin,
                  this.plugin.getPlayerList().get(arg0.getName()).getPlayerId(),
                  this.plugin.getPlayerList().get(receiver.getName()).getPlayerId(),
                  KickAndBanAsync.KICK, strb.toString()));
          
          // Disconnect player
          receiver.disconnect(new TextComponent("You have been kicked!\n" + strb.toString()));
        }
      }
    } else {
      arg0.sendMessage(new TextComponent("Can't kick players from the console"));
    }
  }
}
