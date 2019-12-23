package com.katsunet.spkproxysuite.spigot;

import org.bukkit.plugin.java.JavaPlugin;

import com.katsunet.spigot.cmds.gc.GcCmd;

public class Main extends JavaPlugin {
	
	@Override
	public void onEnable() {
		
		// Events
		//this.getServer().getPluginManager().registerEvents(new OnEnable(this), this);
		
		// Commands
		this.getCommand("gc").setExecutor(new GcCmd());
		
		// Task
		//Bukkit.getScheduler().runTaskTimer(this, new TaskCleaner(), 100L, 100L);
		//public class TaskCleaner implements Runnable { public void run() {}}
		
	}
	
	@Override
	public void onDisable(){
		//this.getServer().getPluginManager().registerEvents(new OnDisable(this), this);
	}
}
