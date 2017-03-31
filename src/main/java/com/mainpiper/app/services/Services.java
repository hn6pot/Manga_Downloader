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
import com.mainpiper.app.exceptions.TerminateBatchException;
import com.mainpiper.app.exceptions.TerminateScriptProperly;
import com.mainpiper.app.factory.ConnectorFactory;
import com.mainpiper.app.memory.JsonManager;
import com.mainpiper.app.model.Chapter;
import com.mainpiper.app.model.Manga;
import com.mainpiper.app.net.Connector;
import com.mainpiper.app.net.Downloader;
import com.mainpiper.app.net.IntranetApi;
import com.mainpiper.app.util.StringUtils;
import com.mainpiper.app.util.Time;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class Services {

    private final Connector connector;
    private final Downloader downloader;
    private final JsonManager jsonManager;
    private final Map<String, String> chaptersAvailable;
    private final IntranetApi api;
    private final Config conf;
    private final Time time;
    
    private List<Chapter> chaptersDownloaded;

    public Services(String mangaName, Config conf) {
        time = new Time();
        String finalMangaName = StringUtils.getDefaultMangaName(mangaName);
        this.conf = conf;
        jsonManager = new JsonManager(finalMangaName, conf.getWebSources(), conf.getDefaultDownloadDirectory(), 
        		conf.getDefaultMemoryDirectory(), conf.getCheckDirectory());
        api = new IntranetApi(conf.getApiUsername(), conf.getApiPassword(), conf.getApiUrl(), conf.getUSER_AGENT());
        Manga manga = jsonManager.getManga();
        if(!manga.getSource().getAvailable()){
        	throw new TerminateBatchException(TerminateBatchException.EXIT_CODE_WEB_SOURCE_NOT_AVAILABLE,
                    "The Web Source " + manga.getSource().getName() + " is not available yet, we apologize for the disturbance");
        }
        connector = ConnectorFactory.createConnector(manga.getName(), manga.getSource());
        downloader = new Downloader(finalMangaName, conf.getDefaultDownloadDirectory());
        chaptersAvailable = connector.getChaptersUrl();
        if(conf.getCheckApi()){
        	checkChapterAvailableWithApi(manga);
        }
        else{
        	checkChaptersAvailable(manga);
        }  
        log.trace("Services Initialization ended");
    }

    // Constructor use for the Update Functionality
    public Services(File mangaJsonFile, Config conf) {
        time = new Time();
        this.conf = conf;
        jsonManager = new JsonManager(mangaJsonFile, conf.getDefaultDownloadDirectory(), conf.getDefaultMemoryDirectory(),
        		conf.getCheckDirectory());
        api = new IntranetApi(conf.getApiUsername(), conf.getApiPassword(), conf.getApiUrl(), conf.getUSER_AGENT());
        Manga manga = jsonManager.getManga();
        if(!manga.getSource().getAvailable()){
        	throw new TerminateBatchException(TerminateBatchException.EXIT_CODE_WEB_SOURCE_NOT_AVAILABLE,
                    "This Web Source is not available yet");
        }
        connector = ConnectorFactory.createConnector(manga.getName(), manga.getSource());
        downloader = new Downloader(manga.getName(), conf.getDefaultDownloadDirectory());
        chaptersAvailable = connector.getChaptersUrl();
        Display.displaySTitle(manga.getName() + "Downloading");
        Display.displayInfo(manga.getSource().getName() + " Source Selected");
        checkChaptersAvailable(manga);
        Boolean answer = Display.displayChapterAvailable(manga.getName(), chaptersAvailable.keySet(), conf.getHardUpdate());
        if (!answer) {
            throw new TerminateScriptProperly();
        }

        log.trace("Services Initialization ended");
    }

    public void downloadManga(Boolean displayMsg) throws InterruptedException, ExecutionException {
        log.debug("downloadManga in progress");
        if(displayMsg){
        	log.info("Downloading entire Manga");
        	Display.displayInfo("Entire Manga option selected.");
        }

        chaptersDownloaded = chaptersAvailable.keySet().stream().parallel().map(chapter -> downloadChapter(chapter))
                .filter(elem -> elem != null).collect(Collectors.toList());

        String info = chaptersDownloaded.size() + " chapter(s) downloaded successfully.";
        Display.displayInfo(info);
        log.debug(info);

        jsonManager.updateJSON(chaptersDownloaded);
        String duration = time.getFinalTime();
        if(displayMsg)
        	Display.displayTitle("Thank you for using a MainPiper&Co Production ! (Exec Time : " + duration + " )");
    }

    public void downloadChapters() {
        String[] chapterNumbers = conf.getCli().getOptionValues(CliOptions.OPT_CHAPTER)[0].split("-");
        List<String> chaptersAvailable = Arrays.asList(chapterNumbers);
        log.debug("downloadChapters in progress");

        this.chaptersDownloaded = chaptersAvailable.stream().parallel().map(chapter -> downloadChapter(chapter))
                .filter(elem -> elem != null).collect(Collectors.toList());

        String info = "** " + chaptersDownloaded.size() + " chapter(s) downloaded successfully.";
        Display.displaySave(info);
        log.debug(info);

        jsonManager.updateJSON(chaptersDownloaded);
        String duration = time.getFinalTime();
        Display.displayTitle("Thank you for using a MainPiper&Co Production ! (Exec Time : " + duration + " )");

    }

    private Chapter downloadChapter(String chapterNumber) {
        // Display.displaySTitle("Chapter " + chapterNumber);
        Chapter result = null;
        Connector conn = ConnectorFactory.Duplicate(connector);
        if (chaptersAvailable.containsKey(chapterNumber)) {
            try {
                downloader.saveChapter(chapterNumber, conn.getImageUrls(chaptersAvailable.get(chapterNumber)),
                        conf.getCbz());
                result = new Chapter(chapterNumber);

            } catch (Exception e) {
                Display.displayError("An unexpected error Occured, Check logs for further information !");
                log.error("An unexpected error Occured !", e);
            }
        } else {
            String info = "Chapter " + chapterNumber + " is not available yet or you already downloaded it.";
            Display.displayWarn(info);
            log.warn(info);
        }
        return result;
    }

    private void checkChapterAvailableWithApi(Manga manga){
        	String msg = "Memory Update with Server API in progress...";
        	log.info(msg);
        	Display.displaySTitle(msg);
        	List<String> apiContent = api.getChapterList(manga.getName());
        	for (Iterator<Chapter> it = manga.getChapters().iterator(); it.hasNext();) {
                String key = it.next().getNumber();
     
                try {
                    chaptersAvailable.remove(key);
                    
                } catch (NullPointerException ne) {
                    // case chapter is no more available on the web site
                	msg = "Chapter "+ key + ", is no more available on the webSite {} keep it hide from the law !" +  manga.getSource().getName();
                    Display.displayWarn(msg);
                	log.trace(msg);
         
                }
                try{
                	apiContent.remove(key);
                } catch (NullPointerException ne) {
                    // case chapter is no more available on the web site
                	msg = "Chapter " + key + ", is no upload on the Intranet but you have downloaded it!";
                	Display.displayWarn(msg);
                    log.trace(msg);
                    continue;
                } 
            }
        	for (Iterator<String> it = apiContent.iterator(); it.hasNext();) {
                String key = it.next();
                try {
                    chaptersAvailable.remove(key);
                } catch (NullPointerException ne) {
                    // case chapter is no more available on the web site
                    log.trace("Chapter {}, is no more available on the webSite {} keep it hide from the law !", key,
                            manga.getSource().getName());
                    continue;
                }            
            }
        	manga.updateChapters(apiContent.stream().map(chapterNumber -> new Chapter(chapterNumber)).collect(Collectors.toList()));
            if (chaptersAvailable.isEmpty()) {
                throw new TerminateScriptProperly("There is no new chapter available yet !");
            }
    }
    
    private void checkChaptersAvailable(Manga manga) {
        log.debug("Check for any new chapter available on the WebSource : {}", manga.getSource().getName());
        
        for (Iterator<Chapter> it = manga.getChapters().iterator(); it.hasNext();) {
            String key = it.next().getNumber();
            try {
                chaptersAvailable.remove(key);
            } catch (NullPointerException ne) {
                // case chapter is no more available on the web site
                log.trace("Chapter {}, is no more available on the webSite {} keep it hide from the law !", key,
                        manga.getSource().getName());
                continue;
            }
        }
        if (chaptersAvailable.isEmpty()) {
            throw new TerminateScriptProperly("There is no new chapter available yet !");
        }
    }

}
