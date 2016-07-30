package com.mainpiper.app.connector.sources;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.mainpiper.app.connector.ConnectorHTML;
import com.mainpiper.app.connector.ConnectorInterface;
import com.mainpiper.app.utils.ConnectorUtils;
import com.mainpiper.app.utils.StringUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j

public class JapscanConnector extends ConnectorHTML implements ConnectorInterface{
	
	private static final String WEBSITERACINE = "http://www.japscan.com";
	private static final String WEBSITEURL = "http://www.japscan.com/mangas/";
	
	/*Variables used to parse the HTML*/
    public static final String LECTURE = "lecture-en-ligne";
    public static final String VOLUME = "volume";
    public static final String VOLUME_REPLACE = "volume-";
    public static final String PAGE = "Page ";
	
	private String mangaName;
    private String mangaUrl;

	private final Map<String, String> chaptersUrl;
    
    //FIXME change string to manga obj
    public JapscanConnector(String mangaName) {
        super(WEBSITEURL);
        
        this.mangaName = transformMangaName(mangaName);
        this.mangaUrl = WEBSITEURL + this.mangaName + "/";    
        chaptersUrl = getChaptersUrl();
        log.info("Successful Connector Creation");
        log.debug("LelScanConnector Initialization ended properly");
    }


	private static String transformMangaName(String mangaName) {
    	/**
    	 * We need this king of name : one-piece
    	 */
        return StringUtils.deAccent(mangaName).replaceAll(" ", "-").replaceAll("_", "-").toLowerCase();
    }
    
	public final Map<String, String> getMangaUrls(){

		   Map<String, String> result = new HashMap<String, String>();
		   
		   //Treatment
		   log.info("Trying to get manga url from : {}", WEBSITEURL);
		
		   Elements option = ConnectorUtils.tryFirstConnect(connector, WEBSITEURL, "a"); 
		   if(option == null)
			   return null;
		   
		  Iterator<Element> it = option.iterator();
		  
		  while(it.hasNext()){
			  Element op = it.next();
			  String href = op.absUrl("href");
			  if(href.contains("manga"))
				  result.put(op.text(), href);
  				  log.debug("Manga {0} with link {1} has been successfully add to the Map", op.text(), op.absUrl("href"));
		  }
		 log.info("We apparently get every Manga url needed !");
		  return result;
	   }


	public Map<String, String> getChaptersUrl() {

		//Variable Initialization
		   HashMap<String, String> result = new HashMap<String, String>();
		   
		   log.info("Trying to get url chapter from : {}, on manga : {}", this.mangaUrl, this.mangaName);
		   Elements option = ConnectorUtils.tryConnect(connector, this.mangaUrl, "a");
		   if(option == null)
			   return null;

		 //let's take some urls
		   Iterator<Element> it = option.subList(5, option.size()).iterator();
	    	Boolean first = true;
	    	while(it.hasNext()){
	    		Element o = it.next();
	    		String href = o.absUrl("href");
	    		if (first){
	    			log.info("Trad Team : {}", href);
	    			first = false;
	    		}
	    		if(href.contains(LECTURE)){
	    			String chapterNumber;
	    			if(href.contains(VOLUME)){
	    				String[] tab = href.split("/");
	    				chapterNumber = tab[tab.length - 1].replace(VOLUME_REPLACE, "");
	    			}
	    			else{
	    				String[] tab = href.split("/");
	    				chapterNumber = tab[tab.length - 1];
	    			}
	    			result.put(chapterNumber, href);
	    		}
	    	}	
		 log.debug("getChaptersUrl Ended Properly");
		 log.info("We apparently get every chapters url needed !");
		  return result;
	   }
	   
	
	public Map<String, String> getImageUrls(String chapterNumber) {
		   //Variable Initialization
		Map<String, String> result = new HashMap<String, String>();
			
	   String chapterUrl = StringUtils.checkChapter(chapterNumber, chaptersUrl);
	   if(chapterUrl == null)
		   return null;
	   
	   log.info("Trying to get images url from chapter : {}, on manga : {}", chapterNumber, this.mangaName);
	   Elements option = ConnectorUtils.tryConnect(connector, chapterUrl, "option");
	   if(option == null)
		   return null;

		 //let's take some urls
		  Iterator<Element> it = option.iterator();
	    	while(it.hasNext()){
	    		Element op = it.next();
	    		result.put(op.text().replace(PAGE, ""), getImage(WEBSITERACINE + op.val()));
		  		log.debug("Page {0} with link {1} has been successfully add to the Map", op.text(), op.val() );
			}
		  
		 log.debug("getImagesUrl Ended Properly");
		 log.info("We apparently get every image url needed !");
		 
		  return result;
	   }
   
   private String getImage(String url){
	   log.info("Trying to get image from url : {}", url);
	   String link = "";
	   
	   Elements option = ConnectorUtils.tryConnect(connector, url, "img");
	   if(option == null)
		   return null;
	   
	   Element it = option.first();
	   link = it.absUrl("src");
		log.debug("getImage Ended Properly");
		 return link;
		  
   }
}
