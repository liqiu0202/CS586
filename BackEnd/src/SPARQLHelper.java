

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.hp.hpl.jena.query.Dataset;
import com.hp.hpl.jena.query.DatasetFactory;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;

public class SPARQLHelper {
	public static ArrayList<Thing> processQuery(String queryString)throws IOException{
		List<String> namedGraphURIs = new ArrayList<String>() ; 
		namedGraphURIs.add("http://www-scf.usc.edu/~liqiu/cs586/BookLarge.ttl");
		namedGraphURIs.add("http://www-scf.usc.edu/~liqiu/cs586/MovieLarge.ttl") ; 
		Query query = QueryFactory.create(queryString);
		Dataset dataset = DatasetFactory.create(namedGraphURIs) ;
		QueryExecution qexec = QueryExecutionFactory.create(query, dataset);
		//Execute.
		ArrayList<Thing> res = new ArrayList<Thing>();
		Iterator<QuerySolution> rs = qexec.execSelect();
		while( rs.hasNext() ){
			QuerySolution soln = rs.next();
			Thing thing = new Thing();
			if( soln.getLiteral("wikiLink") != null ) thing.setWikiLink( soln.getLiteral("wikiLink").toString() );
			if( soln.getLiteral("name") != null ) thing.setName(soln.getLiteral("name").toString() );
			res.add( thing );
		}
		return res;		
    }
}
