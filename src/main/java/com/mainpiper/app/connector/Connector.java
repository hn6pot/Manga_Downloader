package com.mainpiper.app.connector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.parser.Parser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mainpiper.app.models.Chapter;
import com.mainpiper.app.models.Manga;
import com.mainpiper.app.utils.StringUtils;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter

public abstract class Connector {
	 protected final static int MAX_TRIES = 3;
	 protected final static int TIME_OUT_IN_MILLIS = 5000;
	 protected final String WEBSITE_GENERIQUE_URL;
	
	 
	 
	 public Connector(String websiteurl){
		 WEBSITE_GENERIQUE_URL = websiteurl;
		 //TODO get the manga url from enum
	 }
     
	 //TODO variable final
	 public static Connection jsoupConnectionHTML(String url){
		return Jsoup.connect(url)
				.header("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.64 Safari/537.11")
		 		.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
		 		.header("Accept-Charset", "ISO-8859-1,utf-8;q=0.7,*;q=0.3")
		 		.header("Accept-Language", "en-US,en;q=0.8")
		 		.header("keep-alive", "keep-alive")
				.timeout(TIME_OUT_IN_MILLIS)
				.ignoreContentType(true).parser(Parser.htmlParser());
	 }
	 
	 public static Connection jsoupConnectionRSS(String url){
		 return Jsoup.connect(url)
				    .header("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.64 Safari/537.11")
		     		.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
		     		.header("Accept-Charset", "ISO-8859-1,utf-8;q=0.7,*;q=0.3")
		     		.header("Accept-Language", "en-US,en;q=0.8")
		     		.header("keep-alive", "keep-alive")
		     		.timeout(TIME_OUT_IN_MILLIS)
		     		.parser(Parser.xmlParser());
       
	 }
	 //FIXME do single header

    public String getSiteUrl(){
    	return WEBSITE_GENERIQUE_URL;
    }
	
	
	 protected final Map<Chapter, String> CHAPTERS_WITH_ERRORS = new HashMap<Chapter, String>();
	
	 protected final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	 protected List<Chapter> UPDATED_CHAPTERS = new ArrayList<Chapter>();
	 //rss = JSOUP_XML_CONNECTION.url(MANGA.getXmlLink()).get().getElementsByTag("rss").get(0);
}
