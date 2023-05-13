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

public class Logger {

	private Logger() {
		//no instance
	}

	public static void warn(String msg) {
		Bukkit.getConsoleSender().sendMessage(
			ColorManager.formatColours("&8[&6" + SimpleSolutions.getPlugin().getName() + " &6&lWARN&8] " + msg)
		);
	}


	public static void severe(String msg) {
		Bukkit.getConsoleSender().sendMessage(
			ColorManager.formatColours("&8[&c" + SimpleSolutions.getPlugin().getName() + " &c&lSEVERE&8] &f" + msg)
		);
	}

	public static void toConsole(String msg) {
		Bukkit.getConsoleSender().sendMessage(
			ColorManager.formatColours("&8[&b" + SimpleSolutions.getPlugin().getName() + "&8] " + msg)
		);
	}

}
