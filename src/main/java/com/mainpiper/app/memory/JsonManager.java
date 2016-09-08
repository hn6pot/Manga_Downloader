package com.mainpiper.app.memory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mainpiper.app.enums.MangaWebsite;
import com.mainpiper.app.exceptions.TerminateBatchException;
import com.mainpiper.app.model.Chapter;
import com.mainpiper.app.model.Manga;
import com.mainpiper.app.util.StringUtils;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public class JsonManager {

    private final static String DEFAULT_CHARSET = "UTF-8";

    private final Gson GSON;
    private final String defaultDirectory;
    private final File mangaJson;
    private final Manga manga;

    public JsonManager(String mangaName, MangaWebsite WebSources, String defaultDirectory, Boolean checkDirectory) {
        GSON = new GsonBuilder().setPrettyPrinting().create();
        this.defaultDirectory = defaultDirectory;
        mangaJson = new File(StringUtils.getPath(mangaName, defaultDirectory));
        manga = getManga(mangaName, WebSources);
        if (checkDirectory) {
            updateMangaFromDirectory();
        }
    }

    public void updateJSON(List<Chapter> chapters) {
        log.debug("Manga object update in progress");
        manga.updateChapters(chapters);
        try {
            FileUtils.writeStringToFile(mangaJson, GSON.toJson(manga), Charset.forName(DEFAULT_CHARSET));
        } catch (IOException ex) {
            log.error("Unable to create or update the json file.\n, ", ex);
            throw new TerminateBatchException(TerminateBatchException.EXIT_CODE_ERROR_DURING_JSON_UPDATE,
                    "Unable to create or update the json file");
        } catch (Exception e) {
            log.debug("Unexpected Error occured during the Json file Update: ", e);
            throw new TerminateBatchException(TerminateBatchException.EXIT_CODE_UNKNOWN,
                    "Unexpected Error occured during the Json file Update", e);
        }
        log.info("Manga update ended without issues, json file has been overwrite");
    }

    public void updateMangaFromDirectory() {
        String path = StringUtils.getDefaultPath(manga.getName(), defaultDirectory);
        File defaultDirectory = new File(path);
        updateJSON(checkDirectory(defaultDirectory));
    }

    private Manga getManga(String mangaName, MangaWebsite webSource) {
        Manga result = null;
        if (mangaJson.exists()) {
            result = getMangaFromJson();
            if (webSource != null) {
                result.setSource(webSource);
            }
        } else {
            if (webSource == null) {
                String error = "First Download of : " + mangaName + ", you need to provide Web Sources";
                throw new TerminateBatchException(TerminateBatchException.EXIT_CODE_NO_SOURCE_PROVIDED, error);
            } else {
                log.info("First Download of : {}", mangaName);
                result = new Manga(mangaName, webSource);
            }
        }
        return result;
    }

    private Manga getMangaFromJson() {
        String jsonContent = new String();
        try {
            jsonContent = FileUtils.readFileToString(mangaJson, Charset.forName(DEFAULT_CHARSET));
        } catch (IOException ex) {
            log.debug("Cannot read the Json file content", ex);
            throw new TerminateBatchException(TerminateBatchException.EXIT_CODE_NO_SOURCE_PROVIDED,
                    "Cannot read the Json file content");
        } catch (Exception e) {
            log.error("Unexpected Error occured : ", e);
            throw new TerminateBatchException(TerminateBatchException.EXIT_CODE_UNKNOWN, e);
        }
        return GSON.fromJson(jsonContent, Manga.class);
    }

    private List<Chapter> checkDirectory(File currentFile) {
        log.trace("Directory checked : {}", currentFile);
        List<Chapter> result = new ArrayList<Chapter>();
        if (currentFile.exists()) {
            if (currentFile.isDirectory()) {
                File[] files = currentFile.listFiles();
                for (File f : files) {
                    result.addAll(checkDirectory(f));
                }
            } else {
                String ext = FilenameUtils.getExtension(currentFile.getPath());
                log.trace("Extension founded : {}", ext);
                if (ext.equals("cbz")) {
                    // TODO add mangas download without cbz format
                    String chapterNumber = currentFile.getName().replace(StringUtils.cbzExtension, "");
                    Chapter chapter = new Chapter(chapterNumber);
                    log.trace("Chapter founded : {}", chapterNumber);
                    result.add(chapter);
                } else {
                    throw new TerminateBatchException(TerminateBatchException.EXIT_CODE_STRANGE_FILE_FOUNDED,
                            "An unknown file has been founded : " + currentFile.getAbsolutePath());
                }
            }
        }
        return result;
    }
}
