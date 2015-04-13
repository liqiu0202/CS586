

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Author extends Thing {
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

	public static void processQuery(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		String queryString = "PREFIX Book: <http://dbpedia.org/ontology/Book>\n"
				+ "PREFIX Movie: <http://dbpedia.org/ontology/Film>\n"
				+ "PREFIX wikiLink: <http://www.w3.org/ns/prov#wasDerivedFrom>\n"
				+ "PREFIX name: <http://xmlns.com/foaf/0.1/name>\n"
				+ "PREFIX author: <http://dbpedia.org/ontology/author>\n"
				+ "PREFIX writer:<http://dbpedia.org/ontology/writer>\n";
		String condition = "";
		String movieName = request.getParameter("movieName");
		String bookName = request.getParameter("bookName");

		if (movieName != null)
			condition +=  "\nFILTER regex( str(?movieName), '" + movieName + "', 'i' ).";
		
		if (bookName != null)
			condition +=  "\nFILTER regex( str(?bookName), '" + bookName + "', 'i' ).";

		queryString = queryString
				+ "SELECT DISTINCT ?wikiLink ?name WHERE{?Movie a Movie:; writer: ?Author; name: ?movieName."
				+ "\nFILTER isIRI(?Author)."
				+ " ?Book a Book:; author: ?Author; name: ?bookName."
				+ "\nFILTER isIRI(?Author)."
				+ "?Author wikiLink: ?wikiLink;"
				+ " name: ?name." + condition + "}LIMIT 100";

		System.out.println(queryString);
		ArrayList<Thing> res = new SPARQLHelper().processQuery(queryString);
    	ObjectMapper mapper = new ObjectMapper();
		String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(res);
		response.getWriter().println( jsonString );
	}
}
