package com.katsunet.bungee.evts;

import com.katsunet.common.Global;
import com.katsunet.spkproxysuite.bungee.Main;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.ServerKickEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ServerKickEvt implements Listener {

  private Main _plugin;

  public ServerKickEvt(Main plugin) {
    this._plugin = plugin;
  }

  @EventHandler
  public void onServerKickEvent(ServerKickEvent e) {
    if (!e.getKickedFrom().getName()
        .equals(this._plugin.getMainCnf().getYaml().getString(Global.CONFNODE_AUTHLOBBY_SERVER))) {
      e.setCancelled(true);
      e.getPlayer()
          .sendMessage(new TextComponent("The server was shutdown. Use /"
              + this._plugin.getMainCnf().getYaml().getString(Global.CONFNODE_LOBBY_SERVER)
              + " to return to the lobby."));
      e.getPlayer().connect(ProxyServer.getInstance().getServerInfo(
          this._plugin.getMainCnf().getYaml().getString(Global.CONFNODE_AUTHLOBBY_SERVER)));
    }
  }

}
