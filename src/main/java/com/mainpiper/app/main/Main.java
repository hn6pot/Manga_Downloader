package com.mainpiper.app.main;

import com.mainpiper.app.enums.MangaWebsite;
import com.mainpiper.app.services.DownloadService;
import com.mainpiper.app.services.impl.MangaFoxDowloadService;
import com.mainpiper.app.utils.StringUtils;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;

/**
 * @author Main Piper Dev
 */

//@Slf4j
public class Main {
    public static final String APP_VERSION = "1.0";


    public static void main(String[] args) {
        CommandLine commandLine = null;
        DownloadService ds = null;
        String mangaName = null;

        try {
            CommandLineParser cliParser = new DefaultParser();
            commandLine = cliParser.parse(CliOptions.getInstance(), args);
        } catch (Exception e) {
            printUsageAndExit("Wrong usage");
        }

        CommandLineParser parser = new DefaultParser();

        if (args.length > 0 && StringUtils.isNotEmpty(args[0]) && commandLine != null) {
            mangaName = args[0];

            if (commandLine.hasOption(CliOptions.OPT_HELP)) {
                printUsageAndExit("Help");
            } else if (commandLine.hasOption(CliOptions.OPT_VERSION)) {
                printVersion();
            } else if (commandLine.hasOption(CliOptions.OPT_SOURCE)) {
                ds = getDownloadServiceByWebsite(commandLine.getOptionValue(CliOptions.OPT_SOURCE), mangaName);
            } else {
                ds = new MangaFoxDowloadService(mangaName);
            }

            if (ds != null) {
                if (commandLine.hasOption(CliOptions.OPT_VOLUME) && commandLine.hasOption(CliOptions.OPT_CHAPTER)) {
                    ds.downloadChapter(commandLine.getOptionValue(CliOptions.OPT_VOLUME), commandLine
                            .getOptionValue(CliOptions.OPT_CHAPTER));
                } else if (commandLine.hasOption(CliOptions.OPT_VOLUME)) {
                    ds.downloadVolume(commandLine.getOptionValue(CliOptions.OPT_VOLUME));
                } else {
                    ds.downloadManga();
                }
            } else {
                printUsageAndExit("Unknown source");
            }
        } else {
            printUsageAndExit("Wrong usage");
        }
    }

    private static void printVersion() {
        System.out.println("Manga downloader v" + Main.APP_VERSION);
        System.exit(0);
    }

    private static void printUsageAndExit(String errorMsg) {
        System.out.println(errorMsg);
        new HelpFormatter().printHelp("manga_downloader <manga name>", CliOptions.getInstance(), true);
        System.exit(1);
    }

    private static DownloadService getDownloadServiceByWebsite(String ws, String mangaName) {
        if (MangaWebsite.MANGAFOX.getCliShortcut().equalsIgnoreCase(ws)) {
            return null;
            //new MangaFoxDowloadService(mangaName);
        }

        return null;
    }
}
