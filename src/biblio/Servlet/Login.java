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
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
 * @author marco
 *
 */
public class Login extends HttpServlet{

	
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
            
            if(!isNull(request.getParameter("titoli")))
	    	 {
            	Map<String,Object> data= new HashMap<String,Object>();
	             
   	    	 List<Opera> lista_opere= new ArrayList<Opera>();
   	   		 List<Utente> lista_utenti= new ArrayList<Utente>();

   	   		 /* Inserimento lista in lista_opere */
   	   		 
   	   		 try {
   	   			 	lista_opere= OperaDAO.returnList();	   		
   	   			 	lista_utenti= UtenteDAO.returnListutenti();
   	   		 	 } 
   	   		 
   	   		 catch (Exception e) {
   				// TODO Auto-generated catch block
   				e.printStackTrace();}
   	   		 
   	   		data.put("lista_opere", lista_opere);
   	        data.put("lista_utenti", lista_utenti);
   		 	        
   	        FreeMarker.process("visualizza_titoli.html", data, response, getServletContext());
	    		 
	    	 }
            else if (s != null) {
	    		  
	                data.put("session",s.getAttribute("username"));
	                String test = null;
					try {
						test = DataUtil.getUsername((String) s.getAttribute("username"));
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					List<Opera> lista_opere= new ArrayList<Opera>();
		    		 
		    		 /* inserisco lista in lista_opere */
		    		 
					try{

				        Database.connect();
				              
				              ResultSet rs =Database.selectRecord("pub", "pubblicato = 1");
				              
				              
				               while(rs.next()){ 
				                int id= rs.getInt("id_op");
				               String nome= rs.getString("nome");
				               Date date= rs.getDate("data");
				               String autore=rs.getString("autore");
				               String lingua=rs.getString("lingua");
				               String utente=rs.getString("user");
				                
				           Opera tempopera= new Opera (id,nome,date,autore,lingua,utente);
				           
				          lista_opere.add(tempopera);
				             }
				     }catch(NamingException e)
				           { } 
				     catch (SQLException e) { } 
				     catch (Exception e) {e.printStackTrace();}
		    		 
		    		 /* Riempio la mappa */
		    		 
		 	        data.put("lista_opere", lista_opere);
		 	        data.put("test", test);
		 	        data.put("index", 0);
	                FreeMarker.process("home.html", data, response, getServletContext());
	                
	            } else FreeMarker.process("login.html", data, response, getServletContext());
	    }

	     @Override
	    protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
	    	 
	    	 HttpSession s = SecurityLayer.checkSession(request);

   		     String email = request.getParameter("email_login");
             String pass = request.getParameter("password_login");
             String email_reg = request.getParameter("email_reg");
             String pass_reg = request.getParameter("pass_reg");
             String nome_reg = request.getParameter("nome_reg");
             String cognome_reg = request.getParameter("cognome_reg");
             String citta_reg = request.getParameter("citta_reg");
             String data_reg = request.getParameter("data_reg");
             
             if(email != ""){ 
                 
                 /*LOGIN -- METHOD POST */

           int userid = 0;
		
			try {
				userid = DataUtil.checkUser(email, pass);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if(userid==0){
	
				FreeMarker.process("login.html", data, response, getServletContext());			
		}
			else{
           System.out.println(userid);
           
           	/**	... VALIDAZIONE IDENTITA'...
           		... IDENTITY CHECKS ...
           
                se la validazione ha successo
                if the identity validation succeeds
                carichiamo lo userid dal database utenti
               load userid from user database   **/
           
                SecurityLayer.createSession(request, email , userid);
                data.put("session",email);
                try {
					data.put("test", DataUtil.getUsername(email));
				} catch (Exception e) {
					e.printStackTrace();
				}
                
                List<Opera> lista_opere= new ArrayList<Opera>();
                
                /* inserisco lista in lista_opere */
	    		List<Opera> temp= null;
	    		List<Opera> temp2= null;
	 	    	try{

	 				 Database.connect();
	 			        
	 			        ResultSet rs =Database.selectRecord("pub", "pubblicato = 1");
	 			        temp= new ArrayList<Opera>(); 
	 			        
	 			         while(rs.next()){ 
	 			        	 int id= rs.getInt("id_op");
	 			        	String nome= rs.getString("nome");
	 			        	Date date= rs.getDate("data");
	 			        	String autore=rs.getString("autore");
	 			        	String lingua=rs.getString("lingua");
	 			        	String utente=rs.getString("user");
	 			        	 
	 			     Opera tempopera= new Opera (id,nome,date,autore,lingua,utente);
	 			     
	 			        	temp.add(tempopera);
	 			       }
	 			         
	 			        ResultSet is =Database.selectRecord("pub", "1");
	 			        temp2= new ArrayList<Opera>(); 
	 			        
	 			         while(is.next()){ 
	 			        	 int id= is.getInt("id_op");
	 			        	String nome= is.getString("nome");
	 			        	Date date= is.getDate("data");
	 			        	String autore=is.getString("autore");
	 			        	String lingua=is.getString("lingua");
	 			        	String utente=is.getString("user");
	 			        	 
	 			     Opera tempopera= new Opera (id,nome,date,autore,lingua,utente);
	 			     
	 			        	temp2.add(tempopera);
	 			       }
	 			     
	 			         
	 			    Database.close();
	 			      }catch(NamingException e)
	 			      {     
	 			      } catch (SQLException e) {
	 			      } catch (Exception e) {
						e.printStackTrace();
					}
	    		 
	    		 /* Riempio la mappa */
	 	        data.put("lista_opere", temp);
	 	        data.put("lista_opere_all", temp2);
	 	        data.put("index", 0);
	 	        
	 	        
	 	        /* metodo che permette di inserire il ruolo nel data
	 	        in modo da gestirlo meglio con freemarker  */
	 	        
	 	       int gruppo=0;
	 	        try {
					 gruppo= UtenteDAO.getGroup(email);
				} catch (Exception e) {
					e.printStackTrace();
				}
	 	        
	 	       data.put("gruppo",gruppo);
	 	        
	 	        System.out.print(data.get("gruppo"));

                FreeMarker.process("home.html", data, response, getServletContext());
           
              }
             }
             
             if(email_reg != "" && pass_reg!= "" && nome_reg != "" && cognome_reg != "" && citta_reg != ""){
	              
                 /*REGISTRAZIONE -- METHOD POST */
                 
                   try {
           Database.connect();
           Map<String,Object> map= new HashMap<String,Object>();
           map.put("email",email_reg);
           map.put("password", crypt(pass_reg));
           map.put("nome",nome_reg);
           map.put("cognome",cognome_reg);
           map.put("citta",citta_reg);
           map.put("annonascita",data_reg);
           map.put("gruppo",2);
           
           Database.insertRecord("users",map);
               
           FreeMarker.process("login.html", data, response, getServletContext());
                   
            try {
               Database.close();
           } catch (SQLException ex) {
               Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
           }
       } catch (NamingException ex) {
           Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
       } catch (SQLException ex) {
           Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
       } catch (Exception e) {
		e.printStackTrace();
	}
             }
             else{FreeMarker.process("login.html", data, response, getServletContext());}
	    }

	}