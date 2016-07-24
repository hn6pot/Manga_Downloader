package com.mainpiper.app.main;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.mainpiper.app.enums.CliArgumentsEnum;
import com.mainpiper.app.enums.MangaWebsitesEnum;
import com.mainpiper.app.services.DownloadService;
import com.mainpiper.app.services.impl.MangaFoxDowloadService;
import com.mainpiper.app.utils.Help;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Christian Kula
 *         22/07/2016
 */

@Slf4j
public class Main {
    public static final String APP_VERSION = "1.0";

    public static void main(String[] args) {
        CommandLineParser parser = new DefaultParser();

        VulpesCliOptions options = VulpesCliOptions.getInstance();
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("ant", options);

        try {
            CommandLine line = parser.parse(options, args);
            System.out.println(line.hasOption("s"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        DownloadService ds;
        String cli = "";
        for (String s : args) {
            cli += s + " ";
        }
        System.out.println("<> : " + cli.trim());
        
        log.info("toto");
//        String mangaName = null;
//        if (args.length > 0) {
//            mangaName = args[0];
//        }
//        switch (args.length) {
//            case 0:
//                Help.printHelp();
//                break;
//
//            case 1:
//                if (CliArgumentsEnum.HELP.cliArg().equalsIgnoreCase(mangaName)) {
//                    Help.printHelp();
//                } else {
//                    ds = new MangaFoxDowloadService(mangaName);
//                    ds.downloadManga();
//                }
//                break;
//
//            case 3: {
//                int[] indexes = identifyCliOptions(args);
//
//
//                int wsArgIndex = indexes[0];
//                int volumeArgIndex = indexes[1];
//
//                for (int i = 0; i < args.length; i++) {
//                    if (CliArgumentsEnum.SITE.cliArg().equalsIgnoreCase(args[i])) {
//                        wsArgIndex = i;
//                    } else if (CliArgumentsEnum.VOLUME.cliArg().equalsIgnoreCase(args[i])) {
//                        volumeArgIndex = i;
//                    }
//                }
//
//                if (wsArgIndex >= 1 && wsArgIndex + 1 < args.length) {
//                    String site = args[wsArgIndex + 1];
//
//                    ds = getDownloadServiceByWebsite(site, mangaName);
//                    if (ds != null) {
//                        ds.downloadManga();
//                    } else {
//                        //TODO WEBSITE UNKNOWN
//                    }
//                } else if (volumeArgIndex >= 1 && volumeArgIndex + 1 < args.length) {
//                    String volumeNumber = args[volumeArgIndex + 1];
//
//                    ds = new MangaFoxDowloadService(mangaName);
//                    ds.downloadVolume(volumeNumber);
//                } else {
//                    Help.printHelp();
//                }
//                break;
//            }
//
//            case 5: {
//                int wsArgIndex = -1;
//                int volumeArgIndex = -1;
//                int chapterArgIndex = -1;
//
//                for (int i = 0; i < args.length; i++) {
//                    if (CliArgumentsEnum.SITE.cliArg().equalsIgnoreCase(args[i])) {
//                        wsArgIndex = i;
//                    } else if (CliArgumentsEnum.VOLUME.cliArg().equalsIgnoreCase(args[i])) {
//                        volumeArgIndex = i;
//                    } else if (CliArgumentsEnum.CHAPTER.cliArg().equalsIgnoreCase(args[i])) {
//                        chapterArgIndex = i;
//                    }
//                }
//
//                if (wsArgIndex >= 1 && wsArgIndex + 1 < args.length &&
//                        volumeArgIndex >= 1 && volumeArgIndex + 1 < args.length) {
//
//                } else if (volumeArgIndex >= 1 && volumeArgIndex + 1 < args.length &&
//                        chapterArgIndex >= 1 && chapterArgIndex + 1 < args.length) {
//
//                } else {
//
//                }
//
//
//                break;
//            }
//
//            case 7:
//                int wsArgIndex = -1;
//                int volumeArgIndex = -1;
//                int chapterArgIndex = -1;
//
//                for (int i = 0; i < args.length; i++) {
//                    if (CliArgumentsEnum.SITE.cliArg().equalsIgnoreCase(args[i])) {
//                        wsArgIndex = i;
//                    } else if (CliArgumentsEnum.VOLUME.cliArg().equalsIgnoreCase(args[i])) {
//                        volumeArgIndex = i;
//                    } else if (CliArgumentsEnum.CHAPTER.cliArg().equalsIgnoreCase(args[i])) {
//                        chapterArgIndex = i;
//                    }
//                }
//
//                if (wsArgIndex >= 1 && wsArgIndex + 1 < args.length &&
//                        volumeArgIndex >= 1 && volumeArgIndex + 1 < args.length &&
//                        chapterArgIndex >= 1 && chapterArgIndex + 1 < args.length) {
//
//                    String site = args[wsArgIndex + 1];
//
//                    ds = getDownloadServiceByWebsite(site, mangaName);
//                    if (ds != null) {
//                        String volumeNumber = args[volumeArgIndex + 1];
//                        String chapterNumber = args[chapterArgIndex + 1];
//
//                        ds.downloadChapter(volumeNumber, chapterNumber);
//                    }
//
//                } else {
//                    Help.printHelp();
//                }
//                break;
//
//            default:
//                Help.printHelp();
//                break;

//        }
    }

    /**
     * Identify indexes of CLI options
     * indexes[0] = -s
     * indexes[1] = -v
     * indexes[2] = -c
     *
     * if indexes[i] = -1 then cli options is not present
     */
    private static int[] identifyCliOptions(String[] args) {
        int[] indexes = {-1, -1, -1};

        for (int i = 0; i < args.length; i++) {
            if (CliArgumentsEnum.SITE.getCliArg().equalsIgnoreCase(args[i])) {
                indexes[0] = i;
            } else if (CliArgumentsEnum.VOLUME.getCliArg().equalsIgnoreCase(args[i])) {
                indexes[1] = i;
            } else if (CliArgumentsEnum.CHAPTER.getCliArg().equalsIgnoreCase(args[i])) {
                indexes[2] = i;
            }
        }

        return indexes;
    }

    private static DownloadService getDownloadServiceByWebsite(String ws, String mangaName) {
        if (MangaWebsitesEnum.MANGAFOX.getCliShortcut().equalsIgnoreCase(ws)) {
            return null;
            //new MangaFoxDowloadService(mangaName);
        }

        return null;
    }
}
