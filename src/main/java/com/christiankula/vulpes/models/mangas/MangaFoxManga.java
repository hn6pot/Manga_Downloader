package com.christiankula.vulpes.models.mangas;

import com.christiankula.vulpes.models.Manga;
import com.christiankula.vulpes.models.Volume;
import com.christiankula.vulpes.utils.StringUtils;

import java.util.TreeSet;

/**
 * @author Christian Kula
 * @date 22/07/2016.
 */
public class MangaFoxManga extends Manga {
    private String urlName;

    private String rssUrl;

    public MangaFoxManga(String mangaName) {
        super(mangaName);

        this.rssUrl = StringUtils.transformToMangaFoxUrlName(mangaName);
        this.volumes = new TreeSet<Volume>();

        this.url = "http://mangafox.me/manga/" + this.urlName;
        this.rssUrl = "http://mangafox.me/rss/" + this.urlName + ".xml";
    }

    public String getUrlName() {
        return urlName;
    }

    public void setUrlName(String urlName) {
        this.urlName = urlName;
    }

    public String getRssUrl() {
        return rssUrl;
    }

    public void setRssUrl(String rssUrl) {
        this.rssUrl = rssUrl;
    }
}
