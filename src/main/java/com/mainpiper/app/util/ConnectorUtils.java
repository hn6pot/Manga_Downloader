package com.mainpiper.app.util;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.select.Elements;

import com.mainpiper.app.exceptions.TerminateBatchException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConnectorUtils {

    public static Elements tryConnect(Connection connector, String url, String request) {
        Elements option;
        try {
            option = connector.url(url).get().select("body " + request);
        } catch (Exception e) {
            log.warn("This url does not work properly : {} [Need Code Review]", url, e);
            throw new TerminateBatchException(TerminateBatchException.EXIT_CODE_URL_MALFORMED,
                    "This url does not work properly : " + url);
        }
        return option;
    }

    public static Elements tryFirstConnect(Connection connector, String url, String request) {
        Elements option;
        try {
            option = connector.get().select("body " + request);
        } catch (Exception e) {
            log.warn("This url does not work properly : {} [Need Code Review]", url);
            throw new TerminateBatchException(TerminateBatchException.EXIT_CODE_URL_MALFORMED,
                    "This url does not work properly : " + url);
        }
        return option;
    }

    public static Elements tryLelscan(Connection connector, String url, String urlBis) {
        Elements option;

        try {
            option = connector.url(url).get().select("body option");
        } catch (Exception e) {
            log.warn("This url does not work properly : {}", url);

            log.info("we change the url : {}", urlBis);
            try {
                option = connector.url(urlBis).get().select("body option");
            } catch (Exception ex) {
                log.warn("This url does not work properly too: {}", urlBis);
                log.error("We cannot find the chapters url");
                // TODO better error management bitch
                throw new TerminateBatchException(TerminateBatchException.EXIT_CODE_MANGA_UNKNOWN_FOR_SOURCE_PROVIDED,
                        "We cannot find the chapters url, This manga is probably not referenced on this source");
            }
        }
        return option;
    }
}
