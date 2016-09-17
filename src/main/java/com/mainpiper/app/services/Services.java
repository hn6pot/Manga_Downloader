package com.mainpiper.app.services;

import java.io.File;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import com.mainpiper.app.args.CliOptions;
import com.mainpiper.app.args.Config;
import com.mainpiper.app.display.Display;
import com.mainpiper.app.exceptions.TerminateScriptProperly;
import com.mainpiper.app.factory.ConnectorFactory;
import com.mainpiper.app.memory.JsonManager;
import com.mainpiper.app.model.Chapter;
import com.mainpiper.app.model.Manga;
import com.mainpiper.app.net.Connector;
import com.mainpiper.app.net.Downloader;
import com.mainpiper.app.util.StringUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Services {

    private final Connector connector;
    private final Downloader downloader;
    private final JsonManager jsonManager;
    private final Map<String, String> chaptersAvailable;
    private final Config conf;

    public Services(String mangaName, Config conf) {
        String finalMangaName = StringUtils.getDefaultMangaName(mangaName);
        this.conf = conf;
        jsonManager = new JsonManager(finalMangaName, conf.getWebSources(), conf.getDefaultDownloadDirectory(),
                conf.getCheckDirectory());
        Manga manga = jsonManager.getManga();
        connector = ConnectorFactory.createConnector(manga.getName(), manga.getSource());
        downloader = new Downloader(finalMangaName, conf.getDefaultDownloadDirectory());
        chaptersAvailable = connector.getChaptersUrl();

        checkChaptersAvailable(manga);
        log.trace("Services Initialization ended");
    }

    // Constructor use for the Update Functionality
    public Services(File mangaJsonFile, Config conf) {
        this.conf = conf;
        jsonManager = new JsonManager(mangaJsonFile, conf.getDefaultDownloadDirectory(), conf.getCheckDirectory());
        Manga manga = jsonManager.getManga();
        connector = ConnectorFactory.createConnector(manga.getName(), manga.getSource());
        downloader = new Downloader(manga.getName(), conf.getDefaultDownloadDirectory());
        chaptersAvailable = connector.getChaptersUrl();
        Display.displayInfo(manga.getSource().getName() + "Source Selected");
        checkChaptersAvailable(manga);
        Boolean answer = Display.displayChapterAvailable(manga.getName(), chaptersAvailable.keySet());
        if (!answer) {
            throw new TerminateScriptProperly();
        }

        log.trace("Services Initialization ended");
    }

    public void downloadManga() throws InterruptedException, ExecutionException {
        log.debug("downloadManga in progress");
        log.info("Downloading entire Manga");
        Display.displayInfo("Entire Manga option selected");
        List<Chapter> chapters = null;

        chapters = chaptersAvailable.keySet().stream().parallel().map(chapter -> downloadChapter(chapter))
                .filter(elem -> elem != null).collect(Collectors.toList());

        String info = chapters.size() + " chapter(s) downloaded successfully";
        Display.displayInfo(info);
        log.debug(info);

        jsonManager.updateJSON(chapters);
        Display.displayTitle("Tank you for using a MainPiper&Co Production !");
    }

    public void downloadChapters() {
        String[] chapterNumbers = conf.getCli().getOptionValues(CliOptions.OPT_CHAPTER)[0].split("-");
        List<String> chaptersAvailable = Arrays.asList(chapterNumbers);
        log.debug("downloadChapters in progress");
        List<Chapter> chapters = null;

        chapters = chaptersAvailable.stream().parallel().map(chapter -> downloadChapter(chapter))
                .filter(elem -> elem != null).collect(Collectors.toList());

        String info = "** " + chapters.size() + " chapter(s) downloaded successfully";
        Display.displaySave(info);
        log.debug(info);

        jsonManager.updateJSON(chapters);
        Display.displayTitle("Tank you for using a MainPiper&Co Production !");
    }

    private Chapter downloadChapter(String chapterNumber) {
        // Display.displaySTitle("Chapter " + chapterNumber);
        Chapter result = null;
        Connector conn = ConnectorFactory.Duplicate(connector);
        if (chaptersAvailable.containsKey(chapterNumber)) {
            try {
                downloader.saveChapter(chapterNumber, conn.getImageUrls(chaptersAvailable.get(chapterNumber)), true);
                result = new Chapter(chapterNumber);

            } catch (Exception e) {
                Display.displayError("An unexpected error Occured, Check logs for further information !");
                log.error("An unexpected error Occured !", e);
            }
        } else {
            String info = "Chapter " + chapterNumber + " is not available yet or you already downloaded it";
            Display.displayWarn(info);
            log.warn(info);
        }
        return result;
    }

    private void checkChaptersAvailable(Manga manga) {
        log.debug("Check for any new chapter available on the WebSource : {}", manga.getSource().getName());
        for (Iterator<Chapter> it = manga.getChapters().iterator(); it.hasNext();) {
            String key = it.next().getNumber();
            try {
                chaptersAvailable.remove(key);
            } catch (NullPointerException ne) {
                // case chapter is no more available on the web site
                log.trace("Chapter {}, is no more available on the webSite {} keep it hide from the law!", key,
                        manga.getSource().getName());
                continue;
            }
        }
        if (chaptersAvailable.isEmpty()) {
            throw new TerminateScriptProperly("There is no new chapter available yet !");
        }
    }

}
