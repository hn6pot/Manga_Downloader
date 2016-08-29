package com.mainpiper.app.tests.poubelle;

import java.util.HashMap;

import com.mainpiper.app.net.Downloader;
import com.sun.javafx.collections.MappingChange.Map;

public class testDownload {
	private static final String mangaName = "one-piece";
	private static final String chapterNumber = "836";

	private final HashMap<String, String> chapterContent;

	public testDownload() {
		chapterContent = new HashMap<String, String>();
		chapterContent.put("01", "http://lel-scan.me/mangas/one-piece/836/01.jpg?v=f");
		chapterContent.put("02", "http://lel-scan.me/mangas/one-piece/836/02.jpg?v=f");
		chapterContent.put("03", "http://lel-scan.me/mangas/one-piece/836/03.jpg?v=f");
		chapterContent.put("04", "http://lel-scan.me/mangas/one-piece/836/04.jpg?v=f");
	}

	public void testnoCbz() {
		Downloader down = new Downloader(mangaName);
		down.saveChapter(chapterNumber, chapterContent, false);
	}

	public void testCbz() {
		Downloader down = new Downloader(mangaName);
		down.saveChapter(chapterNumber, chapterContent, true);
	}

	public static void main(String[] args) {
		testDownload test = new testDownload();
		test.testCbz();

	}

}