package com.mainpiper.app.display;

import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Display {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static void displaySet(Set<String> chapters) {
        for (Iterator<String> it = chapters.iterator(); it.hasNext();) {
            System.out.print(ANSI_BLUE + it.next() + " - " + ANSI_RESET);
        }
    }

    public static boolean displayChapterAvailable(String mangaName, Set<String> chapters) {
        Boolean result = null;
        log.trace("Display Chapters Available");
        Display.displayTitle("Chapters Available");
        Display.displaySet(chapters);
        Scanner input = new Scanner(System.in);
        while (true) {
            System.out.println(ANSI_GREEN + "Do you still want to update " + mangaName + " ?  (y/n)");
            String answer = input.next();
            answer = answer.toLowerCase();
            if (answer.equals("yes") || answer.equals("y")) {
                result = true;
                break;
            } else if (answer.equals("no") || answer.equals("n")) {
                result = false;
                break;
            }
        }
        input.close();
        return result;
    }

    public static void displayTitle(String msg) {
        System.out.println(
                ANSI_CYAN + "--------------------------- " + msg + " ----------------------------" + ANSI_RESET);
    }

    public static void displaySTitle(String msg) {
        System.out.println(ANSI_BLUE + msg + ANSI_RESET);
    }

    public static void displayError(String msg) {
        System.out.println(ANSI_RED + msg + ANSI_RESET);
    }

    public static void displayInfo(String msg) {
        System.out.println(ANSI_GREEN + msg + ANSI_RESET);
    }

    public static void displaySave(String msg) {
        System.out.println(ANSI_CYAN + msg + ANSI_RESET);
    }

    public static void displayWarn(String msg) {
        System.out.println(ANSI_YELLOW + msg + ANSI_RESET);
    }

}
