package biblio.Model.DAO;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;

import biblio.Model.Opera;
import biblio.Util.Database;
/**
 * @author marco
 *
 */
public class OperaDAO implements OperaDAO_Interface {

	/**
	 * cancella, dato il nome dell'opera, la relativa opera nel DB
	 * 
	 * @param opera
	 *            nome dell'opera da eliminare
	 * @return
	 * @throws java.sql.SQLException
	 */
	public static void delete_opera(String opera) throws Exception {

		Database.connect();
		Database.deleteRecord("pub", "nome='" + opera + "'");
		Database.close();

	}

	/**
	 * dato il nume dell'opera, setta a True il valore "pubblicato"
	 * 
	 * @param opera
	 *            nome dell'opera da pubblicare
	 * @return
	 * @throws java.sql.SQLException
	 */
	public static void pubblica(String opera) throws Exception {

		Database.connect();
		Map<String, Object> temp = new HashMap<String, Object>();
		temp.put("pubblicato", '1');
		Database.updateRecord("pub", temp, "nome = '" + opera + "'");
		System.out.println("ciao");
	}

	/**
	 * restituisce la lista di opere
	 * 
	 * @return lista di opere
	 * @throws java.sql.SQLException
	 */
	public static List<Opera> returnList() throws Exception {

		List<Opera> temp = new ArrayList<Opera>();

		try {

			Database.connect();

			ResultSet rs = Database.selectRecord("pub", "1");

			while (rs.next()) {
				int id = rs.getInt("id_op");
				String nome = rs.getString("nome");
				Date data = rs.getDate("data");
				String autore = rs.getString("autore");
				String lingua = rs.getString("lingua");
				String utente = rs.getString("user");

				Opera tempopera = new Opera(id, nome, data, autore, lingua, utente);
				temp.add(tempopera);
			}

		} catch (NamingException e) {
		} catch (SQLException e) {
		}

		return temp;

	}

}
