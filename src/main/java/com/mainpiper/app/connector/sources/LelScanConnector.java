package com.mainpiper.app.connector.sources;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.jsoup.Connection;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.mainpiper.app.connector.Connector;
import com.mainpiper.app.connector.ConnectorInteface;
import com.mainpiper.app.utils.StringUtils;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public class LelScanConnector extends Connector implements ConnectorInteface {
	
	//TODO make better connector functions 
	
	private static final String WEBSITEURL = "http://lelscan.me";
	private static final String LINKTOMANGA = ".lel-scan.co/";
	private static final String LINKTOMANGABIS = "http://lelscan.me/lecture-en-ligne-";
	
	
	/*Variables used to parse the HTML*/
    private static final String Prec = "Prec";
    private static final String Suiv = "Suiv";
    private static final String tbc = "thumb_cover";
	
	
	private String mangaName;
    private String mangaUrl;
    
    private final HashMap<String, String> chaptersUrl;
    
    
    private final static Connection connector = jsoupConnectionHTML(WEBSITEURL);

    //FIXME change string to manga obj
    public LelScanConnector(String mangaName) {
        super();
     
        this.mangaName = transformMangaName(mangaName);
        this.mangaUrl = "http://" + this.mangaName + LINKTOMANGA;
        log.info("Successful Connector Creation");     
        chaptersUrl = getChaptersUrl();
    }

    
    private static String transformMangaName(String mangaName) {
    	/**
    	 * We need this king of name : one-piece
    	 */
        return StringUtils.deAccent(mangaName).replaceAll(" ", "-").replaceAll("_", "-").toLowerCase();
    }
    
   public final static HashMap<String, String> getMangaUrl(){
	   
	   Elements option;
	   HashMap<String, String> result = new HashMap<String, String>();
	   
	   //Treatment
	   log.info("Trying to get manga url from : {}", WEBSITEURL);
	   try {
		   option = connector.get().select("body option");
	   }
	   catch(Exception e){
		   log.warn("This url does not work properly : {} : [Need Code Review]", WEBSITEURL);
			   e.printStackTrace();
			   return null;
		  }
	  Iterator<Element> it = option.iterator();
	  
	  while(it.hasNext()){
		  Element op = it.next();
		  
	  		String value = op.val();
	  		int lastIndex = value.split("/").length -1;
	  		if (!StringUtils.isChapterNumber(value.split("/")[lastIndex])){
	  			result.put(op.text(), op.val());
	  			log.debug("Manga {0} with link {1} has been successfully add to the Map", op.text(), op.val() );
	  		} 
	  }
	 log.info("We apparently get every Manga url needed !");
	  return result;
   }
   

	public HashMap<String, String> getChaptersUrl() {
		   //Variable Initialization
		   Elements option;
		   HashMap<String, String> result = new HashMap<String, String>();
		   
		   //Treatment
		   log.info("Trying to get url chapter from : {}, on manga : {}", this.mangaUrl, this.mangaName);
		   try {
			   option = connector.url(this.mangaUrl).get().select("body option");
		   }
		   catch(Exception e){
			   log.warn("This url does not work properly : {}", this.mangaUrl);
			   this.mangaUrl = LINKTOMANGABIS + this.mangaName + ".php";
			   log.info("we change the url : {}", this.mangaUrl);
			   try{
				  option = connector.url(this.mangaUrl).get().select("body option");
			   }
			   catch(Exception ex){
				   log.warn("This url does not work properly too: {}", this.mangaUrl);
				   log.error("We cannot find the chapters url");
				   //TODO better error management bitch
				   e.printStackTrace();
				   ex.printStackTrace();
				   return null;
			   }
		   }
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
		 log.info("We apparently get every chapters url needed !");
		  return result;
	   }
	   
	
	public HashMap<String, String> getImageUrls(String chapterNumber) {
		   //Variable Initialization
		   String chapterUrl = chaptersUrl.get(chapterNumber);
		   if(chapterNumber == null){
			   if (StringUtils.isChapterNumber(chapterNumber)){
				   log.error("The chapter {} is not yet available !", chapterNumber);
			   }
			   else {
			   log.error("There is no chapter {}, you probably misspelled it");
			   }
			   return null;
		   }
		   Elements option;
		   HashMap<String, String> result = new HashMap<String, String>();
		   
		   //Treatment
		   log.info("Trying to get url chapter from : {}, on manga : {}", this.mangaUrl, this.mangaName);
		   try {
			   option = connector.url(chapterUrl).get().select("body a");
		   }
		   catch(Exception e){
			   log.warn("This url does not work properly : {}", chapterUrl);
				   e.printStackTrace();
				   return null;
			   }
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
		 log.info("We apparently get every chapters url needed !");
		 
		  return result;
		  
		  
	   }
   
   private static String getImage(String url){
	   log.info("Trying to get image from url : {}", url);
	   Elements option;
	   String link = "";
	   try {
		   option = connector.url(url).get().select("body img");
	   }
	   catch(Exception e){
		   log.warn("This url does not work properly : {}", url);
			   e.printStackTrace();
			   return null;
		   }
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
		  return link;
   }

}
