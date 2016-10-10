package com.mainpiper.app.args;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.ParseException;

import com.mainpiper.app.enums.MangaWebsite;
import com.mainpiper.app.exceptions.TerminateBatchException;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class Config {

    private String defaultDownloadDirectory = "Download";

    private String apiUsername = "toto";
    private String apiPassword = "toto";

    private CommandLine cli;
    private MangaWebsite webSources;

    private Boolean cbz;
    private Boolean checkManga;
    private Boolean checkDirectory;
    private Boolean checkApi;

    public void extendConfig(CommandLine cliContent) throws ParseException {
        cli = cliContent;

        cbz = cli.hasOption(CliOptions.OPT_CBZ);
        checkManga = !cli.hasOption(CliOptions.OPT_CHECK);
        checkDirectory = cli.hasOption(CliOptions.OPT_CHECK_DIRECTORY);
        checkApi = cli.hasOption(CliOptions.OPT_CHECK_API);

        if (cli.hasOption(CliOptions.OPT_SOURCE)) {
            String cliWebSource = cli.getOptionValue(CliOptions.OPT_SOURCE);
            try {
                webSources = MangaWebsite.getSource(cliWebSource);
            } catch (TerminateBatchException n) {
                log.info("Web sources provided is unknown");
                throw new TerminateBatchException(TerminateBatchException.EXIT_CODE_WRONG_SOURCE_PROVIDED,
                        "Unknown Web Source Provided");
            } catch (Exception e) {
                log.debug("An unexpected error occured : ", e);
                throw new TerminateBatchException(TerminateBatchException.EXIT_CODE_UNKNOWN, e);
            }
        } else {
            webSources = null;
        }
    }

    public void displayDebug() {
        log.debug("Default Download Directory : {}", defaultDownloadDirectory);
        if (cbz)
            log.debug("Cbz Option Activated");
        if (checkManga)
            log.debug("Check Manga Activated");
        if (checkDirectory)
            log.debug("Check Directory Activated");
        if (checkApi)
            log.debug("Check Api Activated");
        if (webSources != null)
            log.debug("Web Source : {}", webSources.getName());
        log.debug("Api UserName : {}", apiUsername);
        log.debug("Api Password : {}", apiPassword);

    }

}
