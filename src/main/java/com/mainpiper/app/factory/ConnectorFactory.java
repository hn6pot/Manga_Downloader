package com.mainpiper.app.factory;

import java.lang.reflect.Constructor;

import com.mainpiper.app.enums.MangaWebsite;
import com.mainpiper.app.exceptions.TerminateBatchException;
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
        } catch (TerminateBatchException tb) {
            throw tb;
        } catch (Exception e) {
            log.error("Fuck this shit ! [Dev Error]", e);
            throw new TerminateBatchException(TerminateBatchException.EXIT_CODE_UNKNOWN,
                    "Fuck this shit ! [Dev Error]");
        }
        return ac;
    }

    public static Connector Duplicate(Connector con) {
        return con.getNew();
    }
}
