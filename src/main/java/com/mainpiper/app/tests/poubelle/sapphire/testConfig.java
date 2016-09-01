package com.mainpiper.app.tests.poubelle.sapphire;

import java.io.File;
import java.nio.charset.Charset;

import org.apache.commons.io.FileUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mainpiper.app.args.Config;

public class testConfig {
    public static void main(String[] args) {
        File t = new File("Source/config.txt");

        Gson GSON = new GsonBuilder().setPrettyPrinting().create();
        Config params = new Config();

        try {
            FileUtils.writeStringToFile(t, GSON.toJson(params), Charset.forName("UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        // if (t.exists()) {
        // String jsonContent = new String();
        // try {
        // jsonContent = FileUtils.readFileToString(t, Charset.forName("UTF-8"));
        // } catch (IOException ex) {
        // ex.printStackTrace();
        // } catch (Exception e) {
        // e.printStackTrace();
        // }
        // Toto test = GSON.fromJson(jsonContent, Toto.class);
        // System.out.println(test.age);
        // System.out.println(test.getName());
        // //
        // } else {
        // System.out.println("Error no file founded");
        // }
    }
}
