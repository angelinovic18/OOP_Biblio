package biblio.Servlet;

import static biblio.Util.DataUtil.crypt;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import biblio.Model.Opera;
import biblio.Model.Utente;
import biblio.Model.DAO.OperaDAO;
import biblio.Model.DAO.UtenteDAO;
import biblio.Util.DataUtil;
import biblio.Util.Database;
import biblio.Util.FreeMarker;
import biblio.Util.SecurityLayer;
/**
 * @author marco
 *
 */
public class Inserimento_Utente extends HttpServlet{

	private static final long serialVersionUID = 1L;
           
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
	    	Map<String,Object> data= new HashMap<String,Object>();

	    	 HttpSession s = SecurityLayer.checkSession(request);

	    	  if (s != null) {
	                data.put("session",s.getAttribute("username"));
	                String test = null;
					try {
						test = DataUtil.getUsername((String) s.getAttribute("username"));
					} catch (Exception e) {
						e.printStackTrace();
					}
		 	        data.put("test", test);
		 	        
		 	      
		 	        
	                FreeMarker.process("backoffice/inserimento_utente.html", data, response, getServletContext());
	                
	            } else FreeMarker.process("home.html", data, response, getServletContext());
	    }

	     @Override
	    protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
	    	 HttpSession s = SecurityLayer.checkSession(request);
	    	 
	    	 
	    	 
	    		Map<String,Object> data= new HashMap<String,Object>();
	    		
	    		
	    		

	    	  String email = request.getParameter("email");
	    	  String password = request.getParameter("pass");
	    	  String nome = request.getParameter("nome");
	    	  String cognome = request.getParameter("cognome");
	    	  String annonascita= request.getParameter("data");
	    	  String citta= request.getParameter("citta");
	    	  int gruppo2= 2;

             data.put("email",email);
             data.put("password", crypt(password));
             data.put("nome",nome);
             data.put("cognome",cognome);
             data.put("annonascita",annonascita);
             data.put("citta", citta);
             data.put("gruppo", gruppo2);

             try {
				Database.connect();
			} catch (Exception e) {
				e.printStackTrace();
			}
            try {
				Database.insertRecord("users", data);
			} catch (SQLException e) {
				e.printStackTrace();
			}
            
            try {
				Database.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
            
            List<Opera> lista_opere= new ArrayList<Opera>();
      		 List<Utente> lista_utenti= new ArrayList<Utente>();

      		 /* Inserimento lista in lista_opere */
      		 
      		 try {
				lista_opere= OperaDAO.returnList();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
      		 try {
				lista_utenti= UtenteDAO.returnListutenti();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
      		 
      		 /* Passaggio di dati alla mappa "data" */
      		 
      		 data.remove("gruppo");
      		 
      		 
      		int gruppo=0;
 	        try {
 			 gruppo= UtenteDAO.getGroup((String)s.getAttribute("username"));
 	        } catch (Exception e) {
 			e.printStackTrace();
 	        }
          data.put("gruppo", gruppo);
      		
   	        data.put("lista_opere", lista_opere);
   	        data.put("lista_utenti", lista_utenti);
	 	        FreeMarker.process("backoffice/home_bo.html", data, response, getServletContext());             
	    }

	}