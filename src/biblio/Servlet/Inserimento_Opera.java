/**
 * 
 */
package biblio.Servlet;

import biblio.Model.Opera;
import biblio.Model.Utente;
import biblio.Model.DAO.OperaDAO;
import biblio.Model.DAO.UtenteDAO;

import biblio.Util.DataUtil;
import static biblio.Util.DataUtil.crypt;
import biblio.Util.Database;
import biblio.Util.FreeMarker;
import biblio.Util.SecurityLayer;

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
import static java.util.Objects.isNull;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
/**
 * @author MARCO
 *
 */
public class Inserimento_Opera extends HttpServlet{

	 /**
	 * 
	 */
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
	    	
	    	 
	    	 HttpSession s = SecurityLayer.checkSession(request);
	    	 Map<String,Object> data= new HashMap<String,Object>();

	    	  if (s != null) {
	                data.put("session",s.getAttribute("username"));
	                System.out.println(s.getAttribute("username"));
	                String test = null;
					try {
						test = DataUtil.getUsername((String) s.getAttribute("username"));
						System.out.println(test);
					} catch (Exception e) {
						e.printStackTrace();
					}
		 	        data.put("test", test);
		 	        
		 	       
		 	        
	                FreeMarker.process("backoffice/inserimento_opera.html", data, response, getServletContext());
	                
	            } else FreeMarker.process("login.html", data, response, getServletContext());
	    
	    }

	     @Override
	    protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
	    	 HttpSession s = SecurityLayer.checkSession(request);
	    	Map<String,Object> data= new HashMap<String,Object>();

	    	  String nome = request.getParameter("titolo");
	    	  
	    	  
	    	  
	    	  
	    	  
	    	  //getting current date and time using Date class
	          DateFormat df = new SimpleDateFormat("dd/MM/yy");
	          Date dd = new Date();
              String autore = request.getParameter("autore");
              String npagine = request.getParameter("npagine");
              String lingua = request.getParameter("lingua");
              String user= (String) s.getAttribute("username");
              
              data.put("nome",nome);
              data.put("data",df.format(dd));
              data.put("num_pagine",npagine);
              data.put("autore",autore);              
              data.put("lingua",lingua);
              data.put("user", user);
              
              

              try {
				Database.connect();
			} catch (Exception e) {
				e.printStackTrace();
			}
              
             try {
				Database.insertRecord("pub", data);
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