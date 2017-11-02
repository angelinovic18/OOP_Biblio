package biblio.Model.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import biblio.Util.Database;
/**
 * @author MARCO
 *
 */
public class TrascrizioneDAO implements TrascrizioneDAO_Interface {
	
	 /**
     * dato l'indice, l'id dell'opera e il contenuto inserisce la trascrizione 
     * @param indice   indice della trascrizione da inserire
     * @param idd      id dell'opera associata alla trascrizione
     * @param textarea contenuto della trascrizione da inserire
     * @return
     * @throws java.sql.SQLException
     */
	public static void insert(int indice, int idd, String textarea) throws Exception {

		Database.connect();

		ResultSet rs = Database.selectRecord("trascrizioni", "indice=" + indice + "&& id_pub=" + idd);

		if (!(rs.next())) {
			// String contenuto_editor=
			// request.getParameter("contenuto_editor");
			Map<String, Object> temp = new HashMap<String, Object>();
			temp.put("indice", indice);
			temp.put("contenuto", textarea);
			temp.put("id_pub", idd);
			temp.put("validato", 0);
			Database.insertRecord("trascrizioni", temp);

		} else {

			Map<String, Object> temp2 = new HashMap<String, Object>();

			temp2.put("contenuto", textarea);
			if (Database.updateRecord("trascrizioni", temp2, "indice=" + indice + "&& id_pub=" + idd))
				System.out.println("update fatto");
			else {
				System.out.println("update non fatto");
			}
		}
		
		Database.close();
	}
	
	 /**
     * permette di settare a true il booleano relativo alla colonna "valida" della trascrizione  
     * @param indice   indice della trascrizione da eliminare
     * @param idd      id dell'opera associata alla trascrizione
     * @return
     * @throws java.sql.SQLException
     */
	public static void valida(int indice, int idd) throws Exception{
		
		Database.connect();
		Map<String, Object> imm = new HashMap<String, Object>();
		imm.put("validato", '1'); 
		Database.updateRecord("trascrizioni", imm, "indice='" + indice + "' AND id_pub = '" + idd + "'");
		Database.close();
	}
	
	
	 /**
     * cancella, dato l'indice e l'id, la relativa trascrizione  
     * @param indice   indice della trascrizione da eliminare
     * @param idd      id dell'opera associata alla trascrizione
     * @return
     * @throws java.sql.SQLException
     */
	public static void delete(int indice, int idd) throws Exception{
		
		Database.connect();
		Database.deleteRecord("trascrizioni", "indice='" + indice + "' AND id_pub = '" + idd + "'");
		Database.close();
	}
}
