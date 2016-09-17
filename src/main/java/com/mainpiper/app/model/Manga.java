package com.mainpiper.app.model;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.mainpiper.app.enums.Language;
import com.mainpiper.app.enums.MangaWebsite;
import com.mainpiper.app.exceptions.TerminateBatchException;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
public class Manga {

    private String name;
    private String link;
    private Language language = Language.FRENCH;

    private MangaWebsite source = MangaWebsite.LIRESCAN;

    private Set<Chapter> chapters;

    public Manga(String name, MangaWebsite webSite) {
        this.name = name;
        link = new String();
        chapters = new TreeSet<Chapter>();
        source = webSite;
    }

    public void setSource(MangaWebsite webSite) {
        source = webSite;
    }

    public void updateChapters(List<Chapter> chaptersDownloaded) {
        if (chaptersDownloaded.isEmpty()) {
            log.debug("There is no chapters on the list provided !");
        } else {
            try {
                log.debug("Manga Object update in progress");
                System.out.println("toto");
                if (chapters.isEmpty()) {
                    this.chapters.add(chaptersDownloaded.get(0));
                }
                this.chapters.addAll(chaptersDownloaded);
            } catch (NullPointerException n) {
                log.debug("An error occured ", n);
                throw new TerminateBatchException(TerminateBatchException.EXIT_CODE_ERROR_UPDATE_MANGA,
                        "Error During the Chapters Update", n);
            } catch (Exception e) {
                throw new TerminateBatchException(TerminateBatchException.EXIT_CODE_UNKNOWN, e);
            }
        }
    }

}
