package com.mainpiper.app.args;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.ParseException;
import org.apache.commons.io.IOUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mainpiper.app.display.Display;
import com.mainpiper.app.exceptions.TerminateBatchException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Reader {
    public static final String configPath = "config";

    private InputStream streamConfig;

    public Reader() throws IOException {
        try {
            streamConfig = new FileInputStream(configPath);
        } catch (FileNotFoundException fe) {
            String warning =
                    "No Config file found (" + configPath + "), The script will use a default Config file !";
            Display.displayWarn(warning);
            log.warn(warning, fe);
            streamConfig = ClassLoader.getSystemResource("config").openStream();
        } catch (Exception e) {
            throw new TerminateBatchException(TerminateBatchException.EXIT_CODE_UNKNOWN, e);
        }
    }

    public Config generateConfig(CommandLine commandLine) throws ParseException {
        Gson GSON = new GsonBuilder().setPrettyPrinting().create();
        String jsonContent = new String();
        try {
            jsonContent = IOUtils.toString(streamConfig, Charset.forName("UTF-8"));
        } catch (IOException ex) {
            throw new TerminateBatchException(TerminateBatchException.EXIT_CODE_ERROR_CONFIG_FILE_CORRUPT,
                    "There is an error with the config file provided", ex);
        } catch (Exception e) {
            throw new TerminateBatchException(TerminateBatchException.EXIT_CODE_UNKNOWN, e);
        }
        Config conf = GSON.fromJson(jsonContent, Config.class);
        conf.extendConfig(commandLine);
        conf.displayDebug();
        return conf;
    }
}
