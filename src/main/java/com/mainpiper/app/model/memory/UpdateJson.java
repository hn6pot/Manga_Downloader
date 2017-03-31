package com.mainpiper.app.model.memory;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

public class UpdateJson {
	private TreeMap<String, List<UpdateChapter>> update;
	
	public UpdateJson(){
		this.update = new TreeMap<>();
	}
	
	public void UpdateData(List<UpdateChapter> updateChapter){
		String date = getDate();
		this.update.put(date, updateChapter);
	}
	
	private String getDate(){
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date date = new Date();
		return dateFormat.format(date);
	}


}
