package com.mainpiper.app.services;

import java.io.File;

import com.mainpiper.app.args.Config;
import com.mainpiper.app.display.Display;
import com.mainpiper.app.exceptions.TerminateScriptProperly;
import com.mainpiper.app.memory.JsonManager;
import com.mainpiper.app.memory.OmegaMemoryManager;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ServiceUpdate {

    public static void update(Config conf) {
        File memory = new File(conf.getDefaultMemoryDirectory());
        OmegaMemoryManager omm = new OmegaMemoryManager(conf.getDefaultOmegaDirectory(), conf.getDefaultDownloadDirectory(),
        		conf.getUpdateJson(), conf.getMemoryJson());
        Services service = null;
        for (File f : memory.listFiles()) {
        	
        	 try {
        		service = new Services(f, conf);
                service.downloadManga(false);
                if(conf.getGenerateMemoryFile())
	                omm.feedOmegaMemory(service.getJsonManager().getManga().getName(), service.getJsonManager().getManga().getSource(),
	                		service.getChaptersDownloaded());
            } catch(TerminateScriptProperly ts){
            	omm.feedOmegaWithFile(f, conf);	
            	continue;
            }catch (Exception e) {
                log.debug("An unexpected Error Occured, ", e);
                Display.displayError("An unexpected Error Occured, check logs for futher information");
                continue;
            }
        }
        if(conf.getGenerateMemoryFile())
        	omm.writeJsonFiles();
        Display.displayTitle("Thank you for using a MainPiper&Co Production !");
    }
}
