package com.mainpiper.app.factory;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import com.mainpiper.app.net.AbstractConnector;
import com.mainpiper.app.net.connectors.JapscanConnector;
import com.mainpiper.app.net.connectors.LelScanConnector;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GenericConnector {

	private AbstractConnector conn;
	private static Map<String, Class<?>> m;

	private static void mapGeneration() {
		Map<String, Class<?>> m = new HashMap<String, Class<?>>();
		m.put("Japscan", JapscanConnector.class);
		m.put("Lelscan", LelScanConnector.class);
		GenericConnector.m = m;
	}

	public GenericConnector(String mangaName, String webSite) {
		mapGeneration();
		AbstractConnector conn = getConnector(mangaName, getConnectorClass(webSite));
		this.conn = conn;
	}

	public AbstractConnector getConnector() {
		return conn;
	}

	private static Class<?> getConnectorClass(String webSite) {
		Class<?> result = null;
		try {
			result = m.get(webSite);
		} catch (Exception e) {
			log.error("WebSite unknown !");
		}
		return result;

	}

	private static AbstractConnector getConnector(String mangaName, Class<?> connectorClass) {
		try {
			Constructor<?> cons = connectorClass.getConstructor(String.class);
			AbstractConnector t = (AbstractConnector) cons.newInstance(mangaName);
			return t;
		} catch (Exception e) {
			log.error("Fuck this shit ! (Dev Error)", e);
			return null;
		}
	}
}
