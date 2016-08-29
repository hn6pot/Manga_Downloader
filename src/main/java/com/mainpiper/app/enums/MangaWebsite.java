package com.mainpiper.app.enums;

import lombok.Getter;

@Getter
public enum MangaWebsite {
	// TODO revise website shortcuts

	MANGAFOX("Manga Fox", "mfox"), JAPSCAN("Japscan", "japscan"), LELSCAN("lelscan", "lelscan"), LIRESCAN("lirescan",
			"lirescan"), LECTURE_EN_LIGNE("lecture-en-ligne", "l-e-l");

	private final String name;
	private final String cliShortcut;

	public String getCliShortcut() {
		return this.cliShortcut;
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

	MangaWebsite(String name, String cliShortcut) {
		this.name = name;
		this.cliShortcut = cliShortcut;
	}
}