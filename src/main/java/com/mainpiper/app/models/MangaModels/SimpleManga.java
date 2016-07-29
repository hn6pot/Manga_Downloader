package com.mainpiper.app.models.MangaModels;

import com.mainpiper.app.models.Manga;

public class SimpleManga extends Manga{
	/**
	 * 
	 * Mangas with only chapter structure and no Rss
	 * 
	 */
    
    public SimpleManga(String mangaName) {
        super(mangaName);
        DORSS = false;
        language = "French";
    }
}
