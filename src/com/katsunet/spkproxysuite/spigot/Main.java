package com.katsunet.spkproxysuite.spigot;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	
	@Override
	public void onEnable() {
		
		// Complete!
		this.getLogger().info("SPK Proxy Suite started.");
	}
	
	@Override
	public void onDisable(){
		this.getLogger().info("SPK Proxy Suite stopped.");
	}
}
