package com.mainpiper.app.memory;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.nio.charset.Charset;

import org.apache.commons.io.FileUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mainpiper.app.model.AbstractManga;
import com.mainpiper.app.model.mangas.SimpleManga;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public class JsonManager {
    // ?? add json serialization
    private final static String jsonDirectory = "Json";
    private final static String DEFAULT_CHARSET = "UTF-8";

    private final Gson GSON;
    private final File mangaJson;
    private final Class<?> mangaClass;
    private final AbstractManga manga;

    public JsonManager(Class<?> mangaClass, String mangaName) {
        GSON = new GsonBuilder().setPrettyPrinting().create();
        this.mangaClass = mangaClass;
        mangaJson = new File(getPath(mangaName));
        manga = getManga(mangaName);
    }

    private static String getPath(String mangaName) {
        return jsonDirectory + File.separator + mangaName;
    }

    public void updateJSON() {
        try {
            FileUtils.writeStringToFile(mangaJson, GSON.toJson(manga), Charset.forName(DEFAULT_CHARSET));

        } catch (IOException ex) {
            log.error("Unable to create or update the json file.\n, ", ex);
        } catch (Exception e) {
            log.error("Unexpected Error occured during the Json file Update: ", e);
        }
    }

    private AbstractManga getManga(String mangaName) {
        // throws InstantiationException, IllegalAccessException,
        // IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException
        AbstractManga manga = null;

        if (mangaJson.exists()) {
            String jsonContent = new String();
            try {
                jsonContent = FileUtils.readFileToString(mangaJson, Charset.forName(DEFAULT_CHARSET));
            } catch (IOException ex) {
                log.error("Cannot read the Json file content", ex);
            } catch (Exception e) {
                log.error("Unexpected Error occured : ", e);
            }
            manga = (SimpleManga) GSON.fromJson(jsonContent, mangaClass);

        } else {
            log.warn("Manga unknown :" + mangaName);
            log.info("Json file generation");
            try {
                Constructor<?> cons = mangaClass.getConstructor(String.class);
                manga = (AbstractManga) cons.newInstance(mangaName);
            } catch (Exception e) {
                log.error("Fuck this shit ! [Dev Error]", e);
                return null;
            }
        }
        return manga;
    }

}
