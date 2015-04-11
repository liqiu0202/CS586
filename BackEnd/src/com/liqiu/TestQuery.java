package com.liqiu;

import java.io.IOException;

public class TestQuery {
	public static void main(String[] args)throws IOException{
		//query books
//		String queryString = "PREFIX onto: <http://www-scf.usc.edu/~liqiu/cs586/BookAndMovie.owl#>\n"
//    			+ "SELECT DISTINCT * WHERE{ ?Book a onto:Book; onto:wikiLink ?wikiLink;"
//    			+ " onto:name ?name; onto:author ?author; onto:language ?language.}";
		
		//query authors
//		String queryString = "PREFIX onto: <http://www-scf.usc.edu/~liqiu/cs586/BookAndMovie.owl#>\n"
//    			+ "SELECT DISTINCT * WHERE{ ?author a onto:Author}";
		
		//query movies
//		String queryString = "PREFIX onto: <http://www-scf.usc.edu/~liqiu/cs586/BookAndMovie.owl#>\n"
//    			+ "SELECT DISTINCT * WHERE{ ?movie a onto:Movie}";
		
		//query actors
//		String queryString = "PREFIX onto: <http://www-scf.usc.edu/~liqiu/cs586/BookAndMovie.owl#>\n"
//    			+ "SELECT DISTINCT * WHERE{ ?actor a onto:Actor}";
		
		//movie based on books
		String queryString = "PREFIX onto: <http://www-scf.usc.edu/~liqiu/cs586/BookAndMovie.owl#>\n"
    			+ "SELECT DISTINCT * WHERE{ ?movie a onto:Movie; onto:name ?name. ?book a onto:Book; onto:name ?name.}";
		SPARQLHelper.processQuery(queryString);
		
		
//		System.out.println( res.size() );
	}
}
