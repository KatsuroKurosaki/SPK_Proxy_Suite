package com.katsunet.bungee.async;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.katsunet.bungee.evts.custom.LoginCusEvt;
import com.katsunet.common.Global;
import com.katsunet.spkproxysuite.bungee.Main;

import net.md_5.bungee.api.connection.ProxiedPlayer;

public class LoginAsync implements Runnable{

	private Main plugin;
	private ProxiedPlayer player;
	private String password;
	
	public LoginAsync(Main plugin, ProxiedPlayer player, String password){
		this.plugin=plugin;
		this.player=player;
		this.password=password;
	}

	@Override
	public void run() {
		if(this.plugin.getMysql().connect(true)){
			PreparedStatement ps=null;
			ResultSet rs = null;
			String sql = null;
			
			try {
				sql = "SELECT `password`, salt FROM mc_players WHERE playername = ?;";
				ps = this.plugin.getMysql().getConnection().prepareStatement(sql);
				ps.setString(1, this.player.getName());
				rs = ps.executeQuery();
				if (rs.isBeforeFirst() ) {
					rs.next();
					if(rs.getString("password").equals(Global.whirlpoolEncode(this.password+rs.getString("salt")))){
						ps.close();
						sql = "UPDATE mc_players SET connections=connections+1, lastlogin = NOW(), lastip = ? WHERE playername = ?;";
						ps = this.plugin.getMysql().getConnection().prepareStatement(sql);
						ps.setString(1, this.player.getAddress().getAddress().getHostAddress());
						ps.setString(2, player.getName());
						ps.executeUpdate();
						ps.close();
						this.plugin.getProxy().getPluginManager().callEvent(new LoginCusEvt(true, this.player,""));
					} else {
						this.plugin.getProxy().getPluginManager().callEvent(new LoginCusEvt(false, this.player,"La contraseña que has escrito no es correcta."));
					}
				}else{
					this.plugin.getProxy().getPluginManager().callEvent(new LoginCusEvt(false, this.player,"No tienes contraseña aun, creala con: /register <contraseña> <email>"));
				}
				rs.close();
				ps.close();
			} catch (SQLException e) {
				this.plugin.getProxy().getPluginManager().callEvent(new LoginCusEvt(false, this.player,"Se ha producido un error al conectar tu cuenta. Contacta con el Admin."));
				e.printStackTrace();
			} finally {
				if(ps != null){
					try {
						ps.close();
					} catch (SQLException e) {
						this.plugin.getProxy().getPluginManager().callEvent(new LoginCusEvt(false, this.player,"Se ha producido un error al conectar tu cuenta. Contacta con el Admin."));
						e.printStackTrace();
					}
					ps = null;
				}
			}
			ps=null;
			rs=null;
			this.plugin.getMysql().disconnect();
		} else {
			this.plugin.getProxy().getPluginManager().callEvent(new LoginCusEvt(false, this.player,"[§bAuth§f] Se ha producido un error al conectar tu cuenta. Contacta con el Admin."));
		}
	}
}
