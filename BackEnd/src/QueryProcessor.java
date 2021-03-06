

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Servlet implementation class QueryProcessor
 */
@WebServlet("/QueryProcessor")
public class QueryProcessor extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		response.setContentType("application/json; charset=UTF-8");
		PrintWriter out = response.getWriter();
		String queryString = request.getParameter("queryString");
		if( queryString != null ){
			ArrayList<Thing> res = new SPARQLHelper().processQuery(SPARQLHelper.prefixString + queryString);
			System.out.println(SPARQLHelper.prefixString + "\n" + queryString);
	    	ObjectMapper mapper = new ObjectMapper();
			String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(res);
			response.getWriter().println( jsonString );
			return;
		}
		String target = request.getParameter("target");
		if (target == null)
			return;
		if (target.equals("Book")) {
			Book.processQuery(request, response);
		} else if (target.equals("Movie")) {
			Movie.processQuery(request, response);
		} else if (target.equals("Author")) {
			Author.processQuery(request, response);
		} else if (target.equals("Actor")) {
			Actor.processQuery(request, response);
		} else if (target.equals("Director")) {
			Director.processQuery(request, response);
		} else {
			out.println("Unsupported Query target: " + target);
		}
	}
}
