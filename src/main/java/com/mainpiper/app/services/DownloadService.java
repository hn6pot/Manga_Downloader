package com.mainpiper.app.services;

/**
 * @author Christian Kula
 * @date 22/07/2016.
 */
public interface DownloadService {
    void downloadManga();

    void downloadVolume(String volumeNumber);

    void downloadChapter(String volumeNumber, String chapterNumber);
}
