package com.mainpiper.app.net.connectors;

import java.util.Map;

import org.jsoup.Connection;

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
    private static final String MANGA_BASE_URL = "http://mangafox.me/manga/%s";
    private static final String MANGA_RSS_BASE_URL = "http://mangafox.me/rss/%s.xml";

    private String mangaName;
    private final String rssUrl;
    private final String mangaUrl;
    private Connection connector;

    public MangaFoxConnector(String mangaName) {
        super(BASE_URL);

        mangaName = mangaName;

        mangaUrl = String.format(MANGA_BASE_URL, mangaName);
        rssUrl = String.format(MANGA_RSS_BASE_URL, mangaName);
        connector = jsoupConnectionRSS(rssUrl);
    }

    @Override
    public String getSiteUrl() {
        return BASE_URL;
    }

    @Override
    public Map<String, String> getMangaUrls() {
        // TODO OMG DO ME
        return null;
    }

    @Override
    public Map<String, String> getChaptersUrl() {
        // TODO OMG DO ME
        return null;
    }

    @Override
    public Map<String, String> getImageUrls(String chapterNumber) {
        // TODO OMG DO ME
        return null;
    }

    public static String transformMangaName(String mangaName) {
        return StringUtils.deAccent(mangaName).replaceAll(" ", "_").replaceAll("[^0-9a-zA-Z_]", "").toLowerCase();
    }
}
