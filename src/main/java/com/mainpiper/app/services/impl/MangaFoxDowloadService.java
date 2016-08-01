package com.mainpiper.app.services.impl;


import com.mainpiper.app.services.DownloadService;

/**
 * @author Christian Kula
 *         22/07/2016
 */
public class MangaFoxDowloadService implements DownloadService {
	public MangaFoxDowloadService(String mangaName) {
	}


	//check list manga
	//ok prend manga truk /chap/volume 
	//ok down -> download cette liste
	//ok json update ma maman 
	//end of story
	
	public void downloadManga() {
		// TODO Auto-generated method stub
		
		
	}

	public void downloadVolume(String volumeNumber) {
		// TODO Auto-generated method stub
		
	}

	public void downloadChapter(String volumeNumber, String chapterNumber) {
		// TODO Auto-generated method stub
		
	}
   

//    public MangaFoxDowloadService(String mangaName) {
//        this.manga = new MangaFox(mangaName);
//    }
//
//    public void downloadManga() {
//        System.out.println("Download Mangafox " + manga.getName());
//    }
//
//    public void downloadVolume(String volumeNumber) {
//        System.out.println("Download Mangafox " + manga.getName() + " Volume " + volumeNumber);
//
//    }
//
//    public void downloadChapter(String volumeNumber, String chapterNumber) {
//        System.out.println("Download Mangafox " + manga.getName() + " Volume " + volumeNumber + " Chapter " + chapterNumber);
//    }
}
