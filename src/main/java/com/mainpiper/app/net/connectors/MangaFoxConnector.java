package com.mainpiper.app.net.connectors;

import java.util.Map;

import org.jsoup.Connection;

import com.mainpiper.app.model.mangas.MangaFox;
import com.mainpiper.app.net.HtmlConnector;
import com.mainpiper.app.util.StringUtils;

import lombok.Getter;
import lombok.Setter;

/**
 * @author MainPiper Chris
 * @date 22/07/2016.
 */

@Getter
@Setter
public class MangaFoxConnector extends HtmlConnector {
    private static final String BASE_URL = "http://mangafox.me";
    private static final String MANGA_BASE_URL = "http://mangafox.me/mangas/";
    private static final String MANGA_RSS_URL = "http://mangafox.me/rss/";

    private String mangaName;
    private final String rssUrl;
    private final String mangaUrl;
    private Connection connector;

    public MangaFoxConnector(MangaFox manga) {
        super(BASE_URL);

        mangaName = transformMangaName(manga.getName());
        mangaUrl = MANGA_BASE_URL + mangaName;
        rssUrl = MANGA_RSS_URL + mangaName + ".xml";
        connector = jsoupConnectionRSS(rssUrl);
    }

    @Override
    public String getSiteUrl() {
        return BASE_URL;
    }

    @Override
    protected Map<String, String> getMangaUrls() {
        // TODO OMG DO ME
        return null;
    }

    @Override
    protected Map<String, String> getChaptersUrl() {
        // TODO OMG DO ME
        return null;
    }

    @Override
    protected Map<String, String> getImageUrls(String chapterNumber) {
        // TODO OMG DO ME
        return null;
    }

    public static String transformMangaName(String mangaName) {
        return StringUtils.deAccent(mangaName).replaceAll(" ", "_").replaceAll("[^0-9a-zA-Z_]", "").toLowerCase();
    }
}
