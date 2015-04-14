

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
	private String baseOnBookLink;
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
		String queryString = "PREFIX Movie: <http://dbpedia.org/ontology/Film>\n"
				+ "PREFIX Book: <http://dbpedia.org/ontology/Book>\n"
				+ "PREFIX wikiLink: <http://www.w3.org/ns/prov#wasDerivedFrom>\n"
				+ "PREFIX name: <http://xmlns.com/foaf/0.1/name>\n"
				+ "PREFIX author:<http://dbpedia.org/ontology/writer>\n"
				+ "PREFIX language: <http://dbpedia.org/property/language>\n"
				+ "PREFIX director: <http://dbpedia.org/property/director>\n"
				+ "PREFIX starring: <http://dbpedia.org/property/starring>\n";
    	String condition = "";
    	String name = request.getParameter("name");
    	String author = request.getParameter("writer");
    	String language = request.getParameter("language");
    	String starring = request.getParameter("starring");
    	String director = request.getParameter("director");
    	String basedOnName = request.getParameter("baseOn");
    	if(name != null )condition += "\nFILTER regex( str(?name), '" + name + "', 'i' ).";  
    	
    	if( author != null )	condition += "\n ?author name: ?authorName."
    			+ "\nFILTER regex( str(?authorName), '" + author + "', 'i' )."; 
    	
    	if( starring != null ) condition += "\n ?starring name: ?starringName."
    			+ "\nFILTER regex( str(?starringName), '" + starring + "', 'i' )."; 
    	if( director != null ) condition += "\n ?director name: ?directorName."
    			+ "\nFILTER regex( str(?directorName), '" + director + "', 'i' )."; 
    	
    	if( language != null )condition += "\nFILTER regex( str(?language), '" + language + "', 'i' ).";

    	if( basedOnName != null ) condition += "?Book a Book:; name: ?name."
    			+ "\nFILTER regex( str(?name), '" + basedOnName + "', 'i' ).";
    	queryString = queryString
    			+ "SELECT DISTINCT ?wikiLink ?name WHERE{ ?Movie a Movie:; wikiLink: ?wikiLink;"
    			+ " name: ?name; author: ?author; starring: ?starring;"
    			+ " director: ?director; language: ?language."
    			+ "FILTER isIRI(?author)."
    			+ "FILTER isIRI(?starring)."
    			+ "FILTER isIRI(?director)."
    			+ condition + "} LIMIT 100";
    	
    	System.out.println( queryString );
    	ArrayList<Thing> res = new SPARQLHelper().processQuery(queryString);
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
