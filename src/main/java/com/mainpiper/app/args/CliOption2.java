package com.mainpiper.app.args;

import com.beust.jcommander.Parameter;
import com.mainpiper.app.enums.MangaWebsite;

import lombok.Getter;

@Getter
public class CliOption2 {

    private static final String defaultValue = "Default";

    private static final String sourceDescription =
            "Specify the website you want to download the mangas from\n Possible sources : " + MangaWebsite.listValues()
                    + "\n";

    @Parameter(names = { "-h", "-help", "--help" }, description = "Display help content", help = true)
    Boolean help = false;

    @Parameter(names = { "-ws", "-source", "--web-sources" },
            description = "Specify the website you want to download the mangas from")
    String webSources = "default";

    @Parameter(names = { "-nm", "-manga", "--manga-name" }, description = "Give us the manga name", required = true)
    String mangaName;

    @Parameter(names = { "-chn", "-chapter", "--chapter-number" },
            description = "Specify the chapter you want to download")
    String chapterNumber = defaultValue;

}
