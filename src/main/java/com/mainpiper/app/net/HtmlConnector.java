package com.mainpiper.app.net;

import org.jsoup.Connection;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class HtmlConnector extends AbstractConnector {
	
	protected final Connection connection;

	public HtmlConnector(String websiteurl){
		super(websiteurl);
		connection = jsoupConnectionHTML(WEBSITE_GENERIC_URL);
		log.debug("Abstract HtmlConnector initialization ended properly");
	}
}
