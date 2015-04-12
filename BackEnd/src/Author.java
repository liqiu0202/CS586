

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

		String condition = "";
		String movieName = request.getParameter("movieName");
		String bookName = request.getParameter("bookName");

		if (movieName != null)
			condition += "\n ?Movie a onto:Movie; onto:author ?Author; onto:name ?movieName."
					+ " \nFILTER( str(?movieName) = \"" + movieName + "\").";
		if (bookName != null)
			condition += "\n ?Book a onto:Book; onto:author ?Author; onto:name ?bookName."
					+ " \nFILTER( str(?bookName) = \"" + bookName + "\").";

		String queryString = "PREFIX onto: <http://www-scf.usc.edu/~liqiu/cs586/BookAndMovie.owl#>\n"
				+ "SELECT DISTINCT ?Author ?wikiLink ?name WHERE{ ?Author a onto:Author; onto:wikiLink ?wikiLink;"
				+ " onto:name ?name." + condition + "}";

		System.out.println(queryString);
		ArrayList<Thing> res = SPARQLHelper.processQuery(queryString);
    	ObjectMapper mapper = new ObjectMapper();
		String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(res);
		response.getWriter().println( jsonString );
	}
}
