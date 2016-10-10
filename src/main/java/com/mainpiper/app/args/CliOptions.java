package com.mainpiper.app.args;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

import com.mainpiper.app.enums.MangaWebsite;

public class CliOptions extends Options {

    private static final long serialVersionUID = 1L;
    public static final String OPT_SOURCE = "s";
    public static final String OPT_VOLUME = "v";
    public static final String OPT_CHAPTER = "c";
    public static final String OPT_CBZ = "cbz";
    public static final String OPT_CHECK = "nocheck";
    public static final String OPT_CHECK_DIRECTORY = "check_directory";
    public static final String OPT_CHECK_API = "check_api";
    public static final String OPT_HELP = "help";
    public static final String OPT_VERSION = "version";
    public static final String OPT_UPDATE_LONG = "--update";
    public static final String OPT_UPDATE_SHORT = "-u";
    public static final String defaultValue = "Default";

    private static CliOptions instance = new CliOptions();

    public static CliOptions getInstance() {
        return instance;
    }

    private CliOptions() {
        Option source = Option.builder(OPT_SOURCE).hasArg(true).optionalArg(false).argName("source")
                .desc("Specify the website you want to download the mangas from\nPossible sources : "
                        + MangaWebsite.listValues() + "\n" + "If not specified, defaults to Manga Fox")
                .required(false).build();

        Option volume = Option.builder(OPT_VOLUME).hasArg(true).optionalArg(false).argName("volume number")
                .desc("Specify the volume you want to download").required(false).build();

        Option chapter = Option.builder(OPT_CHAPTER).hasArg(true).optionalArg(false).argName("chapter number")
                .desc("Specify the chapter you want to download").required(false).build();

        Option cbz = Option.builder(OPT_CBZ).hasArg(false).argName("cbz").required(false)
                .desc("Convert Output on CBZ format").required(false).build();

        Option check = Option.builder(OPT_CHECK).hasArg(false)
                .desc("Do not check if chapters has already been downloaded").required(false).build();

        Option checkDirectory = Option.builder(OPT_CHECK_DIRECTORY).hasArg(false)
                .desc("Check directory content to update script memory").required(false).build();

        Option checkApi = Option.builder(OPT_CHECK_API).hasArg(false).desc("To be implemented").required(false).build();

        Option help = Option.builder(OPT_HELP).hasArg(false).desc("Display the help section").required(false).build();

        Option version =
                Option.builder(OPT_VERSION).hasArg(false).desc("Display the script version").required(false).build();

        addOption(source);
        addOption(volume);
        addOption(chapter);
        addOption(cbz);
        addOption(check);
        addOption(checkDirectory);
        addOption(checkApi);
        addOption(help);
        addOption(version);
    }

}
