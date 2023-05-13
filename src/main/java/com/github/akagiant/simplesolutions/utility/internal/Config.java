/*
 * This file is part of SimpleSolutions, licensed under the MIT License.
 *
 *  Copyright (c) AkaGiant
 *  Copyright (c) contributors
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 */

package com.github.akagiant.simplesolutions.utility.internal;

import com.github.akagiant.simplesolutions.SimpleSolutions;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Config {

	private static final Plugin plugin = SimpleSolutions.getPlugin();
	private final String fileName;
	private FileConfiguration config;
	private File file;

	public Config(String fileName) {
		this.fileName = fileName;
		file = new File(Bukkit.getServer().getPluginManager().getPlugin(plugin.getName()).getDataFolder(), File.separator + fileName + ".yml");
		saveDefaultConfig();
		config = YamlConfiguration.loadConfiguration(file);
	}

	public FileConfiguration getConfig() { return config; }

	public static FileConfiguration getConfig(String configName) {
		File file = getFile(configName);
		if (file == null) return null;
		return YamlConfiguration.loadConfiguration(file);
	}


	public File getFile() { return file; }

	public static File getFile(String configName) {
		File file = new File(Bukkit.getServer().getPluginManager().getPlugin(plugin.getName()).getDataFolder(), File.separator + configName + ".yml");
		if (file.exists()) return file;
		return null;
	}

	public boolean exists() { return file.exists(); }
	static boolean exists(File file) { return file.exists(); }

	public void saveConfig() {
		try {
			this.getConfig().save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void saveDefaultConfig() {
		if (file == null)
			file = new File(Bukkit.getServer().getPluginManager().getPlugin(plugin.getName()).getDataFolder(), File.separator + fileName + ".yml");

		if (!file.exists()) {
			plugin.saveResource(fileName + ".yml", false);
		}
	}

	public void reloadConfig() {
		if (!exists()) {
			Logger.severe("Could not reload file: " + fileName + " as it does not exist.");
			return;
		}

		config = YamlConfiguration.loadConfiguration(file);
		InputStream stream = plugin.getResource(fileName);
		if (stream != null) {
			YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(stream));
			config.setDefaults(defaultConfig);
		}
	}

	public static void reloadConfig(File file) {
		if (!exists(file)) {
			Logger.severe("Could not reload file: " + file.getName() + " as it does not exist.");
			return;
		}
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		InputStream stream = plugin.getResource(file.getName());
		if (stream != null) {
			YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(stream));
			config.setDefaults(defaultConfig);
		}
	}

	public static Config create(String fileName) {
		File file = new File(Bukkit.getServer().getPluginManager().getPlugin(plugin.getName()).getDataFolder(), File.separator + fileName + ".yml");
		FileConfiguration config = YamlConfiguration.loadConfiguration(file);

		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return new Config(fileName);
	}
}

