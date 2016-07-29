package com.mainpiper.app.models.MangaModels;

import com.mainpiper.app.models.Manga;

/**
 * @author MainPiper Dev
 * @date 22/07/2016.
 */

public class MangaFox extends Manga {
    
	//TODO add volumes ?? 
    public MangaFox(String mangaName) {
        super(mangaName);
        DORSS = true;
        language = "English";
    }
    
}
