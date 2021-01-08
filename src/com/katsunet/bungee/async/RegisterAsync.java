package com.katsunet.bungee.async;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.mindrot.jbcrypt.BCrypt;

import com.katsunet.bungee.evts.custom.RegisterCustomEvent;
import com.katsunet.common.Global;
import com.katsunet.spkproxysuite.bungee.Main;

import net.md_5.bungee.api.connection.ProxiedPlayer;

public class RegisterAsync implements Runnable {

	private Main plugin;
	private ProxiedPlayer player;
	private String password, email;

	public RegisterAsync(Main plugin, ProxiedPlayer player, String password, String email) {
		this.plugin = plugin;
		this.player = player;
		this.password = password;
		this.email = email;
	}

	@Override
	public void run() {
		if (this.plugin.getMysql().connect(true)) {
			PreparedStatement ps = null;
			ResultSet rs = null;

			try {
				ps = this.plugin.getMysql().getConnection().prepareStatement(
					"SELECT playername, email FROM mc_players WHERE playername = ? OR email = ?;"
				);
				ps.setString(1, this.player.getName());
				ps.setString(2, this.email);
				rs = ps.executeQuery();
				if (rs.isBeforeFirst()) {
					rs.next();
					if (rs.getString("email").equals(this.email) && !rs.getString("playername").equals(this.player.getName())) {
						this.plugin.getProxy().getPluginManager().callEvent(
							new RegisterCustomEvent(
								false,
								this.player,
								"The provided email already exists on the server."
							)
						);
					} else {
						this.plugin.getProxy().getPluginManager().callEvent(
							new RegisterCustomEvent(
								false,
								this.player,
								"You are already registered. Use /login <password> instead."
							)
						);
					}
				} else {
					ps.close();

					int key = 0;
					String pass = BCrypt.hashpw(this.password, BCrypt.gensalt());
					String ipaddress = Global.extractIpAddress(this.player.getSocketAddress().toString());
					ps = this.plugin.getMysql().getConnection().prepareStatement(
						"INSERT INTO mc_players (playername, playeruuid, playerpassword, email, registerip, lastlogin, lastip) " +
						"VALUES (?,?,?,?,INET_ATON(?),UNIX_TIMESTAMP(NOW()),INET_ATON(?));",
						Statement.RETURN_GENERATED_KEYS
					);
					ps.setString(1, player.getName());
					ps.setString(2, player.getUniqueId().toString());
					ps.setString(3, pass);
					ps.setString(4, this.email);
					ps.setString(5, ipaddress);
					ps.setString(6, ipaddress);
					ps.executeUpdate();
					ResultSet keys = ps.getGeneratedKeys();    
					keys.next();  
					key = keys.getInt(1);
					ps.close();
					this.plugin.getProxy().getPluginManager().callEvent(
						new RegisterCustomEvent(
							true,
							this.player,
							key
						)
					);
				}
				rs.close();
				ps.close();
			} catch (SQLException e) {
				this.plugin.getProxy().getPluginManager().callEvent(
					new RegisterCustomEvent(
						false,
						this.player,
						"Error triggered when registering. Contact the administrator."
					)
				);
				e.printStackTrace();
			} finally {
				if (ps != null) {
					try {
						ps.close();
					} catch (SQLException e) {
						this.plugin.getProxy().getPluginManager().callEvent(
							new RegisterCustomEvent(
								false,
								this.player,
								"Error triggered when registering. Contact the administrator."
							)
						);
						e.printStackTrace();
					}
					ps = null;
				}
			}
			ps = null;
			this.plugin.getMysql().disconnect();
		} else {
			this.plugin.getProxy().getPluginManager().callEvent(
				new RegisterCustomEvent(
					false,
					this.player,
					"Error triggered when registering. Contact the administrator."
				)
			);
		}
	}

}
