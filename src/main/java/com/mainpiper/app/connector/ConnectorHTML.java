package com.mainpiper.app.connector;

import java.util.HashMap;
import java.util.Iterator;

import org.jsoup.Connection;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.mainpiper.app.utils.StringUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class ConnectorHTML extends Connector{
	//TODO create html and rss connector ? 
	private final String WEBSITEURL;
	private final Connection connector;
	 
	 
	public ConnectorHTML(String websiteurl){
		super();
		WEBSITEURL = websiteurl;
		connector = jsoupConnectionHTML(WEBSITEURL);
	}
	
	
	public final HashMap<String, String> getMangaUrl(){
		   
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
    
	
    public String getSiteUrl(){
    	return WEBSITEURL;
    }
}
