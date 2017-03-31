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
public class LireScanConnector extends HtmlConnector {

    public static final String WEBSITEURL = "http://www.lirescan.net";
    private static final String LINKTOMANGA = "-lecture-en-ligne/";

    /* Variables used to parse the HTML */
    private static final String Suiv = "Suiv";
    private static final String Active = "active";
    private static final String Image = "image_scan";

    private String mangaName;
    private String mangaUrl;

    public LireScanConnector(String mangaName) throws TerminateBatchException {
        super(WEBSITEURL);

        this.mangaName = transformMangaName(mangaName);
        mangaUrl = WEBSITEURL + "/" + this.mangaName + LINKTOMANGA;
        log.info("Successful AbstractConnector Creation");
        chaptersUrl = getChaptersUrl();
        log.debug("LelScanConnector Initialization ended properly");
    }

    private LireScanConnector(LireScanConnector con) {
        super(WEBSITEURL);
        this.mangaName = con.getMangaName();
        mangaUrl = con.getMangaUrl();
    }

    @Override
    public LireScanConnector getNew() {
        return new LireScanConnector(this);
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
        // TODO make better first connection managment
        Elements option = ConnectorUtils.tryFirstConnect(connection, WEBSITEURL, "option");

        for (Iterator<Element> it = option.iterator(); it.hasNext();) {
            Element op = it.next();

            String value = op.val();
            String txt = op.text();
            if (!StringUtils.isChapterNumber(txt)) {
                result.put(txt, WEBSITEURL + value);
                log.trace("Manga {} with link {} has been successfully add to the Map", op.text(), op.val());
            }
        }
        log.info("We apparently get every Manga url available !");
        return result;
    }

    @Override
    public Map<String, String> getChaptersUrl() {

        // Variable Initialization
        Map<String, String> result = new HashMap<String, String>();

        log.info("Trying to get url chapter from : {}, on mangas : {}", mangaUrl, mangaName);
        Elements option = ConnectorUtils.tryConnect(connection, mangaUrl, "option");

        for (Iterator<Element> it = option.iterator(); it.hasNext();) {
            Element op = it.next();

            String value = op.val();
            String txt = op.text();
            if (StringUtils.isChapterNumber(txt)) {
                result.put(txt, WEBSITEURL + value);
                log.trace("Manga {} with link {} has been successfully add to the Map", op.text(), op.val());
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

        // let's take some urls
        Boolean parse = false;

        for (Iterator<Element> it = option.iterator(); it.hasNext();) {
            Element op = it.next();
            if (op.attr("id").equals(Active)) {
                parse = true;
            } else if (op.text().equals(Suiv)) {
                parse = false;
            }
            if (parse) {
                result.put(op.text(), getImage(WEBSITEURL + op.attr("href")));
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
            if (op.attr("id").equals(Image)) {
                link = WEBSITEURL + op.attr("src");
                log.trace("Image url found : {}", link);
                break;
            }
        }
        log.trace("getImage Ended Properly");
        return link;

    }
    public static void main(String[] args) {
    	LireScanConnector l = new LireScanConnector("one-piece");
    	System.out.println(l.getMangaUrls().size());
    }

}
