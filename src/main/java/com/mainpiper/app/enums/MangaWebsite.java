package com.mainpiper.app.enums;

import com.mainpiper.app.exceptions.UnknownSourceException;
import com.mainpiper.app.net.connectors.JapscanConnector;
import com.mainpiper.app.net.connectors.LelScanConnector;
import com.mainpiper.app.net.connectors.LireScanConnector;
import com.mainpiper.app.net.connectors.MangaFoxConnector;

import lombok.Getter;

@Getter
public enum MangaWebsite {
	// TODO revise website shortcuts

	MANGAFOX("Manga Fox", "mfox", MangaFoxConnector.class), JAPSCAN("Japscan", "japscan",
			JapscanConnector.class), LELSCAN("lelscan", "lelscan", LelScanConnector.class), LIRESCAN("lirescan",
					"lirescan", LireScanConnector.class), LECTURE_EN_LIGNE("lecture-en-ligne", "l-e-l",
							LelScanConnector.class);

	private final String name;
	private final String cliShortcut;

	private final Class<?> clazz;

	public String getCliShortcut() {
		return this.cliShortcut;
	}

	public static MangaWebsite getSource(String name) {
		for (MangaWebsite mw : MangaWebsite.values()) {
			if (name.equals(mw.getCliShortcut())) {
				return mw;
			}
		}
		throw new UnknownSourceException();
	}

	@Override
	public String toString() {
		return this.cliShortcut + " (" + this.name + ")";
	}

	public static String listValues() {
		String possibleValues = new String();
		for (MangaWebsite mw : MangaWebsite.values()) {
			possibleValues += mw + "\n";
		}
		return possibleValues;
	}

	MangaWebsite(String name, String cliShortcut, Class<?> clazz) {
		this.name = name;
		this.cliShortcut = cliShortcut;
		this.clazz = clazz;
	}

	public String getName() {
		return this.name;
	}

	public Class<?> getConstructorClass() {
		return this.clazz;
	}
}