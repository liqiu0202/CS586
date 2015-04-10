package com.liqiu;

import java.util.ArrayList;

public class Movie extends Thing{
	private String director;
	private String starring;
	private String language;
	public String getDirector() {
		return director;
	}
	public void setDirector(String director) {
		this.director = director;
	}
	public String getStarring() {
		return starring;
	}
	public void setStarring(String starring) {
		this.starring = starring;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	@Override
	public ArrayList<String> nameToURLs(String name) {
		// TODO Auto-generated method stub
		return null;
	}
}
