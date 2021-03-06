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

  public HelpOpCmd(Main plugin) {
    super("helpop", "", new String[] {"report"});
    this.plugin = plugin;
  }

  @Override
  public void execute(CommandSender arg0, String[] arg1) {
    if (arg0 instanceof ProxiedPlayer) {
      if (arg1.length == 0) {
        arg0.sendMessage(
            new TextComponent("You forgot to type the reason. Use: /helpop <reason>. Use 3 words at least."));
      } else if (arg1.length < 3) {
        arg0.sendMessage(new TextComponent("You have to write, at least, 3 words."));
      } else if (arg0.hasPermission(Global.PERM_RECEIVEHELPOP)) {
        arg0.sendMessage(new TextComponent("You can't send helpop messages, because you are a receiver."));
      } else {
        SpkPlayer p = this.plugin.getPlayerList().get(arg0.getName());
        if (p.getLastHelpopMsgTime() > Global.getCurrentTimeSeconds()) {
          arg0.sendMessage(
              new TextComponent(
                  "Wait " + (p.getLastHelpopMsgTime() - Global.getCurrentTimeSeconds() + 1) + " seconds before sending a new message."
              )
          );
        } else {
          ProxiedPlayer sender = (ProxiedPlayer) arg0;
          sender.sendMessage(new TextComponent("Your message has been sent to the staff."));

          StringBuilder strb = new StringBuilder();
          for (int i = 0; i < arg1.length; i++) {
            strb.append(" " + arg1[i]);
          }
          for (ProxiedPlayer player : ProxyServer.getInstance().getPlayers()) {
            if (player.hasPermission(Global.PERM_RECEIVEHELPOP)) {
              player.sendMessage(
                  new TextComponent(
                      "§c§l[HelpOP] §r'" + arg0.getName() + "' on '" + sender.getServer().getInfo().getName() + "' says:" + strb.toString()
                  )
              );
            }
          }

          this.plugin.getProxy().getScheduler().runAsync(
              this.plugin,
              new HelpReplyOpAsync(
                  this.plugin,
                  HelpReplyOpAsync.HELPOP,
                  this.plugin.getPlayerList().get(arg0.getName()).getPlayerId(),
                  sender.getServer().getInfo().getName(),
                  strb.toString()
              )
          );

          strb = null;
          sender = null;
          p.setLastHelpOpTime(Global.getCurrentTimeSeconds() + this.plugin.getMainCnf().getYaml().getInt(Global.CONFNODE_HELPOPCLDWN));
        }
        p = null;
      }
    }
  }
}
