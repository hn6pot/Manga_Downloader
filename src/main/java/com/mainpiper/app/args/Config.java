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

    private String scriptName;
    private String defaultDownloadDirectory = "Download";

    private String apiUsername = "toto";

    private CommandLine cli;
    private MangaWebsite webSources;

    public void extendConfig(CommandLine cliContent) throws ParseException {
        cli = cliContent;
        String cliWebSource = cli.getOptionValue(CliOptions.OPT_SOURCE);
        if (cliWebSource == CliOptions.defaultValue) {
            webSources = null;
        } else {
            try {
                webSources = MangaWebsite.getSource(cliWebSource);
            } catch (TerminateBatchException n) {
                log.info("Web sources provided is unknown");
                throw new org.apache.commons.cli.ParseException("Unknown Web Source");
            } catch (Exception e) {
                log.debug("An unexpected error occured : ", e);
                throw new TerminateBatchException(TerminateBatchException.EXIT_CODE_UNKNOWN, e);
            }
        }

    }

    public void displayDebug() {
        log.debug("input : {}", scriptName);

    }

}
