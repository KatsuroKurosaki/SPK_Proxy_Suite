package com.katsunet.bungee.async;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.katsunet.classes.SpkPlayer;
import com.katsunet.common.Global;
import com.katsunet.spkproxysuite.bungee.Main;

public class PlayerDisconnectAsync implements Runnable {

	private Main plugin;
	private SpkPlayer spkplayer;
	private int tsdisconnect;

	public PlayerDisconnectAsync(Main plugin, SpkPlayer spkplayer) {
		this.plugin = plugin;
		this.spkplayer = spkplayer;
		this.tsdisconnect = Global.getCurrentTimeSeconds();
	}

	@Override
	public void run() {
		if (this.plugin.getMysql().connect(true)) {
			PreparedStatement ps = null;

			try {
				ps = this.plugin.getMysql().getConnection().prepareStatement(
					"INSERT INTO mc_players_log (player_id, time_connect, time_disconnect, ip_address, mcversion) VALUES (?,?,?,INET_ATON(?),?);"
				);
				ps.setInt(1, this.spkplayer.getPlayerId());
				ps.setInt(2, this.spkplayer.getConnectTime());
				ps.setInt(3, this.tsdisconnect);
				ps.setString(4, this.spkplayer.getIpAddress());
				ps.setInt(5, this.spkplayer.getMcVersion());
				ps.executeUpdate();
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
