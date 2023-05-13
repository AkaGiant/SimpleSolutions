package com.github.akagiant.simplesolutions;

import com.github.akagiant.simplesolutions.utility.PlaceholderManager;
import com.github.akagiant.simplesolutions.utility.UpdateChecker;
import com.github.akagiant.simplesolutions.utility.internal.ConfigManager;
import com.github.akagiant.simplesolutions.utility.internal.Logger;
import com.github.akagiant.simplesolutions.utility.internal.Util;
import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIBukkitConfig;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class SimpleSolutions extends JavaPlugin {

	private static Plugin plugin;
	private static boolean hasPlaceholderAPI = false;

	@Override
	public void onLoad() {
		CommandAPI.onLoad(new CommandAPIBukkitConfig(this).silentLogs(true));
	}

	@Override
	public void onEnable() {
		// Plugin startup logic
		plugin = this;
		CommandAPI.onEnable();

		Logger.toConsole("&m------------------------------------");
		Logger.toConsole("&fPlugin is loading...");
		Logger.toConsole("&m--------------&r &fCore &m&8----------------");

		ConfigManager.registerConfigurations();

		registerCommands();
		registerEvents();

		Logger.toConsole("&fCommands Loaded (&a" + (CommandAPI.getRegisteredCommands().size())+ "&f) &8| &fAliases: (&a" + Util.getCommandAliasesCount() + "&f)");
		Logger.toConsole("&fPermissions Loaded (&a" + (Util.getPermissionsCount())+ "&f)");
		Logger.toConsole("&m------------------------------------");

		findDependencies();

		Logger.toConsole("&m------------------------------------");
		Logger.toConsole("&fChecking for Updates...");
		new UpdateChecker().run();

		Logger.toConsole("&m------------------------------------");
		Logger.toConsole("&ahas been Enabled");
		Logger.toConsole("&m------------------------------------");
		Logger.toConsole("&fDeveloped by &aAkaGiant");
		Logger.toConsole("&fVersion: &a" + plugin.getDescription().getVersion());
		Logger.toConsole("&m------------------------------------");
	}

	private void registerCommands() {

	}

	private void registerEvents() {

	}

	private void findDependencies() {
		Logger.toConsole("&m----------&r &fDependencies &m&8------------");
		Logger.toConsole("&fLooking for &a" + getPlugin().getDescription().getSoftDepend().size() + " &fSoft Dependencies");

		List<String> found = new ArrayList<>();
		List<String> missing = new ArrayList<>();

		for (String dependency : getPlugin().getDescription().getSoftDepend()) {
			if (Bukkit.getServer().getPluginManager().getPlugin(dependency) == null) missing.add(dependency);
			else found.add(dependency);
		}

		if (!found.isEmpty()) {
			Logger.toConsole("&fFound &8| &a" + String.join("&8, &a", found));

			if (Bukkit.getServer().getPluginManager().getPlugin("PlaceholderAPI") == null) {
				hasPlaceholderAPI = false;
			} else {
				new PlaceholderManager().register();
				hasPlaceholderAPI = true;
			}

			return;
		}

		if (!missing.isEmpty()) {
			Logger.toConsole("&fMissing &8| &c" + String.join("&8, &c", missing));
		}

	}

	@Override
	public void onDisable() {
		// Plugin shutdown logic
		CommandAPI.onDisable();
	}

	public static Plugin getPlugin() {
		return plugin;
	}

	public static boolean isHasPlaceholderAPI() {
		return hasPlaceholderAPI;
	}
}
