package com.mainpiper.app.tests.poubelle;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import com.mainpiper.app.net.AbstractConnector;
import com.mainpiper.app.net.connectors.JapscanConnector;
import com.mainpiper.app.net.connectors.LelScanConnector;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class testConnector {
	private static final String mangaName = "One Piece";

	private static class testGenericConnector {
		// TODO rename class
		private AbstractConnector conn;
		private static Map<String, Class<?>> m;

		private static void mapGeneration() {
			Map<String, Class<?>> m = new HashMap<String, Class<?>>();
			m.put("Japscan", JapscanConnector.class);
			m.put("Lelscan", LelScanConnector.class);
			testGenericConnector.m = m;
		}

		public testGenericConnector(String mangaName, String webSite) {
			mapGeneration();
			AbstractConnector conn = getConnector(mangaName, getConnectorClass(webSite));
			this.conn = conn;
		}

		public void run() {
			System.out.println(conn.getMangaUrls());
			System.out.println(conn.getChaptersUrl());
			System.out.println(conn.getImageUrls("752"));
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
				log.error("Fuck this shit, Human error ! Let's check your fucking commit !", e);
				return null;
			}
		}
	}

	public static void main(String[] args) {

		testGenericConnector test = new testGenericConnector(mangaName, "Japscan");
		test.run();

	}
}
