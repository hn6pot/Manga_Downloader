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

    private Boolean checkManga;
    private Boolean checkApi;

    public void extendConfig(CommandLine cliContent) throws ParseException {
        cli = cliContent;
        checkManga =
                cli.hasOption(CliOptions.OPT_CHECK) ? Boolean.valueOf(cli.getOptionValue(CliOptions.OPT_CHECK)) : true;

        checkApi = cli.hasOption(CliOptions.OPT_CHECK_API)
                ? Boolean.valueOf(cli.getOptionValue(CliOptions.OPT_CHECK_API)) : false;

        if (cli.hasOption(CliOptions.OPT_SOURCE)) {
            String cliWebSource = cli.getOptionValue(CliOptions.OPT_SOURCE);
            try {
                webSources = MangaWebsite.getSource(cliWebSource);
            } catch (TerminateBatchException n) {
                log.info("Web sources provided is unknown");
                throw new org.apache.commons.cli.ParseException("Unknown Web Source");
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
        if (checkManga)
            log.debug("Check Manga Activated");
        if (checkApi)
            log.debug("Check Api Activated");
        log.debug("Web Source : {}", webSources.getName());
        log.debug("Api UserName : {}", apiUsername);
        log.debug("Api Password : {}", apiPassword);

    }

}
