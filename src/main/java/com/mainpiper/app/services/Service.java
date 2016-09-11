package com.mainpiper.app.services;

import java.io.File;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

import com.mainpiper.app.args.CliOptions;
import com.mainpiper.app.args.Config;
import com.mainpiper.app.exceptions.TerminateBatchException;
import com.mainpiper.app.exceptions.TerminateScriptProperly;
import com.mainpiper.app.factory.ConnectorFactory;
import com.mainpiper.app.memory.JsonManager;
import com.mainpiper.app.model.Chapter;
import com.mainpiper.app.model.Manga;
import com.mainpiper.app.net.Connector;
import com.mainpiper.app.net.Downloader;
import com.mainpiper.app.util.StringUtils;

import display.Display;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Service {

    private final Connector connector;
    private final Downloader downloader;
    private final JsonManager jsonManager;
    private final Map<String, String> chaptersAvailable;
    private final Config conf;

    public Service(String mangaName, Config conf) {
        String finalMangaName = StringUtils.getDefaultMangaName(mangaName);
        this.conf = conf;
        jsonManager = new JsonManager(finalMangaName, conf.getWebSources(), conf.getDefaultDownloadDirectory(),
                conf.getCheckDirectory());
        Manga manga = jsonManager.getManga();
        connector = ConnectorFactory.createConnector(manga.getName(), manga.getSource());
        downloader = new Downloader(finalMangaName, conf.getDefaultDownloadDirectory());
        chaptersAvailable = connector.getChaptersUrl();

        checkChaptersAvailable(manga);
    }

    // Constructor use for the Update Functionality
    public Service(File mangaJsonFile, Config conf) {
        this.conf = conf;
        jsonManager = new JsonManager(mangaJsonFile, conf.getDefaultDownloadDirectory(), conf.getCheckDirectory());
        Manga manga = jsonManager.getManga();
        connector = ConnectorFactory.createConnector(manga.getName(), manga.getSource());
        downloader = new Downloader(manga.getName(), conf.getDefaultDownloadDirectory());
        chaptersAvailable = connector.getChaptersUrl();

        checkChaptersAvailable(manga);
        Boolean answer = Display.displayChapterAvailable(manga.getName(), chaptersAvailable.keySet());
        if (!answer) {
            throw new TerminateScriptProperly();
        }
    }

    public void downloadManga() throws InterruptedException, ExecutionException {
        log.debug("downloadManga in progress");
        log.info("Downloading entire Manga");
        List<Chapter> chapters = null;

        // TODO number of threads depends on config !
        ForkJoinPool forkJoinPool = new ForkJoinPool(16);
        try {
            chapters = forkJoinPool.submit(() -> downloadTask(chaptersAvailable.keySet())).get();
        } catch (InterruptedException ie) {
            throw new TerminateBatchException(TerminateBatchException.EXIT_CODE_UNKNOWN, ie);
        } catch (ExecutionException ee) {
            throw new TerminateBatchException(TerminateBatchException.EXIT_CODE_UNKNOWN, ee);
        }

        log.debug("{} chapter(s) downloaded successfully", chapters.size());
        jsonManager.updateJSON(chapters);
    }

    public void downloadChapters() {
        String[] chapterNumbers = conf.getCli().getOptionValues(CliOptions.OPT_CHAPTER);
        List<String> chaptersAvailable = Arrays.asList(chapterNumbers);
        log.debug("downloadChapters in progress");
        List<Chapter> chapters = null;
        ForkJoinPool forkJoinPool = new ForkJoinPool(16);
        try {
            chapters = forkJoinPool.submit(() -> downloadTask(chaptersAvailable)).get();
        } catch (InterruptedException ie) {
            throw new TerminateBatchException(TerminateBatchException.EXIT_CODE_UNKNOWN, ie);
        } catch (ExecutionException ee) {
            throw new TerminateBatchException(TerminateBatchException.EXIT_CODE_UNKNOWN, ee);
        }

        log.debug("{} chapter(s) downloaded successfully", chapters.size());
        jsonManager.updateJSON(chapters);
    }

    private List<Chapter> downloadTask(List<String> chaptersAvailable) {
        return chaptersAvailable.stream().parallel().map(chapter -> downloadChapter(chapter))
                .filter(elem -> elem != null).collect(Collectors.toList());
    }

    private List<Chapter> downloadTask(Set<String> chaptersAvailable) {
        return chaptersAvailable.stream().parallel().map(chapter -> downloadChapter(chapter))
                .filter(elem -> elem != null).collect(Collectors.toList());
    }

    private Chapter downloadChapter(String chapterNumber) {
        Chapter result = null;
        if (chaptersAvailable.containsKey(chapterNumber)) {
            try {
                downloader.saveChapter(chapterNumber, connector.getImageUrls(chapterNumber), true);
                result = new Chapter(chapterNumber);

            } catch (Exception e) {
                log.error("An unexpected error Occured !", e);
            }
        } else {
            log.info("Chapter is not available yet or you already downloaded it");
            // TODO add chapter available
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
