package com.katsunet.bungee.async;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.mindrot.jbcrypt.BCrypt;
import com.katsunet.bungee.evts.custom.ChangePasswordCustomEvent;
import com.katsunet.spkproxysuite.bungee.Main;

import net.md_5.bungee.api.connection.ProxiedPlayer;

public class ChangePwAsync implements Runnable {

  private Main plugin;
  private ProxiedPlayer player;
  private String oldpw, newpw;

  public ChangePwAsync(Main plugin, ProxiedPlayer player, String oldpw, String newpw) {
    this.plugin = plugin;
    this.player = player;
    this.oldpw = oldpw;
    this.newpw = newpw;
  }

  @Override
  public void run() {
    if (this.plugin.getMysql().connect(true)) {
      PreparedStatement ps = null;
      ResultSet rs = null;
      int playerId = this.plugin.getPlayerList().get(this.player.getName()).getPlayerId();

      try {
        ps = this.plugin.getMysql().getConnection()
            .prepareStatement("SELECT playerpassword FROM mc_players WHERE id = ?;");
        ps.setInt(1, playerId);
        rs = ps.executeQuery();
        if (rs.isBeforeFirst()) {
          rs.next();
          if (BCrypt.checkpw(this.oldpw, rs.getString("playerpassword"))) {
            String pass = BCrypt.hashpw(this.newpw, BCrypt.gensalt());
            ps.close();
            ps = this.plugin.getMysql().getConnection().prepareStatement(
                "UPDATE mc_players SET playerpassword = ?, password_changed = UNIX_TIMESTAMP(NOW()) WHERE id = ?;");
            ps.setString(1, pass);
            ps.setInt(2, playerId);
            ps.executeUpdate();
            ps.close();
            this.plugin.getProxy().getPluginManager()
                .callEvent(new ChangePasswordCustomEvent(true, this.player, ""));
          } else {
            this.plugin.getProxy().getPluginManager().callEvent(new ChangePasswordCustomEvent(false,
                this.player, "The typed password is not correct."));
          }
        } else {

          this.plugin.getProxy().getPluginManager().callEvent(new ChangePasswordCustomEvent(false,
              this.player, "Could not change password: Unknown error. Contact the server admin."));
        }
        rs.close();
        ps.close();
      } catch (SQLException e) {
        this.plugin.getProxy().getPluginManager().callEvent(new ChangePasswordCustomEvent(false,
            this.player, "Could not change password: Unknown error. Contact the server admin."));
        e.printStackTrace();
      } finally {
        if (ps != null) {
          try {
            ps.close();
          } catch (SQLException e) {
            this.plugin.getProxy().getPluginManager()
                .callEvent(new ChangePasswordCustomEvent(false, this.player,
                    "Could not change password: Unknown error. Contact the server admin."));
            e.printStackTrace();
          }
          ps = null;
        }
      }
      ps = null;
      this.plugin.getMysql().disconnect();
    } else {
      this.plugin.getProxy().getPluginManager().callEvent(new ChangePasswordCustomEvent(false,
          this.player, "Could not change password: Unknown error. Contact the server admin."));
    }
  }


}
