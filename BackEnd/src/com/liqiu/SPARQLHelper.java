package com.liqiu;

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
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

public class SPARQLHelper {
	public static ArrayList<QuerySolution> processQuery(String queryString){
//		String dftGraphURI = "file:default-graph.ttl" ; 
		List<String> namedGraphURIs = new ArrayList<String>() ; 
		namedGraphURIs.add("/Users/liqiu/Desktop/BookLarge.ttl");
		namedGraphURIs.add("/Users/liqiu/Desktop/MovieLarge.ttl") ; 
		Query query = QueryFactory.create(queryString);
		Dataset dataset = DatasetFactory.create(namedGraphURIs) ;
    	Model model = ModelFactory.createOntologyModel();
//		model.read("/Users/liqiu/Documents/CS586/RDFFiles/BookAndMovieWithData.owl", "RDF/XML") ;
//		model.read("/Users/liqiu/Desktop/BookLarge.ttl", "TURTLE");

//		Query query = QueryFactory.create(queryString);
		QueryExecution qexec = QueryExecutionFactory.create(query, dataset);
		//Execute.
		ArrayList<QuerySolution> res = new ArrayList<QuerySolution>();
		Iterator<QuerySolution> rs = qexec.execSelect();
		while( rs.hasNext() ){
			QuerySolution soln = rs.next();
			res.add( soln );
		}
		System.out.println("---------executed correctly--------");
		return res;
    }
}
