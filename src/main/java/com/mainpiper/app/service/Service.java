package com.mainpiper.app.service;

import java.util.Iterator;
import java.util.Map;

import com.mainpiper.app.args.CliOptions;
import com.mainpiper.app.args.Config;
import com.mainpiper.app.factory.ConnectorFactory;
import com.mainpiper.app.memory.JsonManager;
import com.mainpiper.app.model.Manga;
import com.mainpiper.app.net.Connector;
import com.mainpiper.app.net.Downloader;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Service {

    private final Connector connector;
    private final Downloader downloader;
    private final JsonManager jsonManager;
    private final Map<String, String> chaptersAvailable;
    private final Config conf;

    public Service(String mangaName, Config conf) {
        this.conf = conf;
        jsonManager = new JsonManager(mangaName, conf.getCli().getWebSite(), conf.getDefaultDownloadDirectory(), false);
        Manga manga = jsonManager.getManga();
        connector = ConnectorFactory.createConnector(manga.getName(), manga.getSource());
        downloader = new Downloader(mangaName);
        chaptersAvailable = connector.getChaptersUrl();
    }

    public void downloadManga() {
        log.debug("downloadManga in progress");
        log.info("Downloading entire Manga");
        // TODO add multi threading operator
        // TODO add save + check chapter already download
        for (Iterator<String> it = chaptersAvailable.keySet().iterator(); it.hasNext();) {
            downloadChapter(it.next());
        }

    }

    public void downloadChapters() {
        String chapterNumbers = conf.getCli().getOptionValue(CliOptions.OPT_CHAPTER);
        String[] chapters = chapterNumbers.split("-");
        log.debug("downloadChapters in progress");
        log.info("Downloading chapters : {}", chapterNumbers.replace("-", " "));
        for (int i = 0; i < chapters.length; i++) {
            downloadChapter(chapters[i]);
        }
    }

    private boolean downloadChapter(String chapterNumber) {
        boolean result = false;
        if (chaptersAvailable.containsKey(chapterNumber)) {
            try {
                downloader.saveChapter(chapterNumber, connector.getImageUrls(chapterNumber), true);
                result = true;
            } catch (Exception e) {
                log.error("An error Occured !", e);
            }
        } else {
            log.error("Chapter is not available yet");
            // TODO add chapter available
        }
        return result;
    }

}
