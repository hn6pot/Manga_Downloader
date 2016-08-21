package com.mainpiper.app.net;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.FileUtils;

import com.mainpiper.app.util.StringUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Downloader {
    private final static int TIME_OUT_IN_MILLIS = 5000;

    private static final byte[] buffer = new byte[1024];

    // TODO add proper file managment
    private final static String fileDirectory = "output/";
   
    private final String mangaName;
    private final File mangaDirectory;

    public Downloader(String mangaName) {
        this.mangaName = mangaName;
        mangaDirectory = new File(StringUtils.makePathconcat(fileDirectory, mangaName));
        log.debug("Downloader for : {} initialization ended properly", this.mangaName);
    }

    public boolean saveChapter(String chapterNumber, Map<String, String> chapterContent,
            Boolean cbz) {
        log.info("Downloading chapter {}", chapterNumber);
        String fileLocation = StringUtils.makePathconcat(mangaDirectory.getPath(), chapterNumber);
        File chapter = new File(fileLocation);
        
        try {
            if (chapter.exists()) {
            	log.warn("File already exist : {}, Manga Downloader will deleted it !", fileLocation);
                chapter.delete();
            }
            for (Iterator<String> it = chapterContent.keySet().iterator(); it.hasNext();) {
                String imageNumber = it.next();
                log.debug("Downloading image {}", imageNumber);
                String imagePath = StringUtils.makeFileName(chapter, imageNumber);
                FileUtils.copyURLToFile(new URL(chapterContent.get(imageNumber)), new File(imagePath),
                        TIME_OUT_IN_MILLIS * 2, TIME_OUT_IN_MILLIS * 2);
            }
            if (cbz) {
                log.debug("Czb Compression in progress");
                try {
                    chapter = compressCbz(chapter);
                } catch (Exception e) {
                    log.error("An error occured with your fucking cbz compression", e);
                    return false;
                }
               
            }
            log.info("Chapter {} has been successfully downloaded", chapterNumber);
        } catch (MalformedURLException e) {
            log.error("Error on the url, {}", e);
            return false;
        } catch (IOException ioe) {
            chapter.delete();
            log.error("Error during download : {}", ioe);
            return false;
        }

        return chapter.exists();
    }

    private File compressCbz(File chapter) throws Exception {
        try {
            String cbzName = StringUtils.makeCbzName(chapter);
            FileOutputStream fos = new FileOutputStream(cbzName);
            ZipOutputStream zos = new ZipOutputStream(fos);
            File[] listOfFiles = chapter.listFiles();
            for (int i = 0; i < listOfFiles.length; i++) {
                String temp = listOfFiles[i].getPath();
                log.debug("File Added : {}", temp);
                ZipEntry ze = new ZipEntry(temp);
                zos.putNextEntry(ze);

                FileInputStream in = new FileInputStream(temp);

                int len;
                while ((len = in.read(buffer)) > 0) {
                    zos.write(buffer, 0, len);
                }

                in.close();
            }

            zos.closeEntry();

            // close the zip folder stream
            zos.close();

            log.debug("Chapter : {} has been zip ", chapter.getName());
            File file = new File(cbzName);
            
            FileUtils.deleteDirectory(chapter);
            // TODO remove stupid if
            if (!file.exists()) {
                throw new Exception("Wtf happend nigga !");
            }          
            return file;

        } catch (IOException ex) {
            throw ex;
        }
    }
    
    public File getMangaDirectory(){
    	return mangaDirectory;
    }

}