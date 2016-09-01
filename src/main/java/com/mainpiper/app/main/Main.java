package com.mainpiper.app.main;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;

import com.mainpiper.app.args.CliOptions;
import com.mainpiper.app.service.Service;

// @Slf4j
public class Main {
	public static final String APP_VERSION = "1.0";

	public static void main(String[] args) {
		CommandLine commandLine = null;
		Service service = null;
		String mangaName = null;

		try {
			CommandLineParser cliParser = new DefaultParser();
			commandLine = cliParser.parse(CliOptions.getInstance(), args);
		} catch (Exception e) {
			printUsageAndExit("Wrong usage");
		}

		if (args.length > 0) {
			mangaName = args[0];

			service = new Service(mangaName, null);

			if (commandLine.hasOption(CliOptions.OPT_HELP)) {
				printUsageAndExit("Help");
			} else if (commandLine.hasOption(CliOptions.OPT_VERSION)) {
				printVersion();
			}

			if (commandLine.hasOption(CliOptions.OPT_VOLUME) && commandLine.hasOption(CliOptions.OPT_CHAPTER)) {
				service.downloadChapters(commandLine.getOptionValue(CliOptions.OPT_CHAPTER));
			} else {
				service.downloadManga();
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
		HelpFormatter hf = new HelpFormatter();
		hf.setWidth(768);
		hf.printHelp("manga_downloader <mangas name>", CliOptions.getInstance(), true);
		System.exit(1);
	}
}
