package com.mainpiper.app.model;

import java.util.Set;
import java.util.TreeSet;

import com.mainpiper.app.enums.Language;
import com.mainpiper.app.enums.MangaWebsite;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Manga {

    private String name;
    private String link;
    private Language language = Language.FRENCH;

    private MangaWebsite source = MangaWebsite.LIRESCAN;

    private Set<Chapter> chapters;

    public Manga(String name, String webSite) {
        this.name = name;
        link = new String();
        chapters = new TreeSet<Chapter>();
        source = MangaWebsite.getSource(webSite);
    }

    public void setSource(String webSite) {
        source = MangaWebsite.getSource(webSite);
    }

}
