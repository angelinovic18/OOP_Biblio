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
public class Visualizza_Titoli extends HttpServlet{

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
	    	
	    	
	    	 Map<String,Object> data= new HashMap<String,Object>();
	             
	    	 List<Opera> lista_opere= new ArrayList<Opera>();

	   		 /* Inserimento lista in lista_opere */
	   		 
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
	   		 
	   		 
	   		data.put("lista_opere", lista_opere);

		 	        
	        FreeMarker.process("visualizza_titoli.html", data, response, getServletContext());
	    }
	                
	          
	    
	    

	     @Override
	     protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	 		// TODO Auto-generated method stub
	 		doGet(request, response);
	 	}

	}