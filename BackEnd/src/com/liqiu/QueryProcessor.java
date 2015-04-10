package com.liqiu;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

/**
 * Servlet implementation class QueryProcessor
 */
@WebServlet("/QueryProcessor")
public class QueryProcessor extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
    	response.setContentType("text/plain; charset=UTF-8");
    	PrintWriter out = response.getWriter();
    	String target = request.getParameter("target");
    	if( target == null ) return;
    	if( target.equals("Book" ) ){
    		queryOnBook(request, response);
    	}else if(target.equals("Movie") ){
//    		queryOnMovie(request, response);
    	}else if( target.equals("Author") ){
//    		queryOnAuthor(request, response);
    	}else if( target.equals("Actor") ){
//    		queryOnActor(request, response);
    	}else if( target.equals("Director") ){
//    		queryOnDirector(request, response);
    	}else{
    		out.println("Unsupported Query target: " + target);
    	}
    }
    
    private void queryOnBook(HttpServletRequest request, HttpServletResponse response)throws IOException, ServletException{
    	
    	String condition = "";
    	String name = request.getParameter("name");
    	String author = request.getParameter("author");
    	String language = request.getParameter("language");
    	if(name != null )condition += "; onto:name " + name; 
    	else condition += "; onto:name ?name";
    	
    	if( author != null )	condition += "; onto:author " + author;
    	else condition += "; onto:author ?author";
    	    	
    	if( language != null )condition += "; onto:language " + language;
    	else condition += "; onto:language ?language";
    	
    	String queryString = "PREFIX onto: <http://www-scf.usc.edu/~liqiu/cs586/BookAndMovie.owl#>\n"
    			+ "SELECT DISTINCT * WHERE{ ?Book a onto:Book; onto:wikiLink ?wikiLink; onto:abstract ?abstract"
    			+ condition + "}";
    	ArrayList<QuerySolution> solns = processQuery(queryString);
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
    	System.out.println(bookList.size() );
    	ObjectMapper mapper = new ObjectMapper();
		String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(bookList);
		System.out.println( jsonString );
    	
    	
    }
    
    private ArrayList<QuerySolution> processQuery(String queryString){
    	Model model = ModelFactory.createOntologyModel();
		model.read("/Users/liqiu/Documents/CS586/RDFFiles/BookAndMovieWithData.owl", "RDF/XML") ;

		Query query = QueryFactory.create(queryString);
		System.out.println( queryString + "-------");
		QueryExecution qexec = QueryExecutionFactory.create(query, model);
		//Execute.
		ArrayList<QuerySolution> res = new ArrayList<QuerySolution>();
		Iterator<QuerySolution> rs = qexec.execSelect();
		while( rs.hasNext() ){
			QuerySolution soln = rs.next();
			res.add( soln );
		}
		System.out.println("execution is good");
		return res;
    }
    
 

}
