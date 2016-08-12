package com.mainpiper.app.net.connectors;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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
	
	
	/*Variables used to parse the HTML*/
    private static final String Prec = "Prec";
    private static final String Suiv = "Suiv";
    private static final String tbc = "thumb_cover";
	
	
	private String mangaName;
    private String mangaUrl;

	private final Map<String, String> chaptersUrl;
    

    //FIXME change string to mangas obj
    public LelScanConnector(String mangaName) {
        super(WEBSITEURL);
     
        this.mangaName = transformMangaName(mangaName);
        this.mangaUrl = "http://" + this.mangaName + LINKTOMANGA;
        log.info("Successful AbstractConnector Creation");
        chaptersUrl = getChaptersUrl();
        log.debug("LelScanConnector Initialization ended properly");
    }


	private static String transformMangaName(String mangaName) {
    	/**
    	 * We need this king of name : one-piece
    	 */
        return StringUtils.deAccent(mangaName).replaceAll(" ", "-").replaceAll("_", "-").toLowerCase();
    }
    
	public final Map<String, String> getMangaUrls(){

		   HashMap<String, String> result = new HashMap<String, String>();
		   
		   //Treatment
		   log.info("Trying to get mangas url from : {}", WEBSITEURL);
		  //TODO make better first connection managment
		   Elements option = ConnectorUtils.tryFirstConnect(connection, WEBSITEURL, "option");
		   if(option == null)
			   return null;;
		   
		  Iterator<Element> it = option.iterator();
		  
		  while(it.hasNext()){
			  Element op = it.next();
			  
		  		String value = op.val();
		  		int lastIndex = value.split("/").length -1;
		  		if (!StringUtils.isChapterNumber(value.split("/")[lastIndex])){
		  			result.put(op.text(), op.val());
		  			log.debug("AbstractManga {0} with link {1} has been successfully add to the Map", op.text(), op.val() );
		  		} 
		  }
		 log.info("We apparently get every AbstractManga url needed !");
		  return result;
	   }

	public Map<String, String> getChaptersUrl() {

		//Variable Initialization
		   Map<String, String> result = new HashMap<String, String>();
		   String urlBis = LINKTOMANGABIS + this.mangaName + ".php";
		   
		   log.info("Trying to get url chapter from : {}, on mangas : {}", this.mangaUrl, this.mangaName);
		   Elements option = ConnectorUtils.tryLelscan(connection, this.mangaUrl, urlBis);
		   if(option == null)
			   return null;

		 //let's take some urls
		  Iterator<Element> it = option.iterator();
		  
		  while(it.hasNext()){
			  Element op = it.next();
			  
		  		String value = op.val();
		  		int lastIndex = value.split("/").length -1;
		  		if (StringUtils.isChapterNumber(value.split("/")[lastIndex])){
		  			result.put(op.text(), op.val());
		  			log.debug("Chapter {0} with link {1} has been successfully add to the Map", op.text(), op.val() );
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
	   
	   log.info("Trying to get images url from chapter : {}, on mangas : {}", chapterNumber, this.mangaName);
	   Elements option = ConnectorUtils.tryConnect(connection, chapterUrl, "a");
	   if(option == null)
		   return null;

		 //let's take some urls
		  Iterator<Element> it = option.iterator();
		  Boolean parse = false;
		  Boolean skip = true;
		  while(it.hasNext()){
			  Element op = (Element) it.next();
			  if(op.text().equals(Prec))
				  parse = true;
			  else if(op.text().equals(Suiv))
				  parse = false;
			  if(parse){
				  if(skip){
					  skip = false;
					  continue;
				}
				result.put(op.text(), getImage(op.absUrl("href")));
		  		log.debug("Page {0} with link {1} has been successfully add to the Map", op.text(), op.val() );
			}
		  }
		 log.debug("getImagesUrl Ended Properly");
		 log.info("We apparently get every image url needed !");
		 
		  return result;
		  
		  
	   }
   
   private String getImage(String url){
	   log.info("Trying to get image from url : {}", url);
	   String link = "";
	   
	   Elements option = ConnectorUtils.tryConnect(connection, url, "img");
	   if(option == null)
		   return null;
	   
	   Iterator<Element> it = option.iterator();
		  while(it.hasNext()){
			  Element op = (Element) it.next();
			  String tmpLink = op.absUrl("src");
			  if(!tmpLink.contains(tbc)){
				  link = tmpLink;
				  log.debug("Image url found : {}", link);
				  break;
			  }
			}
		  log.debug("getImage Ended Properly");
		  return link;
		  
   }

}
