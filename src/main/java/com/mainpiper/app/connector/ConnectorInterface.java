package com.mainpiper.app.connector;

import java.util.HashMap;

public interface ConnectorInterface {

	//TODO add static and private methods with java 8
	
	HashMap<String, String> getMangaUrls();
	
	
	HashMap<String, String> getChaptersUrl();

	HashMap<String, String> getImageUrls(String chapterNumber);


}