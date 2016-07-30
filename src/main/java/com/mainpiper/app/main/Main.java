package com.mainpiper.app.main;

import java.util.Iterator;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.ParseException;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

import com.mainpiper.app.connector.ConnectorInterface;
import com.mainpiper.app.connector.sources.JapscanConnector;
import com.mainpiper.app.connector.sources.LelScanConnector;
import com.mainpiper.app.enums.CliArgumentsEnum;
import com.mainpiper.app.enums.MangaWebsitesEnum;
import com.mainpiper.app.services.DownloadService;
import com.mainpiper.app.utils.StringUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Main Piper Dev
 */

@Slf4j
public class Main {
    public static final String APP_VERSION = "1.0";
 


    public static void main(String[] args) {
        CommandLineParser parser = new DefaultParser();

        VulpesCliOptions options = VulpesCliOptions.getInstance();
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("ant", options);

        try {
            CommandLine line = parser.parse(options, args);
            System.out.println(line.hasOption("s"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        DownloadService ds;
        String cli = "";
        for (String s : args) {
            cli += s + " ";
        }
      
        
//        LelScanConnector toto = new LelScanConnector("One Piece");
//        System.out.println(toto.getMangaUrls());
//        System.out.println(toto.getChaptersUrl());
//        
//        System.out.println(toto.getImageUrls("752"));
        
        JapscanConnector jap = new JapscanConnector("one-piece");
        System.out.println(jap.getMangaUrls());
//        System.out.println(jap.getChaptersUrl());
//        System.out.println(jap.getImageUrls("820"));
//        
        /*******************************TEST***********************************/
        Connection connector = Jsoup.connect("http://www.japscan.com/mangas/")
		.header("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.64 Safari/537.11")
 		.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
 		.header("Accept-Charset", "ISO-8859-1,utf-8;q=0.7,*;q=0.3")
 		.header("Accept-Language", "en-US,en;q=0.8")
 		.header("keep-alive", "keep-alive")
		.timeout(5000)
		.ignoreContentType(true).parser(Parser.htmlParser());
        
        Elements option = null;
    	try {
    		   option = connector.get().select("body a");
    		   
    	   }
    	   catch(Exception e){
    		   log.warn("This url does not work properly : [Need Code Review]");
    			   e.printStackTrace();
    		   }
    	
    	Iterator<Element> it = option.iterator();
    	Boolean first = true;
    	while(it.hasNext()){
    		Element o = it.next();

    		
    		
    	}
        
        	
        
        log.info("toto");
//        String mangaName = null;
//        if (args.length > 0) {
//            mangaName = args[0];
//        }
//        switch (args.length) {
//            case 0:
//                Help.printHelp();
//                break;
//
//            case 1:
//                if (CliArgumentsEnum.HELP.cliArg().equalsIgnoreCase(mangaName)) {
//                    Help.printHelp();
//                } else {
//                    ds = new MangaFoxDowloadService(mangaName);
//                    ds.downloadManga();
//                }
//                break;
//
//            case 3: {
//                int[] indexes = identifyCliOptions(args);
//
//
//                int wsArgIndex = indexes[0];
//                int volumeArgIndex = indexes[1];
//
//                for (int i = 0; i < args.length; i++) {
//                    if (CliArgumentsEnum.SITE.cliArg().equalsIgnoreCase(args[i])) {
//                        wsArgIndex = i;
//                    } else if (CliArgumentsEnum.VOLUME.cliArg().equalsIgnoreCase(args[i])) {
//                        volumeArgIndex = i;
//                    }
//                }
//
//                if (wsArgIndex >= 1 && wsArgIndex + 1 < args.length) {
//                    String site = args[wsArgIndex + 1];
//
//                    ds = getDownloadServiceByWebsite(site, mangaName);
//                    if (ds != null) {
//                        ds.downloadManga();
//                    } else {
//                        //TODO WEBSITE UNKNOWN
//                    }
//                } else if (volumeArgIndex >= 1 && volumeArgIndex + 1 < args.length) {
//                    String volumeNumber = args[volumeArgIndex + 1];
//
//                    ds = new MangaFoxDowloadService(mangaName);
//                    ds.downloadVolume(volumeNumber);
//                } else {
//                    Help.printHelp();
//                }
//                break;
//            }
//
//            case 5: {
//                int wsArgIndex = -1;
//                int volumeArgIndex = -1;
//                int chapterArgIndex = -1;
//
//                for (int i = 0; i < args.length; i++) {
//                    if (CliArgumentsEnum.SITE.cliArg().equalsIgnoreCase(args[i])) {
//                        wsArgIndex = i;
//                    } else if (CliArgumentsEnum.VOLUME.cliArg().equalsIgnoreCase(args[i])) {
//                        volumeArgIndex = i;
//                    } else if (CliArgumentsEnum.CHAPTER.cliArg().equalsIgnoreCase(args[i])) {
//                        chapterArgIndex = i;
//                    }
//                }
//
//                if (wsArgIndex >= 1 && wsArgIndex + 1 < args.length &&
//                        volumeArgIndex >= 1 && volumeArgIndex + 1 < args.length) {
//
//                } else if (volumeArgIndex >= 1 && volumeArgIndex + 1 < args.length &&
//                        chapterArgIndex >= 1 && chapterArgIndex + 1 < args.length) {
//
//                } else {
//
//                }
//
//
//                break;
//            }
//
//            case 7:
//                int wsArgIndex = -1;
//                int volumeArgIndex = -1;
//                int chapterArgIndex = -1;
//
//                for (int i = 0; i < args.length; i++) {
//                    if (CliArgumentsEnum.SITE.cliArg().equalsIgnoreCase(args[i])) {
//                        wsArgIndex = i;
//                    } else if (CliArgumentsEnum.VOLUME.cliArg().equalsIgnoreCase(args[i])) {
//                        volumeArgIndex = i;
//                    } else if (CliArgumentsEnum.CHAPTER.cliArg().equalsIgnoreCase(args[i])) {
//                        chapterArgIndex = i;
//                    }
//                }
//
//                if (wsArgIndex >= 1 && wsArgIndex + 1 < args.length &&
//                        volumeArgIndex >= 1 && volumeArgIndex + 1 < args.length &&
//                        chapterArgIndex >= 1 && chapterArgIndex + 1 < args.length) {
//
//                    String site = args[wsArgIndex + 1];
//
//                    ds = getDownloadServiceByWebsite(site, mangaName);
//                    if (ds != null) {
//                        String volumeNumber = args[volumeArgIndex + 1];
//                        String chapterNumber = args[chapterArgIndex + 1];
//
//                        ds.downloadChapter(volumeNumber, chapterNumber);
//                    }
//
//                } else {
//                    Help.printHelp();
//                }
//                break;
//
//            default:
//                Help.printHelp();
//                break;

//        }
    }

    /**
     * Identify indexes of CLI options
     * indexes[0] = -s
     * indexes[1] = -v
     * indexes[2] = -c
     *
     * if indexes[i] = -1 then cli options is not present
     */
    private static int[] identifyCliOptions(String[] args) {
        int[] indexes = {-1, -1, -1};

        for (int i = 0; i < args.length; i++) {
            if (CliArgumentsEnum.SITE.getCliArg().equalsIgnoreCase(args[i])) {
                indexes[0] = i;
            } else if (CliArgumentsEnum.VOLUME.getCliArg().equalsIgnoreCase(args[i])) {
                indexes[1] = i;
            } else if (CliArgumentsEnum.CHAPTER.getCliArg().equalsIgnoreCase(args[i])) {
                indexes[2] = i;
            }
        }

        return indexes;
    }

    private static DownloadService getDownloadServiceByWebsite(String ws, String mangaName) {
        if (MangaWebsitesEnum.MANGAFOX.getCliShortcut().equalsIgnoreCase(ws)) {
            return null;
            //new MangaFoxDowloadService(mangaName);
        }

        return null;
    }
}
