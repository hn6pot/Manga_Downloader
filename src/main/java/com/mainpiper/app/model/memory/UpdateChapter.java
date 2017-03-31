package com.mainpiper.app.model.memory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.mainpiper.app.enums.MangaWebsite;
import com.mainpiper.app.model.Chapter;
import com.mainpiper.app.model.Manga;

import lombok.Getter;

@Getter
public class UpdateChapter extends Manga{
	
	private final String time;
	private final Chapter chapter;
	
	public UpdateChapter(String mangaName, MangaWebsite webSite, Chapter chapterDowloaded){
		super(mangaName, webSite);
		this.time = getTime();
		this.chapter = chapterDowloaded;
		try {
			this.link = (String) webSite.getConstructorClass().getField("WEBSITEURL").get(webSite.getConstructorClass());
		} catch (NoSuchFieldException | SecurityException e) {
			
		} catch (IllegalArgumentException e) {
			
		} catch (IllegalAccessException e) {
			
		}
	}
	
	private String getTime(){
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		return dtf.format(now);
	}
	

}
