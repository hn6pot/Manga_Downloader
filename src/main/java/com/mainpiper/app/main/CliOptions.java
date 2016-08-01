package com.mainpiper.app.main;

import com.mainpiper.app.enums.MangaWebsite;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class CliOptions extends Options {

    public static final String OPT_SOURCE = "s";
    public static final String OPT_VOLUME = "v";
    public static final String OPT_CHAPTER = "c";
    public static final String OPT_HELP = "help";
    public static final String OPT_VERSION = "version";

    private static CliOptions instance = new CliOptions();

    public static CliOptions getInstance() {
        return instance;
    }

    private CliOptions() {
        String possibleValues = "";
        for (MangaWebsite mw : MangaWebsite.values()) {
            possibleValues += mw;
        }

        Option source = Option.builder(OPT_SOURCE)
                .hasArg(true)
                .optionalArg(false)
                .argName("source")
                .desc("Specify the website you want to download the manga from\nPossible values : " + possibleValues + "\n" +
                        "If not specified, defaults to Manga Fox")
                .required(false)
                .build();

        Option volume = Option.builder(OPT_VOLUME)
                .hasArg(true)
                .optionalArg(false)
                .argName("volume number")
                .desc("Specify the volume you want to download")
                .required(false)
                .build();

        Option chapter = Option.builder(OPT_CHAPTER)
                .hasArg(true)
                .optionalArg(false)
                .argName("chapter number")
                .desc("Specify the chapter you want to download")
                .required(false)
                .build();

        Option help = Option.builder(OPT_HELP)
                .hasArg(false)
                .desc("Display the help section")
                .required(false)
                .build();

        Option version = Option.builder(OPT_VERSION)
                .hasArg(false)
                .desc("Display the script version")
                .required(false)
                .build();

        addOption(source);
        addOption(volume);
        addOption(chapter);
        addOption(help);
        addOption(version);
    }
}
