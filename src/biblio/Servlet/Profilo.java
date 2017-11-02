package biblio.Servlet;

import static biblio.Util.DataUtil.crypt;
import static java.util.Objects.isNull;

import java.io.IOException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import biblio.Model.Opera;
import biblio.Model.Utente;
import biblio.Util.DataUtil;
import biblio.Util.Database;
import biblio.Util.FreeMarker;
import biblio.Util.SecurityLayer;
/**
 * @author marco
 *
 */

public class Profilo extends HttpServlet {
	
	Map<String,Object> data= new HashMap<String,Object>();
    
	 /**
	     * Caricamento pagina di Home
	     *
	     * @param request servlet request
	     * @param response servlet response
	     * @throws ServletException if a servlet-specific error occurs
	     * @throws IOException if an I/O error occurs
	     */
	    @Override
	    protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
	        
           HttpSession s = SecurityLayer.checkSession(request);

	    	  if (s != null) {
	    		  
	    		  	List<String> listaopere= new ArrayList<String>();
	                data.put("session",s.getAttribute("username"));
	                String test = null;
					try {
						test = DataUtil.getUsername((String) s.getAttribute("username"));
					} catch (Exception e) {
						e.printStackTrace();
					}
					Utente profilo= null;
		 	        data.put("test", test);
		 	        try {
						Database.connect();
						ResultSet rs= Database.selectRecord("users", "email='"+s.getAttribute("username")+"'");
						while(rs.next()){
							
							String usermail= (String) s.getAttribute("username");
						     String nome= rs.getString("nome");
						    String cognome= rs.getString("cognome");
						    String citta= rs.getString("citta");
						    Date datanascita= rs.getDate("annonascita");
						    int gruppo= rs.getInt("gruppo");
						    
						    profilo= new Utente(usermail,nome,cognome,citta,datanascita,gruppo);
							
						}
						
						ResultSet ss= Database.selectRecord("pub", "user='"+s.getAttribute("username")+"'");
						while(ss.next()){
							
							listaopere.add(ss.getString("nome"));
						}
						
						Database.close();
					
					} catch (Exception e) {
						e.printStackTrace();
					}
		 	        
		 	        data.put("listaopere", listaopere);
		 	        data.put("profilo", profilo);
	                FreeMarker.process("profilo.html", data, response, getServletContext());
	                
	            } else FreeMarker.process("login.html", data, response, getServletContext());
	    }

	     @Override
	    protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
	        
	    	 FreeMarker.process("profilo.html", data, response, getServletContext());
	    }

	}