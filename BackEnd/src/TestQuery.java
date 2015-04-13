

import java.io.IOException;
import java.util.ArrayList;

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
		String queryString = "PREFIX Book: <http://dbpedia.org/ontology/Book>\n"
				+ "PREFIX wikiLink: <http://www.w3.org/ns/prov#wasDerivedFrom>\n"
				+ "PREFIX name: <http://xmlns.com/foaf/0.1/name>\n"
				+ "PREFIX author: <http://dbpedia.org/ontology/author>\n"
				+ "PREFIX language: <http://dbpedia.org/property/language>\n"
    			+ "SELECT DISTINCT * WHERE{ ?Book a Book:; name: ?name.} LIMIT 10";
		ArrayList<Thing> res = new SPARQLHelper().processQuery(queryString);
		for(Thing thing: res){
			System.out.println(thing.getName()  + " " + thing.getWikiLink() );
		}
		
		
//		System.out.println( res.size() );
	}
}
