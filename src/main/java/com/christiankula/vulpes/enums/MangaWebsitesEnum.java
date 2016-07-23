package com.christiankula.vulpes.enums;

public enum MangaWebsitesEnum {
    MANGAFOX("mf");

    private final String cliShortcut;

    public String cliShortcut() {
        return cliShortcut;
    }

    MangaWebsitesEnum(String cliShortcut) {
        this.cliShortcut = cliShortcut;
    }
}