package biblio.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import biblio.Util.FreeMarker;
import biblio.Util.SecurityLayer;
/**
 * @author marco
 *
 */
public class Logout extends HttpServlet {
	
	  Map<String,Object> data= new HashMap<String,Object>();
			  
		protected void doGet(HttpServletRequest request, HttpServletResponse response)
                                throws ServletException, IOException {
			
			 try {
		            SecurityLayer.disposeSession(request);
		            FreeMarker.process("login.html", data , response, getServletContext());

		        } catch (IOException ex) {
		            request.setAttribute("exception", ex);
		            System.out.println("NON VA");
		        }
	}
}