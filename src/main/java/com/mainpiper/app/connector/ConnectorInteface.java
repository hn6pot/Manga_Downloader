package com.mainpiper.app.connector;

import java.util.HashMap;

public interface ConnectorInteface {

	//static HashMap<String, String> getMangaUrl();
	//TODO add static methods with java 8
	
	HashMap<String, String> getChaptersUrl();

	HashMap<String, String> getImageUrls(String chapterNumber);
	
	
	
	

}