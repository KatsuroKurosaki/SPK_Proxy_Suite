package com.katsunet.bungee.async;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.mindrot.jbcrypt.BCrypt;

import com.katsunet.bungee.evts.custom.LoginCustomEvent;
import com.katsunet.common.Global;
import com.katsunet.spkproxysuite.bungee.Main;

import net.md_5.bungee.api.connection.ProxiedPlayer;

public class LoginAsync implements Runnable {

	private Main plugin;
	private ProxiedPlayer player;
	private String password;

	public LoginAsync(Main plugin, ProxiedPlayer player, String password) {
		this.plugin = plugin;
		this.player = player;
		this.password = password;
	}

	@Override
	public void run() {
		if (this.plugin.getMysql().connect(true)) {
			PreparedStatement ps = null;
			ResultSet rs = null;

			try {
				ps = this.plugin.getMysql().getConnection()
						.prepareStatement("SELECT playerpassword FROM mc_players WHERE playername = ?;");
				ps.setString(1, this.player.getName());
				rs = ps.executeQuery();
				if (rs.isBeforeFirst()) {
					rs.next();
					if (BCrypt.checkpw(this.password, rs.getString("playerpassword"))) {
						ps.close();

						String ipaddress = Global.extractIpAddress(this.player.getSocketAddress().toString());
						ps = this.plugin.getMysql().getConnection().prepareStatement(
								"UPDATE mc_players SET connections = connections+1, lastlogin = UNIX_TIMESTAMP(NOW()), lastip = INET_ATON(?) WHERE playername = ?;");
						ps.setString(1, ipaddress);
						ps.setString(2, player.getName());
						ps.executeUpdate();
						ps.close();
						this.plugin.getProxy().getPluginManager()
								.callEvent(new LoginCustomEvent(true, this.player, ""));
					} else {
						this.plugin.getPlayerList().get(this.player.getName()).increaseLoginAttempt();
						this.plugin.getProxy().getPluginManager().callEvent(new LoginCustomEvent(false, this.player,
								"La contraseña que has escrito no es correcta."));
					}
				} else {
					this.plugin.getProxy().getPluginManager().callEvent(new LoginCustomEvent(false, this.player,
							"No tienes contraseña aun, creala con: /register <contraseña> <email>"));
				}
				rs.close();
				ps.close();
			} catch (SQLException e) {
				this.plugin.getProxy().getPluginManager().callEvent(new LoginCustomEvent(false, this.player,
						"Se ha producido un error al conectar tu cuenta. Contacta con el Admin."));
				e.printStackTrace();
			} finally {
				if (ps != null) {
					try {
						ps.close();
					} catch (SQLException e) {
						this.plugin.getProxy().getPluginManager().callEvent(new LoginCustomEvent(false, this.player,
								"Se ha producido un error al conectar tu cuenta. Contacta con el Admin."));
						e.printStackTrace();
					}
					ps = null;
				}
			}
			ps = null;
			rs = null;
			this.plugin.getMysql().disconnect();
		} else {
			this.plugin.getProxy().getPluginManager().callEvent(new LoginCustomEvent(false, this.player,
					"[§bAuth§f] Se ha producido un error al conectar tu cuenta. Contacta con el Admin."));
		}
	}
}
