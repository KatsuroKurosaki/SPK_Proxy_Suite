package com.katsunet.bungee.async;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.katsunet.classes.SpkPlayer;
import com.katsunet.spkproxysuite.bungee.Main;

public class PlayerDisconnectAsync implements Runnable{
	
	private Main plugin;
	private String playername;
	private SpkPlayer spk;
	private int tsdisconnect;
	
	public PlayerDisconnectAsync(Main plugin, String playername, SpkPlayer spk, int tsdisconnect){
		this.plugin=plugin;
		this.playername=playername;
		this.spk=spk;
		this.tsdisconnect=tsdisconnect;
	}

	@Override
	public void run() {
		if(this.plugin.getMysql().connect(true)){
			PreparedStatement ps=null;
			String sql = null;
			
			try {
				sql = "INSERT INTO mc_players_log (playername, tsconnect, tsdisconnect, ipaddress, mcversion) VALUES (?,?,?,?,?);";
				ps = this.plugin.getMysql().getConnection().prepareStatement(sql);
				ps.setString(1, this.playername);
				ps.setInt(2, this.spk.getConnectTime());
				ps.setInt(3, this.tsdisconnect);
				ps.setString(4, this.spk.getIpAddress());
				ps.setInt(5, this.spk.getMcVersion());
				ps.executeUpdate();
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if(ps != null){
					try {
						ps.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
					ps = null;
				}
			}
			ps=null;
			this.plugin.getMysql().disconnect();
		}
	}

}
