package com.mainpiper.app.args;

import org.apache.commons.cli.CommandLine;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class Config {

    private String scriptName;
    private String defaultDownloadDirectory = "Download";

    private CommandLine cli;

    public void extendConfig(CommandLine cliContent) {
        cli = cliContent;

    }

    public void displayDebug() {
        log.debug("input : {}", scriptName);

    }

}
