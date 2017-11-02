package biblio.Model.DAO;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;

import biblio.Model.Utente;
import biblio.Util.Database;
/**
 * @author MARCO
 *
 */
public class UtenteDAO implements UtenteDAO_Interface{
	
	/**
     * promuovi un utente alla qualifica "amministratore"  
     * @param utente   utente da promuovere
     * @return
     * @throws java.sql.SQLException
     */
	public static void promuovi_amministratore(String Utente) throws Exception {

		Database.connect();
		Map<String, Object> temp = new HashMap<String, Object>();
		temp.put("gruppo", '1'); // Come da accordo con DB
		Database.updateRecord("users", temp, "email='" + Utente + "'");
		Database.close();
	}
	
	
	 /**
     * promuovi un utente alla qualifica "utente avanzato"  
     * @param utente   utente da promuovere
     * @return
     * @throws java.sql.SQLException
     */
	public static void promuovi_avanzato(String Utente) throws Exception {

		Database.connect();
		Map<String, Object> temp = new HashMap<String, Object>();
		temp.put("gruppo", '7'); // Come da accordo con DB
		Database.updateRecord("users", temp, "email='" + Utente + "'");
		Database.close();
	}

	
	/**
     * promuovi un utente alla qualifica "acquisitore"  
     * @param utente   utente da promuovere
     * @return
     * @throws java.sql.SQLException
     */
	public static void promuovi_acquisitore(String Utente) throws Exception {

		Database.connect();
		Map<String, Object> temp = new HashMap<String, Object>();
		temp.put("gruppo", '5');
		Database.updateRecord("users", temp, "email='" + Utente + "'");
		Database.close();
	}

	
	/**
     * promuovi un utente alla qualifica "trascrittore"  
     * @param utente   utente da promuovere
     * @return
     * @throws java.sql.SQLException
     */
	public static void promuovi_trascrittore(String Utente) throws Exception {

		Database.connect();
		Map<String, Object> temp = new HashMap<String, Object>();
		temp.put("gruppo", '3');
		Database.updateRecord("users", temp, "email='" + Utente + "'");
		Database.close();
	}
	
	
	/**
     * promuovi un utente alla qualifica "revisore acquisizioni"  
     * @param utente   utente da promuovere
     * @return
     * @throws java.sql.SQLException
     */
	public static void promuovi_revisore_a(String Utente) throws Exception {

		 Database.connect();
		 Map<String, Object> temp= new HashMap<String,Object>();
		 temp.put("gruppo",'6');
		 Database.updateRecord("users", temp, "email='"+Utente+"'");
		 Database.close();
	}
	
	
	/**
     * promuovi un utente alla qualifica "revisore trascrizioni"  
     * @param utente   utente da promuovere
     * @return
     * @throws java.sql.SQLException
     */
	public static void promuovi_revisore_t(String Utente) throws Exception {

		 Database.connect();
		 Map<String, Object> temp= new HashMap<String,Object>();
		 temp.put("gruppo",'4');
		 Database.updateRecord("users", temp, "email='"+Utente+"'");
		 Database.close();
	}
	
	/**
     * elimina un utente 
     * @param utente   utente da eliminare
     * @return
     * @throws java.sql.SQLException
     */
	 public static void elimina_utente(String Utente) throws Exception {
	
	   Database.connect();
	   Database.deleteRecord("users","email='"+Utente+"'");
	   Database.close();
	 }
		
	/**
     * dato un'email restituisce l'id del gruppo di appartenenza
     * 1 - Amministratore
	  * 2 - UtenteBase
	  * 3 - Trascrittore
	  * 4 - RevisoreTrascrizioni
	  * 5 - Aquisitore
	  * 6 - RevisoreAquisizioni
	  * 7 - UtenteAvanzato
     * @param email   email dell'utente
     * @return int
     * @throws java.sql.SQLException
     */
	 public static int getGroup(String email) throws Exception{
	  int id = 0;
	  Database.connect();
	  String condition = "email='" + email + "'";
	  ResultSet rs = Database.selectRecord("users", condition);
	  while (rs.next()) {
	   id = rs.getInt("gruppo");
	  }
	  System.out.println("GROUP :" + id);
	  return id;
	 }
	 
	 
	 /**
	     * restituisce la lista degli utenti  
	     * @return lista utenti registrati
	     * @throws java.sql.SQLException
	     */
	public static List<Utente> returnListutenti() throws Exception{
    	
    	List<Utente> temp2= new ArrayList<Utente>();


    	try{

			 Database.connect();
		        
		         ResultSet ss =Database.selectRecord("users","1");
		         
		         while(ss.next()){ 
		        	 
			        String email= ss.getString("email");

		        	String nome= ss.getString("nome");
		        	String cognome= ss.getString("cognome");
		        	Date annonascita=ss.getDate("annonascita");
		        	String citta=ss.getString("citta");
		        	int gruppo=ss.getInt("gruppo");
		        	 
		        	Utente temputente= new Utente (email,nome,cognome,citta,annonascita,gruppo);
		        	temp2.add(temputente);
		       }
		                
		      }catch(NamingException e)
		      {     
		      } catch (SQLException e) {
		      }
    	
		        return temp2; 
    }
}
