package com.mainpiper.app.model.mangas;

import com.mainpiper.app.model.AbstractManga;

public class SimpleManga extends AbstractManga {
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
