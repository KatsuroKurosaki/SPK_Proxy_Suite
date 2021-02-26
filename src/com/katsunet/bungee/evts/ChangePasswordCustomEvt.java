package com.katsunet.bungee.evts;

import com.katsunet.bungee.evts.custom.ChangePasswordCustomEvent;

import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ChangePasswordCustomEvt implements Listener {

  @EventHandler
  public void onPwChangeEvent(ChangePasswordCustomEvent e) {
    if (e.getSuccess()) {
      e.getPlayer().sendMessage(new TextComponent("Your password has been updated successfully."));
    } else {
      e.getPlayer().sendMessage(new TextComponent("Error triggered: " + e.getMsg()));
    }
  }

}
