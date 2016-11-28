package com.katsunet.common;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class BungeeYamlFile {
	
	private Plugin plugin;
	private File file;
	private Configuration configuration;
	
	public BungeeYamlFile(Plugin plugin, String fileName){
		this.plugin=plugin;
		if (!this.plugin.getDataFolder().exists()){
			this.plugin.getDataFolder().mkdir();
		}
		this.file = new File(this.plugin.getDataFolder().getPath()+File.separator+fileName);
		if (!this.file.exists()){
			this.plugin.getLogger().info("Error 404: "+fileName+" Not Found! Generating a default one.");
			this.createYamlFile(fileName);
		}
		this.loadYamlFile();
	}
	
	private void createYamlFile(String fileName){
		try{
			Files.copy(this.plugin.getResourceAsStream(fileName), this.file.toPath());
		} catch (IOException ioe){
			this.plugin.getLogger().warning("Could not copy YAML file.");
			ioe.printStackTrace();
		}
	}
	
	public void loadYamlFile(){
		try {
			this.configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(this.file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void saveYamlFile(){
		try {
			ConfigurationProvider.getProvider(YamlConfiguration.class).save(this.configuration, this.file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void updateYamlFile(String fileName){
		try{
			this.file.delete();
			Files.copy(this.plugin.getResourceAsStream(fileName), this.file.toPath());
			this.loadYamlFile();
		} catch (IOException ioe){
			this.plugin.getLogger().warning("Could not update YAML file.");
			ioe.printStackTrace();
		}
	}
	
	public Configuration getYaml(){
		return this.configuration;
	}
}
