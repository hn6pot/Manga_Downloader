package com.mainpiper.app.sources;

import java.util.TreeSet;

import com.mainpiper.app.models.Manga;
import com.mainpiper.app.models.Volume;
import com.mainpiper.app.utils.StringUtils;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Christian Kula
 * @date 22/07/2016.
 */

@Getter
@Setter
public class MangaFox extends Source {
	private static final String WEBSITEURL = "http://mangafox.me.com";
	private static final String LINKTOMANGA = "http://mangafox.me/manga/";
	private static final String LINKTORSS = "http://mangafox.me/rss/";
	
	private Manga manga;
    private String language = "English";
    private String rssUrl;
    private String url;

    public MangaFox(Manga manga) {
        super();
        this.manga = manga;

        this.rssUrl = StringUtils.transformToMangaFoxUrlName(this.manga.getName());

        this.url = LINKTOMANGA + this.manga.getName();
        this.rssUrl = LINKTORSS + this.manga.getName() + ".xml";
    }
    
    public String getSiteUrl(){
    	return WEBSITEURL;
    }
    
    //TODO add parse function
}
