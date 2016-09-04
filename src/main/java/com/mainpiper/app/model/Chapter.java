package com.mainpiper.app.model;

import lombok.Getter;

@Getter
public class Chapter implements Comparable<Chapter> {

    private final String number;

    private final int pagesCount;

    private final String associatedVolume;

    public Chapter(String number) {
        this.number = number;
        pagesCount = 0;
        associatedVolume = null;
    }

    public Chapter(String number, String associatedVolume) {
        this.number = number;
        pagesCount = 0;
        this.associatedVolume = associatedVolume;
    }

    @Override
    public int compareTo(Chapter o) {
        // TODO implement correct 'compareTo' method
        return 0;
    }
}
