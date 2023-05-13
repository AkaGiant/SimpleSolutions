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

package com.github.akagiant.simplesolutions.utility;

import com.github.akagiant.simplesolutions.SimpleSolutions;
import com.github.akagiant.simplesolutions.utility.internal.ColorManager;
import com.github.akagiant.simplesolutions.utility.internal.ConfigManager;
import com.github.akagiant.simplesolutions.utility.internal.ConfigUtil;
import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class MessageManager {

	private MessageManager() {
		//no instance
	}

	public static String getConfigMessageFailure() {
		return ConfigUtil.getString(ConfigManager.messages, "message-failure");
	}

	public static void sendMessage(CommandSender sender, String path, String... args) {
		List<String> messages = ConfigUtil.getStringList(ConfigManager.messages, path + ".message");

		for (String message : messages) {

			message = message.replace("[prefix]", ConfigUtil.getString(ConfigManager.messages, "prefix"));

			// Handles argument replacements for internal placeholders.
			for (int i = 0; i < args.length; i++) {
				message = message.replace("[A" + (i + 1) + "]", args[i]);
			}

			// ConsoleCommandSender can only be sent a standard message with no PlaceholderAPI implementation.
			if (sender instanceof ConsoleCommandSender) {
				sender.sendMessage(message);
				continue;
			}

			Player player = (Player) sender;

			// Handles PlaceholderAPI Placeholders.
			if (SimpleSolutions.isHasPlaceholderAPI()) {
				message = PlaceholderAPI.setPlaceholders(player, message);
			}

			// Handles the differentiation between a standard message and an actionbar message.
			String type = ConfigUtil.getStringOptional(ConfigManager.messages, path + ".message-type").orElse(null);

			// If not message type is provided, it will default to a standard message.
			if (type == null || type.equalsIgnoreCase("MESSAGE")) {
				player.sendMessage(ColorManager.formatColours(message));
			} else {
				player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
			}

			// Handles sending a sound to a player if one is provided.
			Sound sound = ConfigUtil.getSoundOptional(ConfigManager.messages, path + ".sound-effect").orElse(null);
			if (sound == null) continue;
			player.playSound(player, sound, 1, 1);
		}

	}

}
