package com.mainpiper.app.models;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;

/**
 * @author MainPiper Dev
 * @date 22/07/2016.
 */
@Getter
@Setter
public abstract class Manga {
    protected String name;
    protected static String language;

    protected Set<Chapter> chapter;
    
    protected static Boolean DORSS;
    
    public Manga(String mangaName) {
        this.name = mangaName;
    }

	public static String getLanguage() {
		return language;
	}

	public static void setLanguage(String language) {
		Manga.language = language;
	}

	public static Boolean getDORSS() {
		return DORSS;
	}

	public static void setDORSS(Boolean dORSS) {
		DORSS = dORSS;
	}
    

}
