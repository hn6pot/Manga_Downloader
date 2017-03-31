package com.mainpiper.app.memory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mainpiper.app.args.Config;
import com.mainpiper.app.display.Display;
import com.mainpiper.app.enums.MangaWebsite;
import com.mainpiper.app.exceptions.TerminateBatchException;
import com.mainpiper.app.model.Chapter;
import com.mainpiper.app.model.memory.MemoryJson;
import com.mainpiper.app.model.memory.MemoryManga;
import com.mainpiper.app.model.memory.UpdateChapter;
import com.mainpiper.app.model.memory.UpdateJson;
import com.mainpiper.app.util.StringUtils;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class OmegaMemoryManager {

	 private final static String DEFAULT_CHARSET = "UTF-8";
	 
	 private final Gson GSON;
	 private final String defaultOmegaDirectory;
	 private final String defaultDownloadDirectory;
	 private final File updateJson;
	 private final File memoryJson;
	 
	 private List<UpdateChapter> updateList;
	 private List<MemoryManga> memoryList;
	 
	 private final UpdateJson updateJsonContent;
	 private  MemoryJson memoryJsonContent;
	 
	 public OmegaMemoryManager(String defaultOmegaDirectory, String defaultDownloadDirectory, String updateJson, String memoryJson){
		 GSON = new GsonBuilder().setPrettyPrinting().create();
		 this.defaultOmegaDirectory = defaultOmegaDirectory;
		 this.defaultDownloadDirectory = defaultDownloadDirectory;
		 this.updateJson = new File(StringUtils.getPath(updateJson, defaultOmegaDirectory));
		 this.memoryJson = new File(StringUtils.getPath(memoryJson, defaultOmegaDirectory));
		 
		 this.updateList = new ArrayList<>();
		 this.memoryList = new ArrayList<>();
		 
		 if(this.updateJson.exists()){
			 this.updateJsonContent = getUpdateFromJson();
		 }
		 else{
			 log.info("There is no updateJson File here : {}, a new one will be generate", this.updateJson.getPath());
		 this.updateJsonContent = new UpdateJson();
		 }
	 }
	 
	 public void feedOmegaMemory(String mangaName, MangaWebsite webSite, List<Chapter> chapters){
		 for(Iterator<Chapter> it = chapters.iterator(); it.hasNext();){
			 UpdateChapter currentUpdateChapter = new UpdateChapter(mangaName, webSite, it.next());
		 	 this.updateList.add(currentUpdateChapter);
		 }
		 this.memoryList.add(getMemoryFromDirectory(mangaName, webSite, chapters));
	 }
	 
	 public void feedOmegaWithFile(File mangaJsonFile, Config conf){
		 JsonManager jsonManager = new JsonManager(mangaJsonFile, conf.getDefaultDownloadDirectory(), conf.getDefaultMemoryDirectory(),
		        		conf.getCheckDirectory());

		 List<Chapter> empty = new ArrayList<>();
		 this.memoryList.add(getMemoryFromDirectory(jsonManager.getManga().getName(), jsonManager.getManga().getSource(), empty));
	 }
	 
	 

	 public void writeJsonFiles() {
	        log.debug("Omega Memory update in progress");
	        this.memoryJsonContent = new MemoryJson(memoryList);
	        this.updateJsonContent.UpdateData(updateList);
	        try {
	            FileUtils.writeStringToFile(updateJson, GSON.toJson(updateJsonContent), Charset.forName(DEFAULT_CHARSET));
	            FileUtils.writeStringToFile(memoryJson, GSON.toJson(memoryJsonContent), Charset.forName(DEFAULT_CHARSET));
	        } catch (IOException ex) {
	            log.error("Unable to create or update the json file.\n, ", ex);
	            throw new TerminateBatchException(TerminateBatchException.EXIT_CODE_ERROR_DURING_JSON_UPDATE,
	                    "Unable to create or update the json file");
	        } catch (Exception e) {
	            log.debug("Unexpected Error occured during the Json file Update: ", e);
	            throw new TerminateBatchException(TerminateBatchException.EXIT_CODE_UNKNOWN,
	                    "Unexpected Error occured during the Json file Update", e);
	        }
	        log.info("Manga update ended without issues, json file has been overwritted");
	        Display.displayInfo("Memory has been updated properly !");
	    }
	 
	
	 private UpdateJson getUpdateFromJson() {
	        String jsonContent = new String();
	        try {
	            jsonContent = FileUtils.readFileToString(this.updateJson, Charset.forName(DEFAULT_CHARSET));
	        } catch (IOException ex) {
	            log.debug("Cannot read the Json file content", ex);
	            throw new TerminateBatchException(TerminateBatchException.EXIT_CODE_NO_SOURCE_PROVIDED,
	                    "Cannot read the Json file content");
	        } catch (Exception e) {
	            log.error("Unexpected Error occured : ", e);
	            throw new TerminateBatchException(TerminateBatchException.EXIT_CODE_UNKNOWN, e);
	        }
	        return GSON.fromJson(jsonContent, UpdateJson.class);
	    }
	 
	  private MemoryManga getMemoryFromDirectory(String mangaName, MangaWebsite webSite, List<Chapter> newChaters) {
	        log.trace("Update Json From Directory in progress");
	        String path = StringUtils.getDefaultPath(mangaName, defaultDownloadDirectory);
	        File defaultDirectory = new File(path);
	       
	        Float fileSize = (float) (FileUtils.sizeOfDirectory(defaultDirectory)/1000000);
	        MemoryManga memoryManga = new MemoryManga(mangaName, webSite, JsonManager.checkDirectory(defaultDirectory), newChaters, fileSize);
	        return memoryManga;
	    }
}
