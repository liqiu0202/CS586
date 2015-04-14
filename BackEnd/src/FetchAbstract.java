import java.io.*;
import java.net.URL;
import java.net.URLConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Servlet implementation class FetchAbstract
 */
@WebServlet("/FetchAbstract")
public class FetchAbstract extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/plain;charset=utf-8");
		String wikiLink = request.getParameter("wikiLink");
		if (wikiLink == null)
			return;
		URL url = new URL(wikiLink);
		URLConnection urlConnection = url.openConnection();
		InputStream in = new BufferedInputStream(urlConnection.getInputStream());

		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(in);
			Element root = doc.getDocumentElement();
			Element paragraph = (Element) root.getElementsByTagName("p")
					.item(0);
			String[] content = paragraph.getTextContent().split(" ");
			StringBuilder sb = new StringBuilder();
			for(int i = 0; i < content.length && i < 50; ++i){
				if( sb.length() > 0 )
					sb.append(" ");
				sb.append( content[i] );
			}
			sb.append("...");
			response.getWriter().println(sb.toString() );
			
		} catch (Exception e) {
			response.getWriter().println("Abstract Unavailable");
			e.printStackTrace();
		}
		in.close();
		// StringBuilder sb = new StringBuilder();
		// String inputLine;
		// while ((inputLine = in.readLine()) != null){
		// sb.append(inputLine);
		// }
		// in.close();
		// response.getWriter().print(sb.toString());
	}

}
