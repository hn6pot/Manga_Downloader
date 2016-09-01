package com.mainpiper.app.model;

public class Chapter implements Comparable<Chapter> {

	private String number;

	private int pagesCount;

	private String associatedVolume;

	public Chapter(String number, String associatedVolume) {
		this.number = number;
		this.associatedVolume = associatedVolume;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public int getPagesCount() {
		return pagesCount;
	}

	public void setPagesCount(int pagesCount) {
		this.pagesCount = pagesCount;
	}

	public String getAssociatedVolume() {
		return associatedVolume;
	}

	public void setAssociatedVolume(String associatedVolume) {
		this.associatedVolume = associatedVolume;
	}

	@Override
	public int compareTo(Chapter o) {
		// TODO implement correct 'compareTo' method
		return 0;
	}
}
