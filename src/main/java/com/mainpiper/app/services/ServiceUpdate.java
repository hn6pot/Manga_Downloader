package com.mainpiper.app.services;

import java.io.File;

import com.mainpiper.app.args.Config;
import com.mainpiper.app.memory.JsonManager;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ServiceUpdate {

    public static void update(Config conf) {
        File memory = new File(JsonManager.defaultMemoryDirectory);

        Service service = null;
        for (File f : memory.listFiles()) {
            service = new Service(f, conf);
            try {
                service.downloadManga();
            } catch (Exception e) {
                log.debug("An unexpected Error Occured, ", e);
                continue;
            }
        }
    }
}
