package com.katsunet.bungee.async;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.katsunet.bungee.evts.custom.PostLoginCustomEvent;
import com.katsunet.classes.SpkPlayer;
import com.katsunet.common.Global;
import com.katsunet.spkproxysuite.bungee.Main;

import net.md_5.bungee.api.connection.ProxiedPlayer;

public class PostLoginAsync implements Runnable {

	private Main plugin;
	private ProxiedPlayer player;
	private SpkPlayer spkplayer;

	public PostLoginAsync(Main plugin, ProxiedPlayer player, SpkPlayer spkplayer) {
		this.plugin = plugin;
		this.player = player;
		this.spkplayer = spkplayer;
	}

	@Override
	public void run() {
		if (this.plugin.getMysql().connect(true)) {
			PreparedStatement ps = null;
			ResultSet rs = null;

			try {
				// Check if IP is banned
				ps = this.plugin.getMysql().getConnection().prepareStatement(
					"SELECT banned_reason, banned_until - UNIX_TIMESTAMP(NOW()) AS ban_remain\n" + 
					"FROM mc_ipban\n" + 
					"WHERE banned_ip = INET_ATON(?) AND (banned_until IS NULL OR banned_until > UNIX_TIMESTAMP(NOW())) AND unbanned = b'0';"
				);
				ps.setString(1, this.spkplayer.getIpAddress());
				rs = ps.executeQuery();
				if (rs.isBeforeFirst()) {
					rs.next();
					if(rs.getInt("ban_remain") == 0) {
						this.plugin.getProxy().getPluginManager().callEvent(
							new PostLoginCustomEvent(
								this.player,
								"Your IP address is banned forever.\nReason: " + rs.getString("banned_reason"),
								true
							)
						);
					} else {
						this.plugin.getProxy().getPluginManager().callEvent(
							new PostLoginCustomEvent(
								this.player,
								"Your IP address is banned for " + Global.secondsToDhms(rs.getInt("ban_remain")) + ".\nReason: " + rs.getString("banned_reason"),
								true
							)
						);
					}
				}
				rs.close();
				ps.close();
				
				// Check if user is banned
				ps = this.plugin.getMysql().getConnection().prepareStatement(
					"SELECT banned_reason, banned_until - UNIX_TIMESTAMP(NOW()) as ban_remain\n" + 
					"FROM mc_playerban\n" + 
					"INNER JOIN mc_players ON mc_players.id = mc_playerban.player_id\n" + 
					"WHERE mc_players.playername = ? AND (banned_until IS NULL OR banned_until > UNIX_TIMESTAMP(NOW())) AND unbanned = b'0';"
				);
				ps.setString(1, this.player.getName());
				rs = ps.executeQuery();
				if (rs.isBeforeFirst()) {
					rs.next();
					if(rs.getInt("ban_remain") == 0) {
						this.plugin.getProxy().getPluginManager().callEvent(
							new PostLoginCustomEvent(
								this.player,
								"Your account is banned forever.\nReason: " + rs.getString("banned_reason"),
								true
							)
						);
					} else {
						this.plugin.getProxy().getPluginManager().callEvent(
							new PostLoginCustomEvent(
								this.player,
								"Your account is banned for " + Global.secondsToDhms(rs.getInt("ban_remain")) + ".\nReason: " + rs.getString("banned_reason"),
								true
							)
						);
					}
				}
				rs.close();
				ps.close();
				
				// Search for player
				ps = this.plugin.getMysql().getConnection().prepareStatement(
					"SELECT id, playername\n" +
					"FROM mc_players\n" +
					"WHERE playername = ?;"	
				);
				ps.setString(1, this.player.getName());
				rs = ps.executeQuery();
				if (rs.isBeforeFirst()) {
					rs.next();
					if(this.player.getName().equals(rs.getString("playername"))) {
						this.plugin.getProxy().getPluginManager().callEvent(
							new PostLoginCustomEvent(
								this.player,
								"Welcome back " + this.player.getName() + ", sign-in typing: /login <your password>",
								false,
								rs.getInt("id"),
								this.plugin
							)
						);
					} else {
						this.plugin.getProxy().getPluginManager().callEvent(
							new PostLoginCustomEvent(
								this.player,
								"Your minecraft name '" + this.player.getName() + "' does not have the same letter case as the one registered '" + rs.getString("playername") + "'. Please, correct this.",
								true
							)
						);
					}
				} else {
					this.plugin.getProxy().getPluginManager().callEvent(
						new PostLoginCustomEvent(
							this.player,
							"Welcome to the server, please register typing: /register <password> <email>",
							false
						)
					);
				}
				rs.close();
				ps.close();
			} catch (SQLException e) {
				this.plugin.getProxy().getPluginManager().callEvent(
					new PostLoginCustomEvent(
						this.player,
						"Internal server error, contact the admin.",
						false
					)
				);
				e.printStackTrace();
			} finally {
				if (ps != null) {
					try {
						ps.close();
					} catch (SQLException e) {
						this.plugin.getProxy().getPluginManager().callEvent(
							new PostLoginCustomEvent(
								this.player,
								"Internal server error, contact the admin.",
								false
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
				new PostLoginCustomEvent(
					this.player,
					"Internal server error, contact the admin.",
					false
				)
			);
		}
	}
}
