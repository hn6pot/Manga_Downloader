package com.mainpiper.app.args;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Config {

    private String Default_S3_Output;
    private String S3_Alarms_Path;

    private String dbURL;
    private String MasterUsername;
    private String MasterUserPassword;

    private String sourceTableName;
    private String csoTableName;
    private String rpaTableName;

    private CliOptions cli;
    private String output;

    // public void extendConfig(CliOptions cli) {
    // this.cli = cli;
    // String outputTemp = cli.getOutput();
    // if (outputTemp == null) {
    // output = Default_S3_Output;
    // } else {
    // output = outputTemp;
    // }
    // }
    //
    // public void displayDebug() {
    // log.debug("input : {}", args.getInput());
    // log.debug("output : {}", output);
    // log.debug("type : {}", args.getType());
    // log.debug("special Values : {}", args.getSpecialValues());
    // log.debug("Default Output : {}", Default_S3_Output);
    // log.debug("Alarms file : {}", S3_Alarms_Path);
    // log.debug("Redshift Url : {}", dbURL);
    // log.debug("Source Table : {}", sourceTableName);
    // log.debug("Rpa Table : {}", rpaTableName);
    // log.debug("Cso Table : {}", csoTableName);
    // }

    public String getDefaultS3Output() {
        return Default_S3_Output;
    }

    public String getDefaultAlarmsPath() {
        return S3_Alarms_Path;
    }

    public String getRedShiftDBUrl() {
        return dbURL;
    }

    public String getRedshiftMasterUsername() {
        return MasterUsername;
    }

    public String getRedshiftMasterPassword() {
        return MasterUserPassword;
    }

    public String getSourceTableName() {
        return sourceTableName;
    }

    public String getRpaTableName() {
        return rpaTableName;
    }

    public String getCsoTableName() {
        return csoTableName;
    }

    public CliOptions getCliOptions() {
        return cli;
    }

}
