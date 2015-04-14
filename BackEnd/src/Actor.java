

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;


public class Actor extends Thing{
	private String movieName;

	public String getMovieName() {
		return movieName;
	}

	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}
	
	public static void processQuery(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		String queryString = "PREFIX Movie: <http://dbpedia.org/ontology/Film>\n"
				+ "PREFIX wikiLink: <http://www.w3.org/ns/prov#wasDerivedFrom>\n"
				+ "PREFIX name: <http://xmlns.com/foaf/0.1/name>\n"
				+ "PREFIX author:<http://dbpedia.org/ontology/writer>\n"
				+ "PREFIX language: <http://dbpedia.org/property/language>\n"
				+ "PREFIX director: <http://dbpedia.org/property/director>\n"
				+ "PREFIX starring: <http://dbpedia.org/property/starring>\n"
				+ "PREFIX description: <http://dbpedia.org/ontology/abstract>";
		String condition = "";
		String movieName = request.getParameter("movieName");

		if (movieName != null)
			condition += "\nFILTER regex( str(?movieName), '" + movieName + "', 'i' ).";

		queryString = queryString
				+ "SELECT DISTINCT ?wikiLink ?name ?description WHERE{ ?Movie a Movie:; starring: ?Actor; name: ?movieName.\n"
				+ "FILTER isIRI(?Actor).\n"
				+ "?Actor wikiLink: ?wikiLink; description: ?description;"
				+ " name: ?name." + condition + "}LIMIT 100";

		System.out.println(queryString);
		ArrayList<Thing> res = new SPARQLHelper().processQuery(queryString);
    	ObjectMapper mapper = new ObjectMapper();
		String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(res);
		response.getWriter().println( jsonString );
	}
}
