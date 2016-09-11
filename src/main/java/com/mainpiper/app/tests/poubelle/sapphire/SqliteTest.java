package com.mainpiper.app.tests.poubelle.sapphire;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqliteTest {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        Connection c = DriverManager.getConnection("jdbc:sqlite:memory.db");
        System.out.println("Opened database successfully");
    }
}
