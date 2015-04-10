package com.liqiu;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hp.hpl.jena.query.QuerySolution;


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
	@Override
	public ArrayList<String> nameToURLs(String name) {
		ArrayList<QuerySolution> solns = SPARQLHelper.processQuery("PREFIX onto: <http://www-scf.usc.edu/~liqiu/cs586/BookAndMovie.owl#>\n"
    			+ "SELECT DISTINCT * WHERE{ ?Book a onto:Book; onto:name "
    			+ name + "}");
		ArrayList<String> bookList = new ArrayList<String>();
		for(QuerySolution soln: solns ){
			bookList.add( soln.getResource("Book").getURI() );
		}
		return bookList;
	}
	
	public static void processQuery(HttpServletRequest request, HttpServletResponse response)throws IOException, ServletException{
    	
    	String condition = "";
    	String name = request.getParameter("name");
    	String author = request.getParameter("author");
    	String language = request.getParameter("language");
    	if(name != null )condition += "\nFILTER ( str(?name) =  \"" + name + "\")."; 
    	
    	if( author != null )	condition += "\n ?author onto:name ?authorName."
    			+ " \nFILTER( str(?authorName) = \"" + author + "\").";
//    	else condition += "; onto:author ?author";
//    	    	
    	if( language != null )condition += "\nFILTER ( str(?language) =  \"" + language + "\").";

    	
    	String queryString = "PREFIX onto: <http://www-scf.usc.edu/~liqiu/cs586/BookAndMovie.owl#>\n"
    			+ "SELECT DISTINCT * WHERE{ ?Book a onto:Book; onto:wikiLink ?wikiLink; onto:abstract ?abstract;"
    			+ " onto:name ?name; onto:author ?author; onto:language ?language."
    			+ condition + "}";
    	
    	System.out.println( queryString );
    	ArrayList<QuerySolution> solns = SPARQLHelper.processQuery(queryString);
    	ArrayList<Book> bookList = new ArrayList<Book>();
    	for(QuerySolution soln :solns){
    		Book book = new Book();
    		if( soln.getLiteral("name") != null )	book.setName(  soln.getLiteral("name").toString() );
    		else book.setName(name);
    		
    		if( soln.getResource("author") != null) book.setAuthor( soln.getResource("author").getURI());
    		else book.setName(author);
    		
    		if( soln.getLiteral("language") != null) book.setLanguage( soln.getLiteral("language").toString() );
    		else book.setLanguage(language);
    		
    		if( soln.getLiteral("abstract") != null)
    			book.setDescription( soln.getLiteral("abstract").toString() );
    		if( soln.getLiteral("wikiLink") != null)
    			book.setWikiLink( soln.getLiteral("wikiLink").toString() );
    		
    		bookList.add( book );
    			
    	}
//    	System.out.println(bookList.size() );
    	ObjectMapper mapper = new ObjectMapper();
		String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(bookList);
//		response.getWriter().println( jsonString );
		System.out.println( jsonString );
    }

}
