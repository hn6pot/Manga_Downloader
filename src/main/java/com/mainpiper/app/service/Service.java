package com.mainpiper.app.service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.mainpiper.app.args.CliOptions;
import com.mainpiper.app.args.Config;
import com.mainpiper.app.factory.ConnectorFactory;
import com.mainpiper.app.memory.JsonManager;
import com.mainpiper.app.model.Chapter;
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
        jsonManager = new JsonManager(mangaName, conf.getWebSources(), conf.getDefaultDownloadDirectory(), false);
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
        List<Chapter> chapters = chaptersAvailable.keySet().stream().parallel().map(chapter -> downloadChapter(chapter))
                .filter(elem -> elem != null).collect(Collectors.toList());
        jsonManager.updateJSON(chapters);
    }

    public void downloadChapters() {
        String chapterNumbers = conf.getCli().getOptionValue(CliOptions.OPT_CHAPTER);
        List<String> chaptersAvailable = Arrays.asList(chapterNumbers.split("-"));
        log.debug("downloadChapters in progress");
        log.info("Downloading chapters : {}", chapterNumbers.replace("-", " "));

        List<Chapter> chapters = chaptersAvailable.stream().parallel().map(chapter -> downloadChapter(chapter))
                .filter(elem -> elem != null).collect(Collectors.toList());
        jsonManager.updateJSON(chapters);
    }

    private Chapter downloadChapter(String chapterNumber) {
        Chapter result = null;
        if (chaptersAvailable.containsKey(chapterNumber)) {
            try {
                downloader.saveChapter(chapterNumber, connector.getImageUrls(chapterNumber), true);
                result = new Chapter(chapterNumber);
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
