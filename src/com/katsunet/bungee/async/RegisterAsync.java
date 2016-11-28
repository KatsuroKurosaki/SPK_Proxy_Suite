package com.katsunet.bungee.async;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.katsunet.bungee.evts.custom.RegisterCusEvt;
import com.katsunet.common.Global;
import com.katsunet.spkproxysuite.bungee.Main;

import net.md_5.bungee.api.connection.ProxiedPlayer;

public class RegisterAsync implements Runnable{

	private Main plugin;
	private ProxiedPlayer player;
	private String password, email;
	
	public RegisterAsync(Main plugin, ProxiedPlayer player, String password, String email){
		this.plugin=plugin;
		this.player=player;
		this.password=password;
		this.email = email;
	}
	
	@Override
	public void run() {
		if(this.plugin.getMysql().connect(true)){
			PreparedStatement ps=null;
			ResultSet rs = null;
			String sql = null;
			
			try {
				sql = "SELECT id, playername, email FROM mc_players WHERE playername = ? OR email = ?;";
				ps = this.plugin.getMysql().getConnection().prepareStatement(sql);
				ps.setString(1, this.player.getName());
				ps.setString(2, this.email);
				rs = ps.executeQuery();
				if (rs.isBeforeFirst() ) {
					rs.next();
					if(rs.getString("email").equals(this.email) && !rs.getString("playername").equals(this.player.getName())){
						this.plugin.getProxy().getPluginManager().callEvent(new RegisterCusEvt(false, this.player,"El email que has escrito, ya existe en el server."));
					} else {
						this.plugin.getProxy().getPluginManager().callEvent(new RegisterCusEvt(false, this.player,"Ya tienes una contraseña, conectate con: /login <contraseña>"));
					}
				}else{
					ps.close();
					sql = "INSERT INTO mc_players (playername, uuid, `password`, salt, email, registerip, lastlogin, lastip) VALUES (?,?,?,?,?,?,NOW(),?);";
					String salt = Global.generateRandomUuidString();
					String pass= Global.whirlpoolEncode(this.password+salt);
					ps = this.plugin.getMysql().getConnection().prepareStatement(sql);
					ps.setString(1, player.getName());
					ps.setString(2, player.getUniqueId().toString());
					ps.setString(3, pass);
					ps.setString(4, salt);
					ps.setString(5, this.email);
					ps.setString(6, this.player.getAddress().getAddress().getHostAddress());
					ps.setString(7, this.player.getAddress().getAddress().getHostAddress());
					ps.executeUpdate();
					ps.close();
					this.plugin.getProxy().getPluginManager().callEvent(new RegisterCusEvt(true, this.player,""));
				}
				rs.close();
				ps.close();
			} catch (SQLException e) {
				this.plugin.getProxy().getPluginManager().callEvent(new RegisterCusEvt(false, this.player,"Se ha producido un error al registrar tu cuenta. Contacta con el Admin."));
				e.printStackTrace();
			} finally {
				if(ps != null){
					try {
						ps.close();
					} catch (SQLException e) {
						this.plugin.getProxy().getPluginManager().callEvent(new RegisterCusEvt(false, this.player,"Se ha producido un error al registrar tu cuenta. Contacta con el Admin."));
						e.printStackTrace();
					}
					ps = null;
				}
			}
			ps=null;
			this.plugin.getMysql().disconnect();
		} else {
			this.plugin.getProxy().getPluginManager().callEvent(new RegisterCusEvt(false, this.player,"Se ha producido un error al registrar tu cuenta. Contacta con el Admin."));
		}
	}

}
