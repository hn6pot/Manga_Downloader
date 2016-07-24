package com.christiankula.vulpes.utils;

import com.christiankula.vulpes.main.Main;;

public class Help {

    private Help() {
    }


    public static void printHelp() {
        StringBuilder help = new StringBuilder();
        help.append(System.getProperty("line.separator"));
        help.append("Vulpes v" + Main.APP_VERSION + " by C.Kula");
        help.append(System.getProperty("line.separator"));

        help.append("This script downloads mangas from mangafox.me. You must have the JRE 1.6 (or higher) installed.");
        help.append(System.getProperty("line.separator"));

        help.append(System.getProperty("line.separator"));
        help.append("SYNTAX");
        help.append(System.getProperty("line.separator"));
        help.append(
                "java -jar mangafox_downloader.jar [REQUIRED] <manga name> [OPTIONAL] -v <volume number> -c <chapitre number>");
        help.append(System.getProperty("line.separator"));

        help.append(System.getProperty("line.separator"));
        help.append(System.getProperty("line.separator"));

        help.append("OPTIONS");
        help.append(System.getProperty("line.separator"));
        help.append(System.getProperty("line.separator"));

        help.append(
                "[REQUIRED] <manga name> \r\nRefers to the name of the manga you want to dowload as displayed on mangafox.me");
        help.append(System.getProperty("line.separator"));

        help.append("Case insensitive and can contain space but not special characters");
        help.append(System.getProperty("line.separator"));

        help.append(
                "Tip : To be sure you got it right, visit the desired manga page on mangafox.me. The name of the manga appears in the URL in this form : www.mangafox.me/manga/<manga name>/");
        help.append(System.getProperty("line.separator"));

        help.append(System.getProperty("line.separator"));

        help.append("EXAMPLES");
        help.append(System.getProperty("line.separator"));
        help.append("java -jar mangafox_downloader.jar naruto");
        help.append(System.getProperty("line.separator"));
        help.append("java -jar mangafox_downloader.jar \"Kangoku Gakuen\" - Kangoku Gakuen refers to Prison School");
        help.append(System.getProperty("line.separator"));

        help.append(System.getProperty("line.separator"));
        help.append(System.getProperty("line.separator"));

        help.append(
                "[OPTIONAL] -v <volume number> \r\nUse this option to download only the specified volume of a manga (= every chapter of the specified volume)");

        help.append(System.getProperty("line.separator"));
        help.append("The volume number is as displayed on the manga page. Ranging from 0 to n");
        help.append("The volume number can also be \"TBD\" (refering to \"Volume TBD\" - To be determined)");
        help.append(System.getProperty("line.separator"));
        help.append("The volume number can also be \"NA\" (refering to \"Volume Not Available\")");
        help.append(System.getProperty("line.separator"));
        help.append(System.getProperty("line.separator"));
        help.append("EXAMPLES");
        help.append(System.getProperty("line.separator"));
        help.append("java -jar mangafox_downloader.jar naruto -v NA");
        help.append(System.getProperty("line.separator"));
        help.append("java -jar mangafox_downloader.jar \"Kangoku Gakuen\" -v 18");

        help.append(System.getProperty("line.separator"));
        help.append(System.getProperty("line.separator"));

        help.append(
                "[OPTIONAL] -v <volume number> -c <chapter number>\r\nUse this option to download only the specified chapter of the specified volume of a manga");
        help.append(System.getProperty("line.separator"));
        help.append(
                "When using -c, -v is mandatory as some mangas' chapters numbers aren't unique (i.e. the Dective Conan manga has multiple \"Chapter 1\" who knows why)");
        help.append(System.getProperty("line.separator"));
        help.append("Be sure the specified chapter is contained within the specified volume");
        help.append(System.getProperty("line.separator"));
        help.append("The chapter number is as displayed on the manga page. Ranging from 0 to n");
        help.append(System.getProperty("line.separator"));
        help.append(System.getProperty("line.separator"));
        help.append("EXAMPLES");
        help.append(System.getProperty("line.separator"));
        help.append(System.getProperty("line.separator"));
        help.append("java -jar mangafox_downloader.jar naruto -v 60 -c 575");
        help.append(System.getProperty("line.separator"));
        help.append("java -jar mangafox_downloader.jar \"Kangoku Gakuen\" -v TBD -c 197");

        help.append(System.getProperty("line.separator"));
        help.append(System.getProperty("line.separator"));
        help.append(System.getProperty("line.separator"));

        help.append("[OTHER] -version");
        help.append(System.getProperty("line.separator"));
        help.append("Display the script version");

        System.out.println(help);
    }
}
