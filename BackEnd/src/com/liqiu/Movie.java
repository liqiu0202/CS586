package com.liqiu;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Movie extends Thing{
	private String director;
	private String starring;
	private String language;
	private String author;
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
	
	public static void processQuery(HttpServletRequest request, HttpServletResponse response)throws IOException, ServletException{
    	
    	String condition = "";
    	String name = request.getParameter("name");
    	String author = request.getParameter("writer");
    	String language = request.getParameter("language");
    	String starring = request.getParameter("starring");
    	String director = request.getParameter("director");
    	if(name != null )condition += "\nFILTER ( str(?name) =  \"" + name + "\")."; 
    	
    	if( author != null )	condition += "\n ?author onto:name ?authorName."
    			+ " \nFILTER( str(?authorName) = \"" + author + "\").";
    	if( starring != null ) condition += "\n ?starring onto:name ?starringName."
    			+ " \nFILTER( str(?starringName) = \"" + starring + "\").";
    	if( director != null ) condition += "\n ?director onto:name ?directorName."
    			+ " \nFILTER( str(?directorName) = \"" + director + "\").";
    	
    	if( language != null )condition += "\nFILTER ( str(?language) =  \"" + language + "\").";

    	
    	String queryString = "PREFIX onto: <http://www-scf.usc.edu/~liqiu/cs586/BookAndMovie.owl#>\n"
    			+ "SELECT DISTINCT ?Movie ?wikiLink ?name WHERE{ ?Movie a onto:Movie; onto:wikiLink ?wikiLink;"
    			+ " onto:name ?name; onto:author ?author; onto:starring ?starring;"
    			+ " onto:director ?director; onto:language ?language."
    			+ condition + "}";
    	
    	System.out.println( queryString );
    	ArrayList<Thing> res = SPARQLHelper.processQuery(queryString);
    	ObjectMapper mapper = new ObjectMapper();
		String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(res);
		response.getWriter().println( jsonString );
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}

}
