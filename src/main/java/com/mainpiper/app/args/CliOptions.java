package com.mainpiper.app.args;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

import com.mainpiper.app.enums.MangaWebsite;

public class CliOptions extends Options {

	private static final long serialVersionUID = 1L;
	public static final String OPT_SOURCE = "s";
	public static final String OPT_VOLUME = "v";
	public static final String OPT_CHAPTER = "c";
	public static final String OPT_CHECK = "check";
	public static final String OPT_CHECK_API = "check-api";
	public static final String OPT_HELP = "help";
	public static final String OPT_VERSION = "version";
	public static final String defaultValue = "Default";

	private static CliOptions instance = new CliOptions();

	public static CliOptions getInstance() {
		return instance;
	}

	private CliOptions() {
		Option source = Option.builder(OPT_SOURCE)
				.hasArg(true)
				.optionalArg(false)
				.argName("source")
				.desc("Specify the website you want to download the mangas from\nPossible sources : " + MangaWebsite
						.listValues() + "\n" + "If not specified, defaults to Manga Fox")
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

		Option check = Option.builder(OPT_CHECK).hasArg(false).desc("check something mdr").required(false).build();

		Option checkApi = Option.builder(OPT_CHECK_API).hasArg(false).desc("To be implemented").required(false).build();

		Option help = Option.builder(OPT_HELP).hasArg(false).desc("Display the help section").required(false).build();

		Option version = Option.builder(OPT_VERSION)
				.hasArg(false)
				.desc("Display the script version")
				.required(false)
				.build();

		addOption(source);
		addOption(volume);
		addOption(chapter);
		addOption(check);
		addOption(checkApi);
		addOption(help);
		addOption(version);
	}

}
