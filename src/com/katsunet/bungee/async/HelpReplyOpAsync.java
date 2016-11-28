package com.katsunet.bungee.async;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.katsunet.spkproxysuite.bungee.Main;

public class HelpReplyOpAsync implements Runnable{

	public static int HELPOP = 1;
	public static int REPLYOP = 2;
	
	private Main plugin;
	private int mode;
	private String playername, mc_mode, message;
	
	
	public HelpReplyOpAsync(Main plugin, int mode, String playername, String mc_mode, String message){
		this.plugin=plugin;
		this.mode=mode;
		this.playername=playername;
		this.mc_mode=mc_mode;
		this.message=message;
	}

	@Override
	public void run() {
		if(this.plugin.getMysql().connect(true)){
			PreparedStatement ps=null;
			String sql = null;
			try {
				switch(this.mode){
					case 1:
						sql = "INSERT INTO mc_helpop (playername, mc_mode, message) VALUES (?,?,?);";
						ps = this.plugin.getMysql().getConnection().prepareStatement(sql);
						ps.setString(1, this.playername);
						ps.setString(2, this.mc_mode);
						ps.setString(3, this.message);
						ps.executeUpdate();
						ps.close();
					break;
					
					case 2:
						sql = "INSERT INTO mc_replyop (playername_sender, playername_receiver, message_optional) VALUES (?,?,?);";
						ps = this.plugin.getMysql().getConnection().prepareStatement(sql);
						ps.setString(1, this.playername);
						ps.setString(2, this.mc_mode);
						ps.setString(3, this.message);
						ps.executeUpdate();
						ps.close();
					break;
				}
				
			}catch (SQLException e) {
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
			sql=null;
			ps=null;
		}
	}
	
	
}
