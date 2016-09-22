package com.mainpiper.app.enums;

import com.mainpiper.app.exceptions.TerminateBatchException;
import com.mainpiper.app.net.connectors.JapscanConnector;
import com.mainpiper.app.net.connectors.LelScanConnector;
import com.mainpiper.app.net.connectors.LireScanConnector;
import com.mainpiper.app.net.connectors.MangaFoxConnector;

import lombok.Getter;

@Getter
public enum MangaWebsite {
    // TODO revise website shortcuts

    MANGAFOX("Manga Fox", "mfox", MangaFoxConnector.class), JAPSCAN("Japscan", "japscan", JapscanConnector.class),
    LELSCAN("Lelscan", "lelscan", LelScanConnector.class), LIRESCAN("Lirescan", "lirescan", LireScanConnector.class),
    LECTURE_EN_LIGNE("lecture-en-ligne", "l-e-l", LelScanConnector.class);

    private final String name;
    private final String cliShortcut;

    private final Class<?> clazz;

    public String getCliShortcut() {
        return cliShortcut;
    }

    public static MangaWebsite getSource(String name) {
        for (MangaWebsite mw : MangaWebsite.values()) {
            if (name.equals(mw.getCliShortcut())) {
                return mw;
            }
        }
        throw new TerminateBatchException(TerminateBatchException.EXIT_CODE_WRONG_SOURCE_PROVIDED,
                "You provided an unknown source");
    }

    @Override
    public String toString() {
        return cliShortcut + " (" + name + ")";
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
        return name;
    }

    public Class<?> getConstructorClass() {
        return clazz;
    }
}
