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

import com.mainpiper.app.display.Display;
import com.mainpiper.app.exceptions.TerminateBatchException;
import com.mainpiper.app.util.StringUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Downloader {
    private final static int TIME_OUT_IN_MILLIS = 5000;

    private static final byte[] buffer = new byte[1024];

    private final String mangaName;
    private final File mangaDirectory;

    public Downloader(String mangaName, String fileDirectory) {
        this.mangaName = mangaName;
        mangaDirectory = new File(StringUtils.getDefaultPath(mangaName, fileDirectory));
        log.trace("Downloader for : {} initialization ended properly", this.mangaName);
    }

    public boolean saveChapter(String chapterNumber, Map<String, String> chapterContent, Boolean cbz) {
        String info = "Chapter " + chapterNumber + ", Downloading in progress ...";
        log.info(info);
        Display.displaySTitle(info);

        String fileLocation = StringUtils.makePathconcat(mangaDirectory.getPath(), chapterNumber);
        File chapter = new File(fileLocation);

        try {
            if (chapter.exists()) {
                log.warn("File already exist : {}, Manga Downloader will deleted it !", fileLocation);
            }
            for (Iterator<String> it = chapterContent.keySet().iterator(); it.hasNext();) {
                String imageNumber = it.next();
                log.trace("Downloading image {}, URL : {}", imageNumber, chapterContent.get(imageNumber));
                String imagePath = StringUtils.makeFileName(chapter, imageNumber);
                FileUtils.copyURLToFile(new URL(chapterContent.get(imageNumber)), new File(imagePath),
                        TIME_OUT_IN_MILLIS * 2, TIME_OUT_IN_MILLIS * 2);
            }
            if (cbz) {
                log.debug("Czb Compression in progress");
                try {
                    chapter = compressCbz(chapter);
                } catch (Exception e) {
                    log.debug("An error occured with your fucking cbz compression", e);
                    throw new TerminateBatchException(TerminateBatchException.EXIT_CODE_COMPRESSION_FAIL,
                            "An error occured during the compression", e);
                }

            }
            info = "Chapter " + chapterNumber + " has been successfully downloaded";
            log.info(info);
            Display.displayInfo(info);
        } catch (MalformedURLException e) {
            log.debug("Error on the url, {}", e);
            throw new TerminateBatchException(TerminateBatchException.EXIT_CODE_URL_MALFORMED,
                    "There is an error with the Url Provided");
        } catch (IOException ioe) {
            chapter.delete();
            log.debug("Error during the download : {}", ioe);
            throw new TerminateBatchException(TerminateBatchException.EXIT_CODE_DOWNLOAD_ERROR,
                    "An Error occured during the download");
        } catch (Exception e) {
            throw new TerminateBatchException(TerminateBatchException.EXIT_CODE_UNKNOWN, e);
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
                log.trace("File Added : {}", temp);
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

    public File getMangaDirectory() {
        return mangaDirectory;
    }

}
