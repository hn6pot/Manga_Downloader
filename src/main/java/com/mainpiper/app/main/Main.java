package com.mainpiper.app.main;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.ParseException;
import org.apache.commons.io.FileUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mainpiper.app.args.CliOptions;
import com.mainpiper.app.args.Config;
import com.mainpiper.app.exceptions.TerminateBatchException;
import com.mainpiper.app.service.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {
    public static final String APP_VERSION = "1.0";

    public static void main(String[] args) {
        CommandLine commandLine = null;
        Service service = null;
        String mangaName = null;

        try {
            CommandLineParser cliParser = new DefaultParser();
            commandLine = cliParser.parse(CliOptions.getInstance(), args);

            if (args.length > 0) {
                mangaName = args[0];
                String configPath = ClassLoader.getSystemResource("config").getFile();

                File t = new File(configPath);
                Gson GSON = new GsonBuilder().setPrettyPrinting().create();
                String jsonContent = new String();
                try {
                    jsonContent = FileUtils.readFileToString(t, Charset.forName("UTF-8"));
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Config conf = GSON.fromJson(jsonContent, Config.class);
                conf.extendConfig(commandLine);
                conf.displayDebug();

                Boolean cliHasHelp = commandLine.hasOption(CliOptions.OPT_HELP);
                Boolean cliHasVersion = commandLine.hasOption(CliOptions.OPT_VERSION);
                Boolean cliHasChapter = commandLine.hasOption(CliOptions.OPT_CHAPTER);

                service = new Service(mangaName, conf);

                if (cliHasHelp) {
                    printUsageAndExit("USAGE");
                } else if (cliHasVersion) {
                    printVersion();
                } else if (cliHasChapter) {
                    service.downloadChapters();
                } else {
                    service.downloadManga();
                }
            } else {
                printUsageAndExit("Wrong usage");
            }
        } catch (ParseException e) {
            log.debug("Erreur in the Cli parsing : ", e);
            printUsageAndExit("Wrong usage");
        } catch (TerminateBatchException ex) {
            log.error("Batch failure with code: {}\n", ex.getExitCode(), ex);
            System.exit(ex.getExitCode());
        } catch (Exception ex) {
            log.error("Unknown exception: ", ex);
            System.exit(TerminateBatchException.EXIT_CODE_UNKNOWN);
        }
    }

    private static void printVersion() {
        System.out.println("Manga downloader v" + Main.APP_VERSION);
        System.exit(0);
    }

    private static void printUsageAndExit(String errorMsg) {
        System.out.println(errorMsg);
        HelpFormatter hf = new HelpFormatter();
        hf.setWidth(768);
        hf.printHelp("manga_downloader <mangas name>", CliOptions.getInstance(), true);
        System.exit(1);
    }
}
