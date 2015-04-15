

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
    	String queryString = SPARQLHelper.prefixString;
    	String condition = "";
    	String name = request.getParameter("name");
    	String author = request.getParameter("author");
    	String language = request.getParameter("language");
    	if(name != null )condition += "\nFILTER regex( str(?name), '" + name + "', 'i' )."; 
    	
    	if( author != null )	condition += "\n ?author name: ?authorName."
    			+ "\nFILTER regex( str(?authorName), '" + author + "', 'i' )."; 
    	
    	if( language != null )condition += "\nFILTER regex( str(?language), '" + language + "', 'i' ).";

    	
    	queryString = queryString 
    			+ "SELECT DISTINCT ?wikiLink ?name ?description WHERE{ ?Book a Book:; wikiLink: ?wikiLink; description: ?description;"
    			+ " name: ?name; author: ?author; language: ?language. FILTER isIRI(?author)."
    			+ condition + "} LIMIT 100";
    	
    	System.out.println( queryString );
    	ArrayList<Thing> res = new SPARQLHelper().processQuery(queryString);
    	ObjectMapper mapper = new ObjectMapper();
		String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(res);
		response.getWriter().println( jsonString );
    }

}
