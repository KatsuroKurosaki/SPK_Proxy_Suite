package com.katsunet.bungee.async;

import java.sql.PreparedStatement;

import java.sql.SQLException;

import com.katsunet.classes.SpkPlayer;
import com.katsunet.spkproxysuite.bungee.Main;

public class MessagingAsync implements Runnable {
	public static final int CHATSPY = 1;
	public static final int MSGTOGGLE = 2;

	private Main plugin;
	private SpkPlayer player;
	private int messagetype;

	public MessagingAsync(Main plugin, SpkPlayer player, int messagetype) {
		this.plugin = plugin;
		this.player = player;
		this.messagetype = messagetype;
	}

	@Override
	public void run() {
		if (this.plugin.getMysql().connect(true)) {
			PreparedStatement ps = null;

			try {
				switch (this.messagetype) {
				case CHATSPY:
					ps = this.plugin.getMysql().getConnection()
							.prepareStatement("UPDATE mc_players SET chatspy_enable = ? WHERE id = ?;");
					ps.setBoolean(1, this.player.getChatspyEnable());
					ps.setInt(2, this.player.getPlayerId());
					ps.executeUpdate();
					ps.close();
					break;

				case MSGTOGGLE:
					ps = this.plugin.getMysql().getConnection()
							.prepareStatement("UPDATE mc_players SET msg_disable = ? WHERE id = ?;");
					ps.setBoolean(1, this.player.getMsgDisable());
					ps.setInt(2, this.player.getPlayerId());
					ps.executeUpdate();
					ps.close();
					break;

				default:
					System.out.println("Message type not valid: " + this.messagetype);
				}

				ps.close();
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
