package com.liqiu;

import java.util.ArrayList;

import com.hp.hpl.jena.query.QuerySolution;

public class Author extends Thing{
	private String movieName;
	private String bookName;
	public String getMovieName() {
		return movieName;
	}
	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}
	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	@Override
	public ArrayList<String> nameToURLs(String name) {
		ArrayList<QuerySolution> solns = SPARQLHelper.processQuery("PREFIX onto: <http://www-scf.usc.edu/~liqiu/cs586/BookAndMovie.owl#>\n"
    			+ "SELECT DISTINCT * WHERE{ ?Author a onto:Author; onto:name "
    			+ name + "}");
		ArrayList<String> authorList = new ArrayList<String>();
		for(QuerySolution soln: solns ){
			authorList.add( soln.getResource("Book").getURI() );
		}
		return authorList;
	}
}
