package com.mainpiper.app.sources;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.parser.Parser;

import com.mainpiper.app.models.Chapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public abstract class Source {
	 private final static int MAX_TRIES = 3;
	 private final static int TIME_OUT_IN_MILLIS = 5000;

	 
	 private String urlName;
	 private String language;
	 private String rssUrl;
	 private String url;
	 
	  private static Connection jsoupConnection = Jsoup.connect("").timeout(TIME_OUT_IN_MILLIS)
			    .ignoreContentType(true).parser(Parser.htmlParser());

		    private final Connection JSOUP_XML_CONNECTION = Jsoup.connect("").timeout(TIME_OUT_IN_MILLIS)
			    .parser(Parser.xmlParser());

		    private final Map<Chapter, String> CHAPTERS_WITH_ERRORS = new HashMap<Chapter, String>();

		    private final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
		    private List<Chapter> UPDATED_CHAPTERS = new ArrayList<Chapter>();

}
