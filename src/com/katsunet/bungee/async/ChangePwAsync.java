package com.katsunet.bungee.async;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.katsunet.bungee.evts.custom.ChangePwCusEvt;
import com.katsunet.common.Global;
import com.katsunet.spkproxysuite.bungee.Main;

import net.md_5.bungee.api.connection.ProxiedPlayer;

public class ChangePwAsync implements Runnable {

	private Main plugin;
	private ProxiedPlayer player;
	private String oldpw, newpw;
	
	public ChangePwAsync(Main plugin, ProxiedPlayer player, String oldpw, String newpw){
		this.plugin=plugin;
		this.player=player;
		this.oldpw=oldpw;
		this.newpw = newpw;
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
					if(rs.getString("password").equals(Global.whirlpoolEncode(this.oldpw+rs.getString("salt")))){
						sql = "UPDATE mc_players SET `password` = ?, salt = ?, pwd_last_change = NOW() WHERE playername = ?;";
						String salt = Global.generateRandomUuidString();
						String pass= Global.whirlpoolEncode(this.newpw+salt);
						ps.close();
						ps = this.plugin.getMysql().getConnection().prepareStatement(sql);
						ps.setString(1, pass);
						ps.setString(2, salt);
						ps.setString(3, this.player.getName());
						ps.executeUpdate();
						ps.close();
						this.plugin.getProxy().getPluginManager().callEvent(new ChangePwCusEvt(true, this.player,""));
					} else {
						this.plugin.getProxy().getPluginManager().callEvent(new ChangePwCusEvt(false, this.player,"La contraseña que has escrito no es correcta."));
					}
				}else{
					
					this.plugin.getProxy().getPluginManager().callEvent(new ChangePwCusEvt(false, this.player,"Se ha producido un error desconocido. Contacta con el Admin"));
				}
				rs.close();
				ps.close();
			} catch (SQLException e) {
				this.plugin.getProxy().getPluginManager().callEvent(new ChangePwCusEvt(false, this.player,"Se ha producido un error al cambiar la contraseña. Contacta con el Admin."));
				e.printStackTrace();
			} finally {
				if(ps != null){
					try {
						ps.close();
					} catch (SQLException e) {
						this.plugin.getProxy().getPluginManager().callEvent(new ChangePwCusEvt(false, this.player,"Se ha producido un error al cambiar la contraseña. Contacta con el Admin."));
						e.printStackTrace();
					}
					ps = null;
				}
			}
			ps=null;
			this.plugin.getMysql().disconnect();
		} else {
			this.plugin.getProxy().getPluginManager().callEvent(new ChangePwCusEvt(false, this.player,"Se ha producido un error al cambiar la contraseña. Contacta con el Admin."));
		}
	}
	
	
}
