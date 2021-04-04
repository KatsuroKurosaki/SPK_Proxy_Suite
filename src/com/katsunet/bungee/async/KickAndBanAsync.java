package com.katsunet.bungee.async;

import java.sql.PreparedStatement;

import java.sql.SQLException;

import com.katsunet.spkproxysuite.bungee.Main;

public class KickAndBanAsync implements Runnable {
  public static final int KICK = 1;
  public static final int TEMPBAN = 2;
  public static final int BAN = 3;
  public static final int TEMPIPBAN = 4;
  public static final int IPBAN = 5;

  private Main plugin;
  private String reason;
  private int action, senderId, receiverId;

  public KickAndBanAsync(Main plugin, int senderId, int receiverId, int action, String reason) {
    this.plugin = plugin;
    this.senderId = senderId;
    this.receiverId = receiverId;
    this.action = action;
    this.reason = reason;
  }

  @Override
  public void run() {
    if (this.plugin.getMysql().connect(true)) {
      PreparedStatement ps = null;

      try {
        switch (this.action) {
          case KICK:
            if (this.receiverId != 0) {
              ps = this.plugin.getMysql().getConnection().prepareStatement(
                  "INSERT INTO mc_playerkick (player_id, kicked_by, kicked_reason) VALUES (?,?,TRIM(?));");
              ps.setInt(1, this.receiverId);
              ps.setInt(2, this.senderId);
              ps.setString(3, this.reason);
              ps.executeUpdate();
              ps.close();
            }
            break;

          default:
            System.out.println("Action not valid: " + this.action);
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
          ps = null;
        }
      }
      ps = null;
      this.plugin.getMysql().disconnect();
    }
  }
}
