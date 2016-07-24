package com.mainpiper.app.enums;

import lombok.Getter;

@Getter
public enum MangaWebsitesEnum {
	//FIXME check enum syntax
    MANGAFOX("mfox"),
	JAPSCAN("japscan"),
	LELSCAN("lelscan"),
	LIRESCAN("lirescan"),
	LECTURE_EN_LIGNE("lecture-en-ligne");
	
    private final String cliShortcut;

    MangaWebsitesEnum(String cliShortcut) {
        this.cliShortcut = cliShortcut;
    }
}