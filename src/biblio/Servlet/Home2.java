package biblio.Servlet;
/**
 * @author marco
 *
 */

import static java.util.Objects.isNull;
import biblio.Model.Opera;
import biblio.Model.Utente;
import biblio.Model.DAO.OperaDAO;
import biblio.Model.DAO.UtenteDAO;
import biblio.Util.DataUtil;
import biblio.Util.Database;
import biblio.Util.FreeMarker;
import biblio.Util.SecurityLayer;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class Home2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	 Map data= new HashMap();
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws Exception{
		  
		data = new HashMap<String, Object>();
    	
   	 HttpSession s = SecurityLayer.checkSession(request);
   	 if(s!=null){

   		 
   		if(!isNull(request.getParameter("vai"))){
  			 
  			 String id= request.getParameter("id");
  			 response.sendRedirect("opera_single?id="+ id +"&index="+0);
  		 }
	       
	        System.out.print(request.getParameter("id"));
	        System.out.print(request.getParameter("opera"));
	       
	        FreeMarker.process("login.html", data, response, getServletContext());
	        
   	 } else 	 
   		 FreeMarker.process("login.html", data, response, getServletContext());
   		}
	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
			processRequest(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
			processRequest(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
} 