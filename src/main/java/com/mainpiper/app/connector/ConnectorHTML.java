package com.mainpiper.app.connector;

import org.jsoup.Connection;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class ConnectorHTML extends Connector{
	
	protected final Connection connector;
	 
	 
	public ConnectorHTML(String websiteurl){
		super(websiteurl);
		connector = jsoupConnectionHTML(WEBSITE_GENERIQUE_URL);
		log.debug("Abstract ConnectorHTML initialization ended properly");
	}
}
