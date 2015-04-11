package com.liqiu;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;


public class Book extends Thing{
	private String author;
	private String language;
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
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
    	String author = request.getParameter("author");
    	String language = request.getParameter("language");
    	if(name != null )condition += "\nFILTER ( str(?name) =  \"" + name + "\")."; 
    	
    	if( author != null )	condition += "\n ?author onto:name ?authorName."
    			+ " \nFILTER( str(?authorName) = \"" + author + "\").";
    	
    	if( language != null )condition += "\nFILTER ( str(?language) =  \"" + language + "\").";

    	
    	String queryString = "PREFIX onto: <http://www-scf.usc.edu/~liqiu/cs586/BookAndMovie.owl#>\n"
    			+ "SELECT DISTINCT ?Book ?wikiLink ?name WHERE{ ?Book a onto:Book; onto:wikiLink ?wikiLink;"
    			+ " onto:name ?name; onto:author ?author; onto:language ?language."
    			+ condition + "}";
    	
    	System.out.println( queryString );
    	ArrayList<Thing> res = SPARQLHelper.processQuery(queryString);
    	ObjectMapper mapper = new ObjectMapper();
		String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(res);
		response.getWriter().println( jsonString );
    }

}
