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

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ColorManager {

	private ColorManager() {
		//no instance
	}

	public static String formatColours(String msg) {
		Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");
		String[] bukkitVer = Bukkit.getBukkitVersion().split("-")[0].split("\\.");
		double converted = Double.parseDouble(bukkitVer[0] + "." + bukkitVer[1]);
		if (converted >= 1.16D) {
			for(Matcher match = pattern.matcher(msg); match.find(); match = pattern.matcher(msg)) {
				String color = msg.substring(match.start(), match.end());
				msg = msg.replace(color, ChatColor.of(color) + "");
			}
		}

		return org.bukkit.ChatColor.translateAlternateColorCodes('&', msg);
	}

	public static List<String> formatColours(List<String> unformatted) {
		List<String> formatted = new ArrayList<>();
		for (String str : unformatted) {
			formatted.add(formatColours(str));
		}
		return formatted;
	}

}
