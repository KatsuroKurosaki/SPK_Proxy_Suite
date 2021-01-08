package com.katsunet.common;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import net.md_5.bungee.api.plugin.Plugin;

public class TextFile {

	private Plugin plugin;
	private String fileName;

	public TextFile(Plugin plugin, String fileName) {
		this.plugin = plugin;
		if (!this.plugin.getDataFolder().exists()) {
			this.plugin.getDataFolder().mkdir();
		}
		this.fileName = this.plugin.getDataFolder().getPath() + File.separator + fileName;
		File f = new File(this.fileName);
		if (!f.exists()) {
			this.plugin.getLogger().info("Error 404: " + fileName + " Not Found! Creating empty file.");
			this.createTextFile(f);
		}
		f = null;
	}

	private void createTextFile(File f) {
		try {
			if (f.createNewFile()) {
				System.out.println("File created: " + f.getName());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String readTextFile() {
		try {
			return new String(Files.readAllBytes(Paths.get(this.fileName)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	public void writeTextFile(String fileContents) {
		try {
			Files.write(Paths.get(this.fileName), fileContents.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
