package com.mainpiper.app.memory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.commons.io.FileUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mainpiper.app.exceptions.TerminateBatchException;
import com.mainpiper.app.model.Manga;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public class JsonManager {

    private final static String json = "Json.txt";
    private final static String DEFAULT_CHARSET = "UTF-8";

    private final Gson GSON;
    private final File mangaJson;
    private final Manga manga;

    public JsonManager(String mangaName, String WebSources, String defaultDirectory) {
        GSON = new GsonBuilder().setPrettyPrinting().create();

        mangaJson = new File(getPath(mangaName, defaultDirectory));
        manga = getManga(mangaName, WebSources);
    }

    private static String getPath(String mangaName, String defaultDirectory) {
        return defaultDirectory + File.separator + mangaName + File.separator + json;
    }

    public void updateJSON() {
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
    }

    private Manga getManga(String mangaName, String webSource) {
        Manga result = null;
        if (mangaJson.exists()) {
            result = getMangaFromJson();
            if (webSource != null) {
                result.setSource(webSource);
            }
        } else {
            log.info("First Download of : {}", mangaName);
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

}
