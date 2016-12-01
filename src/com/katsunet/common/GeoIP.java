package com.katsunet.common;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import net.md_5.bungee.api.plugin.Plugin;

public class GeoIP {
	public static void bungeeProcess(Plugin plugin){
		File geoip = new File(plugin.getDataFolder(),Global.GEOIP_DATABASE_FILE);
		if(!geoip.exists()){
			plugin.getLogger().info("Error 404: "+Global.GEOIP_DATABASE_FILE+" Not Found! Extracting a default one.");
			try {
				Files.copy(plugin.getResourceAsStream(Global.GEOIP_DATABASE_FILE), geoip.toPath());
			} catch (IOException e) {
				e.printStackTrace();
				plugin.getLogger().warning("Could not copy default "+Global.GEOIP_DATABASE_FILE+" file.");
			}
		}
		plugin.getLogger().info(Global.GEOIP_DATABASE_FILE+" loaded successfully.");
		// TO DO: Check if the GeoIP in the JAR is newer than the GeoIP in the plugins folder and update it!
	}
}
