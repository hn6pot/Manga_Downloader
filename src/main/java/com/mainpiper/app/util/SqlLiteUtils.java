package com.mainpiper.app.util;

public class SqlLiteUtils {
    private static final String sqlInitializationRequest = "CREATE TABLE Manga (";
    private static final String PrimaryKey = " Id INT PRIMARY KEY     NOT NULL,";
    private static final String nameParameter = "Name    CHAR(50)        NOT NULL,";
    private static final String webSourceParameter = "WebSource  CHAR(50) NOT NULL,";

    public static String getInitSqlRequest() {
        return null;
    }
}
