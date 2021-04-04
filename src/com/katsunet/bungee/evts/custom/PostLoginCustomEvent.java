package com.katsunet.bungee.evts.custom;

import com.katsunet.spkproxysuite.bungee.Main;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Event;

public class PostLoginCustomEvent extends Event {

  private ProxiedPlayer player;
  private int playerId;
  private String msg;
  private boolean _kickPlayer;
  private Main plugin;

  public PostLoginCustomEvent(ProxiedPlayer player, String msg, boolean kickPlayer) {
    this.player = player;
    this.msg = msg;
    this._kickPlayer = kickPlayer;
    this.playerId = 0;
  }

  public PostLoginCustomEvent(ProxiedPlayer player, String msg, boolean kickPlayer, int playerId, Main plugin) {
    this.player = player;
    this.msg = msg;
    this._kickPlayer = kickPlayer;
    this.playerId = playerId;
    this.plugin = plugin;
  }

  public ProxiedPlayer getPlayer() {
    return this.player;
  }

  public String getMsg() {
    return this.msg;
  }

  public int getPlayerId() {
    return this.playerId;
  }
  
  public Main getPlugin() {
    return this.plugin;
  }

  public boolean getKickPlayer() {
    return this._kickPlayer;
  }

}
