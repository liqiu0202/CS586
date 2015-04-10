package com.liqiu;

import java.util.ArrayList;

public class Actor extends Thing{
	private String movieName;

	public String getMovieName() {
		return movieName;
	}

	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}

	@Override
	public ArrayList<String> nameToURLs(String name) {
		// TODO Auto-generated method stub
		return null;
	}
}
