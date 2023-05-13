/*
 * This file is part of SimpleExp, licensed under the MIT License.
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
import com.github.akagiant.simplesolutions.utility.internal.Logger;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class UpdateChecker {

	String url = "https://raw.githubusercontent.com/AkaGiant/SimpleSolutions/dev/pom.xml";

	double current = Double.parseDouble(
		SimpleSolutions.getPlugin().getDescription().getVersion()
			.replaceAll("[^\\d.]", "").trim()
	);
	double latest = 0.0;

	public void run() {
		this.latest = getGithubLatest();

		if (this.latest == -1) return;

		if (current > latest) {
			Logger.toConsole("&fYou seemingly have a newer version than is currently released");
			Logger.toConsole("&fCurrent: &b" + current + " &8| &fLatest: &c" + latest);
			Logger.toConsole("&m------------------------------------");
			return;
		}

		if (current == latest) {
			Logger.toConsole("&aNo Updates Found");
			Logger.toConsole("&fCurrent: &b" + current + " &8| &fLatest: &c" + latest);
			Logger.toConsole("&m------------------------------------");
			return;
		}

		Logger.toConsole("&aUpdates Found");
		Logger.toConsole("&fCurrent: &b" + current + " &8| &fLatest: &c" + latest);
		Logger.toConsole("&fURL Will be provided soon.");
		Logger.toConsole("&m------------------------------------");
	}

 	private double getGithubLatest() {
	    InputStream in;
	    try {
		    in = new URL(url).openStream();
	    } catch (IOException e) {
		    Logger.severe("&fUnable to check for updates!");
			return -1;
	    }

	    NodeList nodeList;

	    try {
		    nodeList = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(in).getElementsByTagName("version");
	    } catch (SAXException | IOException | ParserConfigurationException e) {
		    Logger.severe("&fUnable to get and find the Version identifier for the latest version.");
			return -1;
	    }

	    String latestVersion = nodeList.item(0).getFirstChild().toString().replaceAll("[^\\d.]", "").trim();
		return Double.parseDouble(latestVersion);
    }
}
