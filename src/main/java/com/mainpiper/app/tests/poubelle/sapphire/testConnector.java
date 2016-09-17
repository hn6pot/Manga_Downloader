package com.mainpiper.app.tests.poubelle.sapphire;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import com.mainpiper.app.net.Connector;
import com.mainpiper.app.net.connectors.JapscanConnector;
import com.mainpiper.app.net.connectors.LelScanConnector;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class testConnector {
    private static final String mangaName = "Tower Of God";

    private static class testGenericConnector {
        // TODO rename class
        private Connector conn;
        private static Map<String, Class<?>> m;

        private static void mapGeneration() {
            Map<String, Class<?>> m = new HashMap<String, Class<?>>();
            m.put("Japscan", JapscanConnector.class);
            m.put("Lelscan", LelScanConnector.class);
            testGenericConnector.m = m;
        }

        public testGenericConnector(String mangaName, String webSite) {
            mapGeneration();
            Connector conn = getConnector(mangaName, getConnectorClass(webSite));
            this.conn = conn;
        }

        public void run() {
            System.out.println(conn.getMangaUrls());
            System.out.println(conn.getChaptersUrl());
            System.out.println(conn.getImageUrls("21"));
        }

        private static Class<?> getConnectorClass(String webSite) {
            Class<?> result = null;
            try {
                result = m.get(webSite);
            } catch (Exception e) {
                // log.error("WebSite unknown !");
            }
            return result;

        }

        private static Connector getConnector(String mangaName, Class<?> connectorClass) {
            try {
                Constructor<?> cons = connectorClass.getConstructor(String.class);
                Connector t = (Connector) cons.newInstance(mangaName);
                return t;
            } catch (Exception e) {
                // log.error("Fuck this shit, Human error ! Let's check your
                // fucking commit !", e);
                return null;
            }
        }
    }

    public static void main(String[] args) {

        testGenericConnector test = new testGenericConnector(mangaName, "Japscan");
        test.run();

    }
}
