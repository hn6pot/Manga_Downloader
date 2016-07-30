package com.mainpiper.app.connector;

import java.util.Map;

public interface ConnectorInterface {

	//TODO add static and private methods with java 8
	
	Map<String, String> getMangaUrls();
	
	
	Map<String, String> getChaptersUrl();

	Map<String, String> getImageUrls(String chapterNumber);


}