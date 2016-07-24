package com.mainpiper.app.enums;

import lombok.Getter;

/**
 * @author Christian Kula
 * @date 22/07/2016
 */

@Getter
public enum CliArgumentsEnum {
    VOLUME("-v"),
    CHAPTER("-c"),
    HELP("-help"),
    SITE("-s");


    private String cliArg;

    CliArgumentsEnum(String cliArg) {
        this.cliArg = cliArg;
    }

}
