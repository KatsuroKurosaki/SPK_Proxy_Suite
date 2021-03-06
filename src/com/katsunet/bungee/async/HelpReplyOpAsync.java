package com.katsunet.bungee.async;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.katsunet.spkproxysuite.bungee.Main;

public class HelpReplyOpAsync implements Runnable {

  public static final int HELPOP = 1;
  public static final int REPLYOP = 2;

  private Main plugin;
  private int mode, playerId, playerReceiver;
  private String mc_server, message;


  public HelpReplyOpAsync(Main plugin, int mode, int playerId, String mc_mode, String message) {
    this.plugin = plugin;
    this.mode = mode;
    this.playerId = playerId;
    this.mc_server = mc_mode;
    this.message = message;
  }
  
  public HelpReplyOpAsync(Main plugin, int mode, int playerId, int playerReceiver, String message) {
    this.plugin = plugin;
    this.mode = mode;
    this.playerId = playerId;
    this.playerReceiver = playerReceiver;
    this.message = message;
  }

  @Override
  public void run() {
    if (this.plugin.getMysql().connect(true)) {
      PreparedStatement ps = null;
      try {
        switch (this.mode) {
          case HelpReplyOpAsync.HELPOP:
            ps = this.plugin.getMysql().getConnection().prepareStatement(
                "INSERT INTO mc_helpop (player_id, mc_server, message) VALUES (?,?,TRIM(?));"
            );
            ps.setInt(1, this.playerId);
            ps.setString(2, this.mc_server);
            ps.setString(3, this.message);
            ps.executeUpdate();
            ps.close();
            break;

          case HelpReplyOpAsync.REPLYOP:
            ps = this.plugin.getMysql().getConnection().prepareStatement(
                "INSERT INTO mc_replyop (player_sender, player_receiver, message) VALUES (?,?,TRIM(?));"
            );
            ps.setInt(1, this.playerId);
            ps.setInt(2, this.playerReceiver);
            ps.setString(3, this.message);
            ps.executeUpdate();
            ps.close();
            break;
        }

      } catch (SQLException e) {
        e.printStackTrace();
      } finally {
        if (ps != null) {
          try {
            ps.close();
          } catch (SQLException e) {
            e.printStackTrace();
          }
        }
      }
      ps = null;
    }
  }


}
