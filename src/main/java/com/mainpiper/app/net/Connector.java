package com.mainpiper.app.net;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.parser.Parser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mainpiper.app.model.Chapter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Connector {
    public final String USER_AGENT =
            "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.64 Safari/537.11";

    private final String HEADER_ACCEPT = "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8";
    private final String HEADER_ACCEPT_CHARSET = "ISO-8859-1,utf-8;q=0.7,*;q=0.3";
    private final String HEADER_LANGUAGE = "en-US,en;q=0.8";
    private final String HEADER_KEEP_ALIVE = "keep-alive";

    protected final static int MAX_TRIES = 3;
    protected final static int TIME_OUT_IN_MILLIS = 10000;
    protected final String WEBSITE_GENERIC_URL;

    protected Map<String, String> chaptersUrl;

    public Connector(String websiteUrl) {
        WEBSITE_GENERIC_URL = websiteUrl;
        // TODO get the mangas url from enum
    }

    public Connection jsoupConnectionHTML(String url) {
        return createJsoupConnection(url, true);
    }

    public Connection jsoupConnectionRSS(String url) {
        return createJsoupConnection(url, false);
    }
    // FIXME do single header

    private Connection createJsoupConnection(String url, boolean htmlParser) {
        Connection jsoupConnection = Jsoup.connect(url).userAgent(USER_AGENT).header("Accept", HEADER_ACCEPT)
                .header("Accept-Charset", HEADER_ACCEPT_CHARSET).header("Accept-language", HEADER_LANGUAGE)
                .header("keep-alive", HEADER_KEEP_ALIVE).timeout(TIME_OUT_IN_MILLIS);

        if (htmlParser) {
            jsoupConnection.parser(Parser.htmlParser());
        } else {
            jsoupConnection.parser(Parser.xmlParser());
        }

        return jsoupConnection;
    }

    public String getSiteUrl() {
        return WEBSITE_GENERIC_URL;
    }

    public abstract Connector getNew();

    public abstract Map<String, String> getMangaUrls();

    public abstract Map<String, String> getChaptersUrl();

    public abstract Map<String, String> getImageUrls(String chapterUrl);

    protected final Map<Chapter, String> CHAPTERS_WITH_ERRORS = new HashMap<Chapter, String>();

    protected final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    protected List<Chapter> UPDATED_CHAPTERS = new ArrayList<Chapter>();

    protected abstract String transformMangaName(String mangaName);
    // rss =
    // JSOUP_XML_CONNECTION.url(MANGA.getXmlLink()).get().getElementsByTag("rss").get(0);
}
