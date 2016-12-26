package com.katsunet.bungee.async;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.katsunet.bungee.evts.custom.PlayerinfoCustomEvent;
import com.katsunet.spkproxysuite.bungee.Main;

import net.md_5.bungee.api.CommandSender;

public class PlayerinfoAsync implements Runnable{

	private Main plugin;
	private CommandSender sender;
	private String playername;
	
	public PlayerinfoAsync(Main plugin, CommandSender sender, String playername){
		this.plugin=plugin;
		this.sender=sender;
		this.playername=playername;
	}

	@Override
	public void run() {
		if(this.plugin.getMysql().connect(true)){
			PreparedStatement ps=null;
			ResultSet rs = null;
			String sql = null;
			
			try {
				sql = "SELECT mc_players.email, mc_ranks.rank, DATE_FORMAT(mc_players.rankuntil,'%H:%i:%s %d/%m/%Y') as rankuntil, mc_players.connections, DATE_FORMAT(mc_players.registerdate,'%H:%i:%s %d/%m/%Y') as registerdate, DATE_FORMAT(mc_players.lastlogin,'%H:%i:%s %d/%m/%Y') as lastlogin, mc_players.lastip "
					+ "FROM mc_players "
					+ "INNER JOIN mc_ranks ON mc_ranks.id = mc_players.rankid "
					+ "WHERE mc_players.playername = ?;";
				ps = this.plugin.getMysql().getConnection().prepareStatement(sql);
				ps.setString(1, this.playername);
				rs = ps.executeQuery();
				if (rs.isBeforeFirst() ) {
					rs.next();
					this.plugin.getProxy().getPluginManager().callEvent(
						new PlayerinfoCustomEvent(
							true,
							this.sender,
							this.playername,
							rs.getString("email"),
							rs.getString("rank"),
							rs.getString("rankuntil"),
							rs.getInt("connections"),
							rs.getString("registerdate"),
							rs.getString("lastlogin"),
							rs.getString("lastip")
						)
					);
				}else{
					this.plugin.getProxy().getPluginManager().callEvent(new PlayerinfoCustomEvent(false,"No se han encontrado datos de "+this.playername+".", this.sender));
				}
				rs.close();
				ps.close();
			} catch (SQLException e) {
				this.plugin.getProxy().getPluginManager().callEvent(new PlayerinfoCustomEvent(false,"Se ha producido un error al consultar datos. Contacta con el Admin.", this.sender));
				e.printStackTrace();
			} finally {
				if(ps != null){
					try {
						ps.close();
					} catch (SQLException e) {
						this.plugin.getProxy().getPluginManager().callEvent(new PlayerinfoCustomEvent(false,"Se ha producido un error al consultar datos. Contacta con el Admin.", this.sender));
						e.printStackTrace();
					}
					ps = null;
				}
			}
			ps=null;
			rs=null;
			this.plugin.getMysql().disconnect();
		} else {
			this.plugin.getProxy().getPluginManager().callEvent(new PlayerinfoCustomEvent(false,"Se ha producido un error al consultar datos. Contacta con el Admin.", this.sender));
		}
	}
}
