package com.katsunet.common;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import net.md_5.bungee.api.plugin.Plugin;

public class TextFile {

	private Plugin plugin;
	private File file;

	public TextFile(Plugin plugin, String fileName) {
		this.plugin = plugin;
		if (!this.plugin.getDataFolder().exists()) {
			this.plugin.getDataFolder().mkdir();
		}
		this.file = new File(this.plugin.getDataFolder().getPath() + File.separator + fileName);
		if (!this.file.exists()) {
			this.plugin.getLogger().info("Error 404: " + fileName + " Not Found! Creating empty file.");
			this.createTextFile();
		}
	}

	private void createTextFile() {
		try {
			if (this.file.createNewFile()) {
				System.out.println("File created: " + this.file.getName());
			} else {
				System.out.println("File already exists.");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String readTextFile() {
		String fileContents = null;
		try {
			Scanner reader = new Scanner(this.file);
			StringBuilder builder = new StringBuilder();
			while (reader.hasNextLine()) {
				builder.append(reader.nextLine());
			}
			fileContents = builder.toString();
			builder = null;
			reader.close();
			reader = null;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return fileContents;
	}

	public void writeTextFile(String fileContents) {
		try {
			FileWriter writer = new FileWriter(this.file);
			writer.write(fileContents);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public File getFile() {
		return this.file;
	}
}
