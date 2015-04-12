

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

		String condition = "";
		String movieName = request.getParameter("movieName");

		if (movieName != null)
			condition += "\n ?Movie a onto:Movie; onto:starring ?Actor; onto:name ?movieName."
					+ " \nFILTER( str(?movieName) = \"" + movieName + "\").";

		String queryString = "PREFIX onto: <http://www-scf.usc.edu/~liqiu/cs586/BookAndMovie.owl#>\n"
				+ "SELECT DISTINCT ?Actor ?wikiLink ?name WHERE{ ?Actor a onto:Actor; onto:wikiLink ?wikiLink;"
				+ " onto:name ?name." + condition + "}";

		System.out.println(queryString);
		ArrayList<Thing> res = SPARQLHelper.processQuery(queryString);
    	ObjectMapper mapper = new ObjectMapper();
		String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(res);
		response.getWriter().println( jsonString );
	}
}
