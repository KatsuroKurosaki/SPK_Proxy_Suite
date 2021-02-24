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
				ps = this.plugin.getMysql().getConnection().prepareStatement(
					"SELECT id, msg_disable, chatspy_enable, playerpassword FROM mc_players WHERE playername = ?;"
				);
				ps.setString(1, this.player.getName());
				rs = ps.executeQuery();
				if (rs.isBeforeFirst()) {
					rs.next();
					if (BCrypt.checkpw(this.password, rs.getString("playerpassword"))) {
						int playerId = rs.getInt("id");
						boolean msgDisable = rs.getBoolean("msg_disable");
						boolean chatspyEnable = rs.getBoolean("chatspy_enable");
						ps.close();

						String ipaddress = Global.extractIpAddress(this.player.getSocketAddress().toString());
						ps = this.plugin.getMysql().getConnection().prepareStatement(
							"UPDATE mc_players SET connections = connections+1, lastlogin = UNIX_TIMESTAMP(NOW()), lastip = INET_ATON(?) WHERE id = ?;"
						);
						ps.setString(1, ipaddress);
						ps.setInt(2, playerId);
						ps.executeUpdate();
						ps.close();
						this.plugin.getProxy().getPluginManager().callEvent(
							new LoginCustomEvent(
								true,
								this.player,
								playerId,
								msgDisable,
								chatspyEnable
							)
						);
					} else {
						this.plugin.getPlayerList().get(this.player.getName()).increaseLoginAttempt();
						this.plugin.getProxy().getPluginManager().callEvent(
							new LoginCustomEvent(
								false,
								this.player,
								"Your password is wrong."
							)
						);
					}
				} else {
					this.plugin.getProxy().getPluginManager().callEvent(
						new LoginCustomEvent(
							false,
							this.player,
							"You don't have a password yet. Sign up typing /register <password> <your email>"
						)
					);
				}
				rs.close();
				ps.close();
			} catch (SQLException e) {
				this.plugin.getProxy().getPluginManager().callEvent(
					new LoginCustomEvent(
						false,
						this.player,
						"Error triggered when logging in. Contact the administrator."
					)
				);
				e.printStackTrace();
			} finally {
				if (ps != null) {
					try {
						ps.close();
					} catch (SQLException e) {
						this.plugin.getProxy().getPluginManager().callEvent(
							new LoginCustomEvent(
								false,
								this.player,
								"Error triggered when logging in. Contact the administrator."
							)
						);
						e.printStackTrace();
					}
					ps = null;
				}
			}
			ps = null;
			rs = null;
			this.plugin.getMysql().disconnect();
		} else {
			this.plugin.getProxy().getPluginManager().callEvent(
				new LoginCustomEvent(
					false,
					this.player,
					"Error triggered when logging in. Contact the administrator."
				)
			);
		}
	}
}
