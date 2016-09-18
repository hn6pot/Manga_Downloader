package com.mainpiper.app.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class Time {

    private final long initialTime;

    public Time() {
        initialTime = System.currentTimeMillis();
    }

    public String getFinalTime() {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(System.currentTimeMillis() - initialTime), ZoneOffset.UTC)
                .toLocalTime().toString();
    }

}
