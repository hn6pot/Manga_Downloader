package com.mainpiper.app.model.memory;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.mainpiper.app.enums.MangaWebsite;
import com.mainpiper.app.model.Chapter;
import com.mainpiper.app.model.Manga;
import com.mainpiper.app.util.StringUtils;

public class MemoryManga extends Manga{
		
		private final Boolean newChapter;
		private final String id;
		private final String size;
		private Set<Chapter> newChapters = new TreeSet<Chapter>();
		
		public MemoryManga (String mangaName, MangaWebsite webSite, List<Chapter> chaptersDowloaded, List<Chapter> newChapter, Float size){
			super(mangaName, webSite);
			id = getId(mangaName);
			this.size = size.toString();
			chaptersDowloaded.removeAll(newChapter);
			this.chapters.addAll(chaptersDowloaded);
			if(newChapter.isEmpty()){
				this.newChapter = false;	
			}
			else{
				this.newChapter = true;
				this.newChapters.addAll(newChapter);
				
				
			}
			
			
		}
		
		 protected String getId(String mangaName) {
		        /**
		         * We need this king of name : one-piece
		         */
		        return StringUtils.deAccent(mangaName).replaceAll(" ", "-").toLowerCase();
		    }


}
