package com.mainpiper.app.net.connectors;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.mainpiper.app.display.Display;
import com.mainpiper.app.net.Connector;
import com.mainpiper.app.net.HtmlConnector;
import com.mainpiper.app.util.ConnectorUtils;
import com.mainpiper.app.util.StringUtils;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;

/**
 * @author MainPiper Chris
 * @date 22/07/2016.
 */

@Getter
@Setter
@Slf4j
public class MangaFoxConnector extends HtmlConnector {
    private static final String WEBSITEURL = "http://mangafox.me";
    private static final String MANGA_BASE_URL = "http://mangafox.me/manga/";

    private String mangaName;
    private final String mangaUrl;
    
    public MangaFoxConnector(String mangaName) {
        super(WEBSITEURL);

        this.mangaName = mangaName;

        mangaUrl = MANGA_BASE_URL + this.transformMangaName(mangaName);
        log.debug("Successful AbstractConnector Creation");
        chaptersUrl = getChaptersUrl();
        log.debug("LireScanConnector Initialization ended properly");
    }

    private MangaFoxConnector(MangaFoxConnector conn) {
        super(WEBSITEURL);

        mangaName = conn.getMangaName();
        mangaUrl = conn.getMangaUrl();

    }

    @Override
    protected String transformMangaName(String mangaName) {
        /**
         * We need this king of name : one-piece
         */
        return StringUtils.deAccent(mangaName).replaceAll(" ", "_").replace("-", "_").toLowerCase();
    }

    @Override
    public final Map<String, String> getMangaUrls() {

        HashMap<String, String> result = new HashMap<String, String>();

        // Treatment
        log.info("Trying to get mangas url from : {}", WEBSITEURL);
        Display.displaySTitle("Retrieving Manga from " + WEBSITEURL + " ...");

        Elements option = ConnectorUtils.tryFirstConnect(connection, WEBSITEURL, "a");

        for (Iterator<Element> it = option.iterator(); it.hasNext();) {
//            Element op = it.next();
//            System.out.println(op.absUrl("href"));
            
        }
        log.info("We apparently get every Manga url available !");
        return result;
    }

    @Override
    public Map<String, String> getChaptersUrl() {

        // Variable Initialization
        Map<String, String> result = new HashMap<String, String>();

        log.info("Trying to get url chapter from : {}, on mangas : {}", mangaUrl, mangaName);
        Elements option = ConnectorUtils.tryConnect(connection, mangaUrl, "a");
        for (Iterator<Element> it = option.iterator(); it.hasNext();) {
            Element op = it.next();
            if(op.absUrl("class").contains("tips")){
            	String txt = op.text();
            	int lastIndex = txt.split(" ").length - 1;
            	String chapterNumber = txt.split(" ")[lastIndex];
            	String link = op.absUrl("href");
            	log.trace("Chapter {0} with link {1} has been successfully add to the Map", chapterNumber, link);
            	result.put(chapterNumber, link);
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

        log.info("Trying to get images url from : {}, on mangas : {}");
        Elements option = ConnectorUtils.tryConnect(connection, chapterUrl, "option");

        Integer prec = 0;
        for (Iterator<Element> it = option.iterator(); it.hasNext();) {
            Element op = it.next();
          
            try{
            	Integer pageNumber = Integer.parseInt(op.text());
            	if(prec > pageNumber){
                	break;
                }
            	String pageLink = StringUtils.changeMangaFoxPageUrl(chapterUrl, op.text());
            	String link = "";
            	while(link.equals("")){
            		link = getImage(pageLink);
            	}
                result.put(op.text(), link);
            }
            catch(NumberFormatException e){
            	//Found something like Comments
            	break;
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
            
            link = op.absUrl("src");
            break;
        }
        log.debug("getImage Ended Properly");
        return link;

    }
    
	@Override
	public Connector getNew() {
		 return new MangaFoxConnector(this);
	}
    
    public static void main(String[] args) {
    	MangaFoxConnector l = new MangaFoxConnector("one-piece");
    	//System.out.println(l.getImage("http://mangafox.me/manga/one_piece/v02/c009/27.html"));
    	System.out.print(l.getImageUrls("http://mangafox.me/manga/one_piece/v02/c009/1.html"));
    }
	
}
