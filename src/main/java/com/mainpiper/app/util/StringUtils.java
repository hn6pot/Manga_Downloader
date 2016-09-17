package com.mainpiper.app.util;

import java.io.File;
import java.text.Normalizer;
import java.util.regex.Pattern;

import org.apache.commons.lang3.text.WordUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StringUtils {

    public final static char dot = '.';
    public final static String jpgExtension = ".jpg";
    public final static String zipExtension = ".zip";
    public final static String cbzExtension = ".cbz";
    private final static String jsonExtension = ".json";

    private final static String content = "content";

    // TODO -> Move in MangaFox class

    public static boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static String deAccent(final String str) {
        final String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
        final Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(nfdNormalizedString).replaceAll("");
    }

    public static boolean isChapterNumber(String chaine) {
        boolean valeur = true;
        char[] tab = chaine.toCharArray();

        for (char carac : tab) {
            if (!Character.isDigit(carac) && valeur) {
                if (!(carac == dot)) {
                    valeur = false;
                }
            }
        }

        return valeur;
    }

    // public static String getPageLinkModel(String pageUrl) {
    // String model = pageUrl;
    // int startIndex = 0;
    // int endIndex = 0;
    //
    // for (int i = model.length() - 1; i >= 0; i--) {
    // if (model.charAt(i) == '/' && startIndex == 0) {
    // startIndex = i + 1;
    // } else if (model.charAt(i) == '.' && endIndex == 0) {
    // endIndex = i;
    // }
    // }
    //
    // String modelStart = model.substring(0, startIndex);
    // String modelEnd = model.substring(endIndex, model.length());
    //
    // model = modelStart + "%d" + modelEnd;
    // return model;
    // }

    public static String substringBetween(final String str, final String tag) {
        return substringBetween(str, tag, tag);
    }

    public static String substringBetween(final String str, final String open, final String close) {
        if (str == null || open == null || close == null) {
            return null;
        }

        final int start = str.indexOf(open);
        if (start != -1) {
            final int end = str.indexOf(close, start + open.length());
            if (end != -1) {
                return str.substring(start + open.length(), end);
            }
        }
        return null;
    }

    public static String makePathconcat(String directory, String file) {
        return directory + File.separator + file;
    }

    public static String makeFileName(File fileLocation, String imageNumber) {
        return fileLocation.getPath() + File.separator + imageNumber + jpgExtension;
    }

    public static String makeCbzName(File chapter) {
        return chapter.getPath() + cbzExtension;
    }

    public static String getDefaultMangaName(String mangaName) {
        return WordUtils.capitalizeFully(mangaName.replace("-", " ").replace("_", " "));
    }

    public static String getPath(String mangaName, String defaultDirectory) {
        return defaultDirectory + File.separator + mangaName + jsonExtension;
    }

    public static String getDefaultPath(String mangaName, String defaultDirectory) {
        return defaultDirectory + File.separator + mangaName;
    }
}
