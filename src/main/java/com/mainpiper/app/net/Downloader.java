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

    public Downloader(String mangaName) {
        this.mangaName = mangaName;
        log.debug("Downloader for : {} initialization ended properly", this.mangaName);
    }

    private boolean saveChapter(String imageUrl, String chapterNumber, Map<String, String> chapterContent,
            Boolean cbz) {
        log.info("Downloading chapter {}", chapterNumber);
        String fileLocation = StringUtils.makeChapterName(fileDirectory, chapterNumber);
        File chapter = new File(fileLocation);

        try {
            if (chapter.exists()) {
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
                }

            }
        } catch (MalformedURLException e) {
            log.error("Error on the url, {}", e);
        } catch (IOException ioe) {
            chapter.delete();
            log.error("Error during download : {}", ioe);
        }

        return chapter.exists();
    }

    private File compressCbz(File chapter) throws Exception {
        try {
            String zipName = StringUtils.makeZipName(chapter);
            FileOutputStream fos = new FileOutputStream(zipName);
            ZipOutputStream zos = new ZipOutputStream(fos);
            File[] listOfFiles = chapter.listFiles();
            for (int i = 0; i < listOfFiles.length; i++) {
                String temp = listOfFiles[i].getName();
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

            log.debug("Chaper : {} has been zip ", chapter.getName());
            File file = new File(zipName);
            // TODO remove stupid if
            if (!file.exists()) {
                throw new Exception("Wtf append nigga !");
            }
            File cbz = new File(StringUtils.makeCbzName(chapter));
            file.renameTo(cbz);
            return file;

        } catch (IOException ex) {
            throw ex;
        }
    }

}
