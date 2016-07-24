package com.mainpiper.app.main;

import org.apache.commons.cli.Options;

import lombok.Getter;

/**
 * @author Christian Kula
 *         23/07/2016
 */

@Getter
public class VulpesCliOptions extends Options {
	
    private static VulpesCliOptions instance = new VulpesCliOptions();

    private VulpesCliOptions() {
        addOption("s", true, "display current time");
        addOption("v", true, "display current time");
        addOption("c", true, "display current time");
        addOption("help", false, "display help");
    }

	public static VulpesCliOptions getInstance() {
		return instance;
	}

}
