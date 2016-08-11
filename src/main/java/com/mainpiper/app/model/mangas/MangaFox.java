package com.mainpiper.app.model.manga;

import com.mainpiper.app.model.manga.AbstractManga;

/**
 * @author MainPiper Dev
 * @date 22/07/2016.
 */

public class MangaFox extends AbstractManga {
    
	//TODO add volumes ?? 
    public MangaFox(String mangaName) {
        super(mangaName);
        DORSS = true;
        language = "English";
    }
    
}
