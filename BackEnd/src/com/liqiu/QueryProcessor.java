package com.liqiu;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
