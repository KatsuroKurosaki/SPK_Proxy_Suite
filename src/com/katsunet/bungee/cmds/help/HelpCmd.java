package com.katsunet.bungee.cmds.help;

import com.katsunet.spkproxysuite.bungee.Main;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

public class HelpCmd extends Command {

  //private Main plugin;

  public HelpCmd(Main plugin) {
    super("help");
    //this.plugin = plugin;
  }

  @Override
  public void execute(CommandSender arg0, String[] arg1) {
    arg0.sendMessage(new TextComponent("Player help coming soon."));
  }
}
