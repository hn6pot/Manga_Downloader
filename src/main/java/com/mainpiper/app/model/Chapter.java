package com.mainpiper.app.model;

/**
 * @author Christian Kula
 * @date 22/07/2016.
 */
public class Chapter implements Comparable<Chapter> {

	private String number;

	private int pagesCount;

	private String associatedVolume;

	public Chapter(String number, String associatedVolume) {
		this.number = number;
		this.associatedVolume = associatedVolume;
	}

	@Override
	public int compareTo(Chapter o) {
		return 0;
	}
}
