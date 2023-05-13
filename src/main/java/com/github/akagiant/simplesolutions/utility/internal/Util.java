package com.github.akagiant.simplesolutions.utility.internal;

import com.github.akagiant.simplesolutions.SimpleSolutions;
import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.RegisteredCommand;
import org.bukkit.Material;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

public class Util {

	private Util() {
		//no instance
	}
	
	public static int getPermissionsCount() {
		return SimpleSolutions.getPlugin().getDescription().getPermissions().size();
	}

	public static int getCommandAliasesCount() {
		int amount = 0;
		for (RegisteredCommand cmd : CommandAPI.getRegisteredCommands()) {
			if (cmd.aliases() != null) { amount += cmd.aliases().length; }
		}
		return amount;
	}

	public static final String PERMISSION_KEY = "simpleexp";

	public static double round(double value, int places) {
		if (places < 0) throw new IllegalArgumentException();

		BigDecimal bd = BigDecimal.valueOf(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}

	public static double random(double min, double max) {
		Random r = new Random();
		return min + (max - min) * r.nextDouble();
	}

	public static String capitalize(String str)
	{
		if (str == null || str.length() == 0) return str;
		return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
	}

	public static String capitaliseMaterial(Material material) {
		return capitaliseMultiWord(material.toString());
	}

	public static String capitaliseMultiWord(String string) {
		String[] split = string.split("_");

		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < split.length; i++) {
			if (i + 1 == split.length) {
				// No space
				stringBuilder.append(capitalize(split[i]));
			} else {
				// With Space
				stringBuilder.append(capitalize(split[i] + " "));
			}
		}

		return stringBuilder.toString();
	}

}
