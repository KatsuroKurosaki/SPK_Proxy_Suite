package com.katsunet.spigot.evts;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class OnEnable implements Listener {

	private JavaPlugin plugin;

	public OnEnable(JavaPlugin plugin) {
		this.plugin = plugin;
	}

	public void onEnable() {
		this.plugin.getLogger().info("-- Optimize SPK checks begin --");

		// server.properties
		this.plugin.getLogger().info("-> server.properties");
		try {
			Properties props = new Properties();

			props.load(new FileReader("server.properties"));
			System.out.println(props.getProperty("render-distance"));

			props = null;
		} catch (FileNotFoundException e) {
			this.plugin.getLogger().info("server.properties not found");
		} catch (IOException e) {
			this.plugin.getLogger().info("Error reading server.properties");
		}

		// bukkit.yml
		this.plugin.getLogger().info("-> bukkit.yml");
		FileConfiguration bukkitYml = YamlConfiguration.loadConfiguration(new File("bukkit.yml"));
		System.out.println(bukkitYml.get("settings.minimum-api"));

		bukkitYml = null;

		// spigot.yml
		this.plugin.getLogger().info("-> spigot.yml");
		FileConfiguration spigotYml = YamlConfiguration.loadConfiguration(new File("spigot.yml"));
		System.out.println(spigotYml.get("world-settings.default.entity-activation-range"));

		spigotYml = null;

		this.plugin.getLogger().info("-- Optimize SPK checks end --");
	}
}
