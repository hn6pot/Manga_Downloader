package com.mainpiper.app.services;

import java.io.File;

import com.mainpiper.app.args.Config;
import com.mainpiper.app.display.Display;
import com.mainpiper.app.memory.JsonManager;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ServiceUpdate {

    public static void update(Config conf) {
        File memory = new File(JsonManager.defaultMemoryDirectory);

        Services service = null;
        for (File f : memory.listFiles()) {
            service = new Services(f, conf);
            try {
                service.downloadManga();
            } catch (Exception e) {
                log.debug("An unexpected Error Occured, ", e);
                Display.displayError("An unexpected Error Occured, check logs for futher information");
                continue;
            }
        }
    }
}
