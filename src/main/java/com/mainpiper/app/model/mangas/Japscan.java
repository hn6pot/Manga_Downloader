package com.mainpiper.app.model.manga;

import com.mainpiper.app.model.manga.AbstractManga;

public class Japscan extends AbstractManga {
    
    public Japscan(String mangaName) {
        super(mangaName);
        DORSS = false;
        language = "French";
    }

}
