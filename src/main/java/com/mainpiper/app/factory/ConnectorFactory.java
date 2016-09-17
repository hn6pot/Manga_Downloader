package com.mainpiper.app.factory;

import java.lang.reflect.Constructor;

import com.mainpiper.app.enums.MangaWebsite;
import com.mainpiper.app.net.Connector;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConnectorFactory {

    private ConnectorFactory() {
    }

    public static Connector createConnector(String mangaName, MangaWebsite mw) {
        Connector ac;
        try {
            Constructor<?> constructor = mw.getConstructorClass().getConstructor(String.class);
            ac = (Connector) constructor.newInstance(mangaName);
        } catch (Exception e) {
            ac = null;
            log.error("Fuck this shit ! [Dev Error]", e);
            // e.printStackTrace();
        }
        return ac;
    }

    public static Connector Duplicate(Connector con) {
        return con.getNew();
    }
}
