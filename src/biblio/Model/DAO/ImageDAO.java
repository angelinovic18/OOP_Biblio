package biblio.Model.DAO;

import java.util.HashMap;
import java.util.Map;

import biblio.Util.Database;
/**
 * @author MARCO
 * @author marco
 *
 */
public class ImageDAO implements ImageDAO_Interface {
	
	 /**
     * cancella, dato l'indice e l'id, la relativa immagine  
     * @param indice   indice dell'immagine da eliminare
     * @param idd      id dell'opera associata all'immagine
     * @return
     * @throws java.sql.SQLException
     */
	
	public static void delete_image(int indice, int idd) throws Exception{
		 Database.connect();
	     Database.deleteRecord("immagini", "indice='" + indice + "' AND id_pub = '" + idd + "'");
	     Database.close();
	}
	
	 /**
     * dato l'id e l'indice dell'immagine, setta il booleano "revisiona" a true
     * @param indice   indice dell'immagine da revisionare
     * @param idd      id dell'opera associata all'immagine
     * @return
     * @throws java.sql.SQLException
     */
	public static void revisiona(int indice, int idd) throws Exception{
		
		 Database.connect();
	     Map<String, Object> imm = new HashMap<String, Object>();
	     imm.put("revisionato", '1');
	     Database.updateRecord("immagini", imm, "indice='" + indice + "' AND id_pub = '" + idd + "'");
	     Database.close();

	}
}
