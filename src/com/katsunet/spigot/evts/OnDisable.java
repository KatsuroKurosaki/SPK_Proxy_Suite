package com.katsunet.spigot.evts;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class OnDisable implements Listener {

	private JavaPlugin plugin;

	public OnDisable(JavaPlugin plugin) {
		this.plugin = plugin;
	}

	public void onDisable() {
		this.plugin.getLogger().info("SPK Proxy Suite stopped.");
	}

}
