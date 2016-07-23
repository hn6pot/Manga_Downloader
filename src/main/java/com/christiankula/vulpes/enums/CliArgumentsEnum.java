package com.christiankula.vulpes.enums;

/**
 * @author Christian Kula
 * @date 22/07/2016
 */
public enum CliArgumentsEnum {
    VOLUME("-v"),
    CHAPTER("-c"),
    HELP("-help"),
    SITE("-s");


    private String cliArg;

    public String cliArg(){
        return this.cliArg;
    }

    CliArgumentsEnum(String cliArg) {
        this.cliArg = cliArg;
    }

}
