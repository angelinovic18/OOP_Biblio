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


public class Backoffice extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	 Map data= new HashMap();
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws Exception{
    	
   	 HttpSession s = SecurityLayer.checkSession(request);
   	 if(s!=null){
   		 
   		int gruppo=0;
        try {
		 gruppo= UtenteDAO.getGroup((String)s.getAttribute("username"));
        } catch (Exception e) {
		e.printStackTrace();
        }
        
       data.put("gruppo",gruppo);
   		 
   		 /* Gestione Opere */
   		 
   		 if(!isNull(request.getParameter("elimina"))){
   			 
   			 String opera= request.getParameter("opera");
   			 OperaDAO.delete_opera(opera);

   			//System.out.println(opera);
   			
   			

   		 }
   		 
   		 if(!isNull(request.getParameter("vai"))){
   			 
   			 String id= request.getParameter("id");
   			 response.sendRedirect("details?id="+ id +"&index="+0);
   		 }

   		 if(!isNull(request.getParameter("pubblica"))){
   			 
   			
   			 
   			 String opera = request.getParameter("opera");
   			System.out.println(opera);
   			 OperaDAO.pubblica(opera);
   			
   			     		 
   		 }
   		 
   		 /* Gestione Utenza */
   		 
   		if(!isNull(request.getParameter("amministratore"))){
  			 
  			 String Utente= request.getParameter("utente");
  			 UtenteDAO.promuovi_amministratore(Utente);
  		 }
   		 
   		 if(!isNull(request.getParameter("avanzato"))){
   			 
   			 String Utente= request.getParameter("utente");
   			 UtenteDAO.promuovi_avanzato(Utente);
   		 }
   		 
   		 if(!isNull(request.getParameter("acquisitore"))){
   			 
   			 String Utente= request.getParameter("utente");
   			 UtenteDAO.promuovi_acquisitore(Utente);
   		 }
   		 
   		 if(!isNull(request.getParameter("trascrittore"))){
   			 
   			 String Utente= request.getParameter("utente");
   			UtenteDAO.promuovi_trascrittore(Utente);
   		 }
   		 
   		 if(!isNull(request.getParameter("revisore_a"))){
   			 
   			 String Utente= request.getParameter("utente");
   			 UtenteDAO.promuovi_revisore_a(Utente);
   		 }
   		 
   		 if(!isNull(request.getParameter("revisore_t"))){
   			 
   			 String Utente= request.getParameter("utente");
   			 UtenteDAO.promuovi_revisore_t(Utente);
   		 }
   		 
   		if(!isNull(request.getParameter("elimina_ut"))){
   	      
   	       String Utente= request.getParameter("utente");
   	      UtenteDAO.elimina_utente(Utente);
   	     }
   		 
   		 
   		 List<Opera> lista_opere= new ArrayList<Opera>();
   		 List<Utente> lista_utenti= new ArrayList<Utente>();

   		 /* Inserimento lista in lista_opere */
   		 
   		 lista_opere= OperaDAO.returnList();
   		 lista_utenti= UtenteDAO.returnListutenti();
   		 
   		 /* Passaggio di dati alla mappa "data" */
   		 
   		String test= DataUtil.getUsername((String) s.getAttribute("username"));
	        data.put("test", test);
	        data.put("lista_opere", lista_opere);
	        data.put("lista_utenti", lista_utenti);
	       
	        System.out.print(data.get("gruppo"));
	       
	        FreeMarker.process("backoffice/home_bo.html", data, response, getServletContext());
	        
   	 } else 	 
   		 FreeMarker.process("home.html", data, response, getServletContext());
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