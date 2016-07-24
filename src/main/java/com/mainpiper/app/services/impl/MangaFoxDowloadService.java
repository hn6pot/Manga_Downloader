package com.christiankula.vulpes.services.impl;

import com.christiankula.vulpes.models.mangas.MangaFoxManga;
import com.christiankula.vulpes.services.DownloadService;

/**
 * @author Christian Kula
 *         22/07/2016
 */
public class MangaFoxDowloadService implements DownloadService {
    private final MangaFoxManga manga;

    public MangaFoxDowloadService(String mangaName) {
        this.manga = new MangaFoxManga(mangaName);
    }

    public void downloadManga() {
        System.out.println("Download Mangafox " + manga.getName());
    }

    public void downloadVolume(String volumeNumber) {
        System.out.println("Download Mangafox " + manga.getName() + " Volume " + volumeNumber);

    }

    public void downloadChapter(String volumeNumber, String chapterNumber) {
        System.out.println("Download Mangafox " + manga.getName() + " Volume " + volumeNumber + " Chapter " + chapterNumber);
    }
}
