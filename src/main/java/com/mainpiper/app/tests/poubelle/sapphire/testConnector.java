package com.mainpiper.app.tests.poubelle.sapphire;

import java.util.Map;

import com.mainpiper.app.enums.MangaWebsite;
import com.mainpiper.app.factory.ConnectorFactory;
import com.mainpiper.app.net.Connector;
import com.mainpiper.app.net.connectors.LireScanConnector;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class testConnector {
    private static final String mangaName = "one-piece";

    private static class testGenericConnector {
        // TODO rename class
        private Connector conn;
        private static Map<String, Class<?>> m;

        public testGenericConnector(String mangaName, String webSite) {
            conn = ConnectorFactory.createConnector(mangaName, MangaWebsite.getSource(webSite));
        }

        public testGenericConnector(String mangaName) {
            conn = new LireScanConnector(mangaName);
            // conn = new MangaFoxConnector(mangaName);
        }

        public void run() {
            System.out.println(conn.getMangaUrls());
            Map<String, String> toto = conn.getChaptersUrl();
            System.out.println(toto);
            System.out.println(conn.getImageUrls(toto.get("800")));
        }
    }

    public static void main(String[] args) {

        testGenericConnector test = new testGenericConnector(mangaName);

        test.run();

    }
}
