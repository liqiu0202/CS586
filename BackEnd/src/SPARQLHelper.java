

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.sparql.engine.http.QueryEngineHTTP;

public class SPARQLHelper {
//	private static String[] urlList = {"http://www-scf.usc.edu/~liqiu/cs586/BookLarge.ttl", "http://www-scf.usc.edu/~liqiu/cs586/MovieLarge.ttl"};
//	private static Dataset dataset = DatasetFactory.create(Arrays.asList(urlList));
//	public static ArrayList<Thing> processQuery(String queryString)throws IOException{
//		Query query = QueryFactory.create(queryString);
//		QueryExecution qexec = QueryExecutionFactory.create(query, dataset);
//		//Execute.
//		ArrayList<Thing> res = new ArrayList<Thing>();
//		Iterator<QuerySolution> rs = qexec.execSelect();
//		while( rs.hasNext() ){
//			QuerySolution soln = rs.next();
//			Thing thing = new Thing();
//			if( soln.getLiteral("wikiLink") != null ) thing.setWikiLink( soln.getLiteral("wikiLink").toString() );
//			if( soln.getLiteral("name") != null ) thing.setName(soln.getLiteral("name").toString() );
//			res.add( thing );
//		}
//		return res;		
//    }
	public static final String prefixString = "PREFIX Movie: <http://dbpedia.org/ontology/Film>\n"
			+ "PREFIX Book: <http://dbpedia.org/ontology/Book>\n"
			+ "PREFIX wikiLink: <http://www.w3.org/ns/prov#wasDerivedFrom>\n"
			+ "PREFIX name: <http://xmlns.com/foaf/0.1/name>\n"
			+ "PREFIX author: <http://dbpedia.org/ontology/author>\n"
			+ "PREFIX writer:<http://dbpedia.org/ontology/writer>\n"
			+ "PREFIX language: <http://dbpedia.org/property/language>\n"
			+ "PREFIX director: <http://dbpedia.org/property/director>\n"
			+ "PREFIX starring: <http://dbpedia.org/property/starring>\n"
			+ "PREFIX description: <http://dbpedia.org/ontology/abstract>";
	public ArrayList<Thing> processQuery(String queryString) throws IOException{
		Query query = QueryFactory.create(queryString);
		QueryExecution qexec = QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql", query);
		
		//set timeout for DBpedia
		((QueryEngineHTTP) qexec).addParam("timeout", "10000");
		ArrayList<Thing> res = new ArrayList<Thing>();
		
		Iterator<QuerySolution> rs = qexec.execSelect();
		HashSet<String> hashSet = new HashSet<String>();
		while( rs.hasNext() ){
			QuerySolution soln = rs.next();
			Thing thing = new Thing();
			if( soln.getResource("wikiLink") != null ) thing.setWikiLink( soln.getResource("wikiLink").getURI() );
			if( soln.getLiteral("name") != null ) thing.setName(soln.getLiteral("name").getString() );
			if( soln.getLiteral("description") != null ) thing.setDescription(soln.getLiteral("description").getString() );
			if( !hashSet.contains( thing.getWikiLink() ) ){//remove duplicate records
				hashSet.add( thing.getWikiLink());
				res.add( thing );
			}
		}
		ArrayList<Thing> ans = new ArrayList<Thing>();
		for(int i = 0; i < 30 && i < res.size(); ++i ) ans.add( res.get(i) );
		return ans;

	}
}
