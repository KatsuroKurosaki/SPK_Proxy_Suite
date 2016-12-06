package com.katsunet.bungee.async;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.katsunet.bungee.evts.custom.PostLoginCustomEvent;
import com.katsunet.common.Global;
import com.katsunet.spkproxysuite.bungee.Main;

import net.md_5.bungee.api.connection.ProxiedPlayer;

public class PostLoginAsync implements Runnable{

	private Main plugin;
	private ProxiedPlayer player;
	
	public PostLoginAsync(Main plugin, ProxiedPlayer player){
		this.plugin=plugin;
		this.player=player;
	}

	@Override
	public void run() {
		// Get player country
		this.plugin.getPlayerList().get(this.player.getName()).setCountry( this.plugin.getLookupService().getCountry(this.player.getAddress().getAddress()).getName() );
		// Check anonymous proxy login
		if(!this.plugin.getPlayerList().get(this.player.getName()).getCountry().equals(Global.ANON_PROXY_STR)){
			if(this.plugin.getMysql().connect(true)){
				PreparedStatement ps=null;
				ResultSet rs = null;
				String sql = null;
				
				try {
					sql = "SELECT id FROM mc_players WHERE playername = ?;";
					ps = this.plugin.getMysql().getConnection().prepareStatement(sql);
					ps.setString(1, this.player.getName());
					rs = ps.executeQuery();
					if (rs.isBeforeFirst() ) {
						this.plugin.getProxy().getPluginManager().callEvent(new PostLoginCustomEvent(this.player,"Bienvenido de nuevo "+this.player.getName()+", conectate con: /login <contraseña>",false));
					}else{
						this.plugin.getProxy().getPluginManager().callEvent(new PostLoginCustomEvent(this.player,"Bienvenido al server, registrate con /register <contraseña> <email>",false));
					}
					rs.close();
					ps.close();
				} catch (SQLException e) {
					this.plugin.getProxy().getPluginManager().callEvent(new PostLoginCustomEvent(this.player,"Se ha producido un error en el server. Contacta con el Admin.",false));
					e.printStackTrace();
				} finally {
					if(ps != null){
						try {
							ps.close();
						} catch (SQLException e) {
							this.plugin.getProxy().getPluginManager().callEvent(new PostLoginCustomEvent(this.player,"Se ha producido un error en el server. Contacta con el Admin.",false));
							e.printStackTrace();
						}
						ps = null;
					}
				}
				ps=null;
				this.plugin.getMysql().disconnect();
			} else {
				this.plugin.getProxy().getPluginManager().callEvent(new PostLoginCustomEvent(this.player,"Se ha producido un error en el server. Contacta con el Admin.",false));
			}
		} else {
			this.plugin.getProxy().getPluginManager().callEvent(new PostLoginCustomEvent(this.player,"La conexión al server usando un proxy está prohibida.",true));
		}
	}
}
