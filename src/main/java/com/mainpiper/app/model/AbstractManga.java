package com.mainpiper.app.model.manga;

import com.mainpiper.app.model.misc.Chapter;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;

/**
 * @author MainPiper Dev
 * @date 22/07/2016.
 */
@Getter
@Setter
public abstract class AbstractManga {
    protected String name;
    protected static String language;

    protected Set<Chapter> chapter;
    
    protected static Boolean DORSS;
    
    public AbstractManga(String mangaName) {
        this.name = mangaName;
    }

	public static String getLanguage() {
		return language;
	}

	public static void setLanguage(String language) {
		AbstractManga.language = language;
	}

	public static Boolean getDORSS() {
		return DORSS;
	}

	public static void setDORSS(Boolean dORSS) {
		DORSS = dORSS;
	}
    

}
