package com.mainpiper.app.main;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.ParseException;

import com.mainpiper.app.args.CliOptions;
import com.mainpiper.app.args.Config;
import com.mainpiper.app.args.Reader;
import com.mainpiper.app.display.Display;
import com.mainpiper.app.exceptions.TerminateBatchException;
import com.mainpiper.app.exceptions.TerminateScriptProperly;
import com.mainpiper.app.services.ServiceUpdate;
import com.mainpiper.app.services.Services;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {
    public static final String APP_VERSION = "1.4.0";

    public static void main(String[] args) {
        CommandLine commandLine = null;
        Services service = null;
        String mangaName = null;
        int cores = Runtime.getRuntime().availableProcessors()*2;
        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", Integer.toString(cores));
        Display.displayTitle("Manga Downloader " + APP_VERSION);

        try {
            CommandLineParser cliParser = new DefaultParser();
            commandLine = cliParser.parse(CliOptions.getInstance(), args);

            if (args.length > 0) {
                Reader reader = new Reader();
                Config conf = reader.generateConfig(commandLine);

                Boolean cliHasHelp = commandLine.hasOption(CliOptions.OPT_HELP);
                Boolean cliHasVersion = commandLine.hasOption(CliOptions.OPT_VERSION);
                Boolean cliHasChapter = commandLine.hasOption(CliOptions.OPT_CHAPTER);

                if (args[0].equals(CliOptions.OPT_UPDATE_LONG) || args[0].equals(CliOptions.OPT_UPDATE_SHORT)) {
                	String msg = "Update in progress...";
                	log.info(msg);
                	Display.displayInfo(msg);
                    ServiceUpdate.update(conf);
                } else {
                    mangaName = args[0];

                    service = new Services(mangaName, conf);

                    if (cliHasHelp) {
                        printUsageAndExit("USAGE");
                    } else if (cliHasVersion) {
                        printVersion();
                    } else if (cliHasChapter) {
                        service.downloadChapters();
                    } else {
                        service.downloadManga(true);
                    }
                }
            } else {
                printUsageAndExit("Wrong usage");
            }
        } catch (ParseException e) {
            log.debug("Erreur in the Cli parsing : ", e);
            printUsageAndExit("Wrong usage");
        } catch (TerminateScriptProperly ts) {
            log.info(ts.getMessage());
            Display.displayInfo(ts.getMessage());
            System.exit(0);
        } catch (TerminateBatchException ex) {
            log.error("Batch failure with code: {}\n", ex.getExitCode(), ex);
            Display.displayError(ex.getMessage());
            System.exit(ex.getExitCode());
        } catch (Exception ex) {
            log.error("Unknown exception: ", ex);
            Display.displayError("Unknown exception occured, we apologize for this !\nPLease contact the support !");
            System.exit(TerminateBatchException.EXIT_CODE_UNKNOWN);
        }
    }

    private static void printVersion() {
        Display.displayInfo("Manga downloader v" + Main.APP_VERSION);
        System.exit(0);
    }

    private static void printUsageAndExit(String errorMsg) {
        Display.displayError(errorMsg);
        HelpFormatter hf = new HelpFormatter();
        hf.setWidth(768);
        hf.printHelp("manga_downloader <mangas name>", CliOptions.getInstance(), true);
        System.exit(1);
    }
}
