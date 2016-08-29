package com.mainpiper.app.service;

import com.mainpiper.app.factory.GenericConnector;
import com.mainpiper.app.net.AbstractConnector;
import com.mainpiper.app.net.Downloader;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Service {

	private final AbstractConnector conn;
	private final Downloader down;

	public Service(String mangaName, String webSite) {
		GenericConnector genericConn = new GenericConnector(mangaName, webSite);
		conn = genericConn.getConnector();
		down = new Downloader(mangaName);
	}

	public void downloadManga() {

	}

	public void downloadChapters(String chapterNumbers) {
		String[] chapters = chapterNumbers.split("-");
		log.debug("downloadChapters in progress");
		log.info("Downloading chapters : {}", chapterNumbers.replace("-", " "));
		for (int i = 0; i < chapters.length; i++) {
			downloadChapter(chapters[i]);
		}
	}

	public void downloadFromTo(String start, String end) {

	}

	private boolean downloadChapter(String chapterNumber) {
		return false;
	}

}
