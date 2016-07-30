package com.mainpiper.app.connector.sources;

import java.util.HashMap;

import org.jsoup.Connection;

import com.mainpiper.app.connector.Connector;
import com.mainpiper.app.models.MangaModels.MangaFox;
import com.mainpiper.app.utils.StringUtils;

import lombok.Getter;
import lombok.Setter;

/**
 * @author MainPiper dev
 * @date 22/07/2016.
 */

@Getter
@Setter
public class MangaFoxConnector extends Connector {
	private static final String WEBSITEURL = "http://mangafox.me.com";
	private static final String LINKTOMANGA = "http://mangafox.me/manga/";
	private static final String LINKTORSS = "http://mangafox.me/rss/";
	
	private String mangaName;
    private final String rssUrl;
    private final String mangaUrl;
    private Connection connector;
    

    public MangaFoxConnector(MangaFox manga) {
        super();
     
        this.mangaName = transformMangaName(manga.getName());
        this.mangaUrl = LINKTOMANGA + this.mangaName;
        this.rssUrl = LINKTORSS + this.mangaName + ".xml";
        this.connector = jsoupConnectionRSS(this.rssUrl);
    }
    
    public String getSiteUrl(){
    	return WEBSITEURL;
    }
    
    public static String transformMangaName(String mangaName) {
        return StringUtils.deAccent(mangaName).replaceAll(" ", "_").replaceAll("[^0-9a-zA-Z_]", "").toLowerCase();
    }
    
   //public HashMap<String, String> getChapterUrl(String url/manga)
}
