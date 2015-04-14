

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.hp.hpl.jena.query.Dataset;
import com.hp.hpl.jena.query.DatasetFactory;
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
	public ArrayList<Thing> processQuery(String queryString) throws IOException{
		Query query = QueryFactory.create(queryString);
		QueryExecution qexec = QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql", query);
		
		//set timeout for DBpedia
		((QueryEngineHTTP) qexec).addParam("timeout", "10000");
		ArrayList<Thing> res = new ArrayList<Thing>();
		
		Iterator<QuerySolution> rs = qexec.execSelect();
		
		while( rs.hasNext() ){
			QuerySolution soln = rs.next();
			Thing thing = new Thing();
			if( soln.getResource("wikiLink") != null ) thing.setWikiLink( soln.getResource("wikiLink").getURI() );
			if( soln.getLiteral("name") != null ) thing.setName(soln.getLiteral("name").getString() );
			res.add( thing );
		}
		return res;	

	}
}
