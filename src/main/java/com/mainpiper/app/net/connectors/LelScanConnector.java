package com.mainpiper.app.net.connectors;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.mainpiper.app.display.Display;
import com.mainpiper.app.exceptions.TerminateBatchException;
import com.mainpiper.app.net.HtmlConnector;
import com.mainpiper.app.util.ConnectorUtils;
import com.mainpiper.app.util.StringUtils;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class LelScanConnector extends HtmlConnector {

    private static final String WEBSITEURL = "http://lelscan.me";
    private static final String LINKTOMANGA = ".lel-scan.co/";
    private static final String LINKTOMANGABIS = "http://lelscan.me/lecture-en-ligne-";

    /* Variables used to parse the HTML */
    private static final String Prec = "Prec";
    private static final String Suiv = "Suiv";
    private static final String tbc = "thumb_cover";

    private String mangaName;
    private String mangaUrl;

    public LelScanConnector(String mangaName) throws TerminateBatchException {
        super(WEBSITEURL);

        this.mangaName = transformMangaName(mangaName);
        mangaUrl = "http://" + this.mangaName + LINKTOMANGA;
        log.info("Successful AbstractConnector Creation");
        chaptersUrl = getChaptersUrl();
        log.debug("LelScanConnector Initialization ended properly");
    }

    private LelScanConnector(LelScanConnector con) {
        super(WEBSITEURL);
        this.mangaName = con.getMangaName();
        mangaUrl = con.getMangaUrl();
    }

    @Override
    public LelScanConnector getNew() {
        return new LelScanConnector(this);
    }

    @Override
    protected String transformMangaName(String mangaName) {
        /**
         * We need this king of name : one-piece
         */
        return StringUtils.deAccent(mangaName).replaceAll(" ", "-").toLowerCase();
    }

    @Override
    public final Map<String, String> getMangaUrls() {

        HashMap<String, String> result = new HashMap<String, String>();

        // Treatment
        log.info("Trying to get mangas url from : {}", WEBSITEURL);
        Display.displaySTitle("Retrieving Manga from " + WEBSITEURL + " ...");

        Elements option = ConnectorUtils.tryFirstConnect(connection, WEBSITEURL, "option");

        for (Iterator<Element> it = option.iterator(); it.hasNext();) {
            Element op = it.next();

            String value = op.val();
            int lastIndex = value.split("/").length - 1;
            if (!StringUtils.isChapterNumber(value.split("/")[lastIndex])) {
                result.put(op.text(), op.val());
                log.trace("Manga {0} with link {1} has been successfully add to the Map", op.text(), op.val());
            }
        }
        log.info("We apparently get every Manga url available !");
        return result;
    }

    @Override
    public Map<String, String> getChaptersUrl() {

        // Variable Initialization
        Map<String, String> result = new HashMap<String, String>();
        String urlBis = LINKTOMANGABIS + mangaName + ".php";

        log.info("Trying to get url chapter from : {}, on mangas : {}", mangaUrl, mangaName);
        Elements option = ConnectorUtils.tryLelscan(connection, mangaUrl, urlBis);

        for (Iterator<Element> it = option.iterator(); it.hasNext();) {
            Element op = it.next();

            String value = op.val();
            int lastIndex = value.split("/").length - 1;
            if (StringUtils.isChapterNumber(value.split("/")[lastIndex])) {
                result.put(op.text(), op.val());
                log.trace("Chapter {0} with link {1} has been successfully add to the Map", op.text(), op.val());
            }
        }
        log.debug("getChaptersUrl Ended Properly");
        log.info("We apparently get every chapters url available !");
        return result;
    }

    @Override
    public Map<String, String> getImageUrls(String chapterUrl) {
        // Variable Initialization
        Map<String, String> result = new HashMap<String, String>();

        log.info("Trying to get images url from : {}, on mangas : {}", chapterUrl, mangaName);
        Elements option = ConnectorUtils.tryConnect(connection, chapterUrl, "a");

        Boolean parse = false;
        Boolean skip = true;
        for (Iterator<Element> it = option.iterator(); it.hasNext();) {
            Element op = it.next();
            if (op.text().equals(Prec)) {
                parse = true;
            } else if (op.text().equals(Suiv)) {
                parse = false;
            }
            if (parse) {
                if (skip) {
                    skip = false;
                    continue;
                }
                result.put(op.text(), getImage(op.absUrl("href")));
                log.trace("Page {0} with link {1} has been successfully add to the Map", op.text(), op.val());
            }
        }
        log.debug("getImagesUrl Ended Properly");
        log.info("We apparently get every image url needed !");

        return result;

    }

    private String getImage(String url) {
        log.info("Trying to get image from url : {}", url);
        String link = "";

        Elements option = ConnectorUtils.tryConnect(connection, url, "img");

        for (Iterator<Element> it = option.iterator(); it.hasNext();) {
            Element op = it.next();
            String tmpLink = op.absUrl("src");
            if (!tmpLink.contains(tbc)) {
                link = tmpLink;
                log.trace("Image url found : {}", link);
                break;
            }
        }
        log.debug("getImage Ended Properly");
        return link;

    }

}
