package com.mainpiper.app.utils;

import java.text.Normalizer;
import java.util.HashMap;
import java.util.regex.Pattern;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StringUtils {
	
	private static final char dot = '.';

//TODO -> Move in MangaFox class
    

    public static String deAccent(final String str) {
        final String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
        final Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(nfdNormalizedString).replaceAll("");
    }
    
    public static boolean isChapterNumber(String chaine){ 
    	boolean valeur = true; 
    	char[] tab = chaine.toCharArray(); 

    	for(char carac : tab){ 
	    	if(!Character.isDigit(carac) && valeur){
	    		if(!(carac == dot)){valeur = false;} 
	    		} 
    	} 

    	return valeur; 
    	} 
    
    public static String checkChapter(String chapterNumber, HashMap<String, String> chaptersUrl){
		 String chapterUrl = chaptersUrl.get(chapterNumber);
		   if(chapterNumber == null){
			   if (StringUtils.isChapterNumber(chapterNumber)){
				   log.error("The chapter {} is not yet available !", chapterNumber);
			   }
			   else {
			   log.error("There is no chapter {}, you probably misspelled it");
			   }
			   return null;
		   }
		  return chapterUrl;
	}

//    public static String getPageLinkModel(String pageUrl) {
//        String model = pageUrl;
//        int startIndex = 0;
//        int endIndex = 0;
//
//        for (int i = model.length() - 1; i >= 0; i--) {
//            if (model.charAt(i) == '/' && startIndex == 0) {
//                startIndex = i + 1;
//            } else if (model.charAt(i) == '.' && endIndex == 0) {
//                endIndex = i;
//            }
//        }
//
//        String modelStart = model.substring(0, startIndex);
//        String modelEnd = model.substring(endIndex, model.length());
//
//        model = modelStart + "%d" + modelEnd;
//        return model;
//    }

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
}
