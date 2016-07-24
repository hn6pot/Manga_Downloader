package com.mainpiper.app.connector;

import java.util.HashMap;
import java.util.Iterator;

import org.jsoup.Connection;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.mainpiper.app.models.MangaModels.MangaFox;
import com.mainpiper.app.utils.StringUtils;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
public class LelScanConnector extends Connector{
	private static final String WEBSITEURL = "http://lelscan.me";
	private static final String LINKTOMANGA = ".lel-scan.co/";
	private static final String LINKTOMANGABIS = "http://lelscan.me/lecture-en-ligne-";
	
	
	private String mangaName;
    private String mangaUrl;
    private final Connection connector;

    public LelScanConnector(MangaFox manga) {
        super();
     
        this.mangaName = transformMangaName(manga.getName());
        this.mangaUrl = "http://" + this.mangaName + LINKTOMANGA;
        this.connector = jsoupConnectionHTML(this.mangaUrl);
        log.info("Successful Connector Creation");     
    }
    
    public String getSiteUrl(){
    	return WEBSITEURL;
    }
    
    public static String transformMangaName(String mangaName) {
    	/**
    	 * We need this king of name : one-piece
    	 */
        return StringUtils.deAccent(mangaName).replaceAll(" ", "-").replaceAll("_", "-").toLowerCase();
    }
    
   public HashMap<String, String> getChaptersUrl() {
	   //Variable Initialization
	   Elements option;
	   HashMap<String, String> result = new HashMap<String, String>();
	   
	   //Treatment
	   log.info("Trying to get url chapter from : {}, on manga : {]", this.mangaUrl, this.mangaName);
	   try {
		   option = this.connector.get().select("body option");
	   }
	   catch(Exception e){
		   log.warn("This url does not work properly : {}", this.mangaUrl);
		   this.mangaUrl = LINKTOMANGABIS + this.mangaName + ".php";
		   log.info("we change the url : {}", this.mangaUrl);
		   try{
			  option = this.connector.get().select("body option");
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
	 //let's take urls
	  Iterator<Element> it = option.iterator();
	  
	  while(it.hasNext()){
		  Element op = it.next();
		  result.put(op.text(), op.val());
		
		 //TODO BREAK condition
		  
	  }
	 log.info("We apparently get every chapters url needed !");
	  return result;
   }

}
