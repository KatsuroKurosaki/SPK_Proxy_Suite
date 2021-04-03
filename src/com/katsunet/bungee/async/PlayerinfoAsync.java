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
			
			try {
				ps = this.plugin.getMysql().getConnection().prepareStatement(
				    "SELECT playeruuid, playername, connections, DATE_FORMAT(registerdate,'%H:%i:%s %d/%m/%Y') AS registerdate, DATE_FORMAT(FROM_UNIXTIME(lastlogin),'%H:%i:%s %d/%m/%Y') AS lastlogin, INET_NTOA(lastip) AS lastip "+
	                "FROM mc_players "+
	                "WHERE mc_players.playername = ?;"
				);
				ps.setString(1, this.playername);
				rs = ps.executeQuery();
				if (rs.isBeforeFirst() ) {
					rs.next();
					this.plugin.getProxy().getPluginManager().callEvent(
						new PlayerinfoCustomEvent(
							true,
							this.sender,
							rs.getString("playername"),
							rs.getString("playeruuid"),
							rs.getInt("connections"),
							rs.getString("registerdate"),
							rs.getString("lastlogin"),
							rs.getString("lastip")
						)
					);
				}else{
				  this.plugin.getProxy().getPluginManager().callEvent(
		                new PlayerinfoCustomEvent(
		                    false,
		                    this.sender,
		                    "No data was found for player: "+this.playername
		                )
		            );
				}
				rs.close();
				ps.close();
			} catch (SQLException e) {
			  this.plugin.getProxy().getPluginManager().callEvent(
	                new PlayerinfoCustomEvent(
	                    false,
	                    this.sender,
	                    "Error triggered while searching info. Try again or contact the admin."
	                )
	            );
				e.printStackTrace();
			} finally {
				if(ps != null){
					try {
						ps.close();
					} catch (SQLException e) {
					  this.plugin.getProxy().getPluginManager().callEvent(
			                new PlayerinfoCustomEvent(
			                    false,
			                    this.sender,
			                    "Error triggered while searching info. Try again or contact the admin."
			                )
			            );
						e.printStackTrace();
					}
					ps = null;
				}
			}
			ps=null;
			rs=null;
			this.plugin.getMysql().disconnect();
		} else {
			this.plugin.getProxy().getPluginManager().callEvent(
			    new PlayerinfoCustomEvent(
			        false,
			        this.sender,
			        "Error triggered while searching info. Try again or contact the admin."
			    )
			);
		}
	}
}
