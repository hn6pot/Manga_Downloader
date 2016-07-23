package com.christiankula.vulpes.main;

import org.apache.commons.cli.Options;

/**
 * @author Christian Kula
 *         23/07/2016
 */
public class VulpesCliOptions extends Options {
    private static VulpesCliOptions instance = new VulpesCliOptions();

    public static VulpesCliOptions getInstance() {
        return instance;
    }

    private VulpesCliOptions() {
        addOption("s", true, "display current time");
        addOption("v", true, "display current time");
        addOption("c", true, "display current time");
        addOption("help", false, "display help");
    }
}
