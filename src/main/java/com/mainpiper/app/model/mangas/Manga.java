package com.mainpiper.app.model.mangas;

import java.util.Set;
import java.util.TreeSet;

import com.mainpiper.app.enums.Language;
import com.mainpiper.app.enums.MangaWebsite;
import com.mainpiper.app.model.Chapter;

public class Manga {

	private String name;
	private String link;
	private Language language = Language.FRENCH;

	private MangaWebsite source = MangaWebsite.LIRESCAN;

	private Set<Chapter> chapters;

	public Manga(String name, String webSite) {
		this.name = name;
		this.link = new String();
		this.chapters = new TreeSet<Chapter>();
		this.source = MangaWebsite.getSource(webSite);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public Set<Chapter> getChapters() {
		return chapters;
	}

	public void setChapters(Set<Chapter> chapters) {
		this.chapters = chapters;
	}

	public MangaWebsite getSource() {
		return this.source;
	}

}
