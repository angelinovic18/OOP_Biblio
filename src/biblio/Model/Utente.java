/**
 * 
 */
package biblio.Model;
/**
 * @author MARCO
 * @author marco
 *
 */
import java.sql.Date;
/**
 *
 */
public class Utente {

	private String usermail;
	private String nome;
	private String cognome;
	private String citta;
	private Date datanascita;
	private int gruppo;

	    public Utente(String usermail, String nome, String cognome, String citta, Date datanascita, int gruppi) {
	        this.usermail = usermail;
	        this.nome = nome;
	        this.cognome = cognome;
	        this.citta = citta;
	        this.datanascita = datanascita;
	        this.gruppo= gruppi;
	    }

	    public Utente() {
	        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	    }


	    public String getUsermail() {
	        return usermail;
	    }

	    public void setUsermail(String usermail) {
	        this.usermail = usermail;
	    }

	    public String getNome() {
	        return nome;
	    }

	    public void setNome(String nome) {
	        this.nome = nome;
	    }

	    public String getCognome() {
	        return cognome;
	    }

	    public void setCognome(String cognome) {
	        this.cognome = cognome;
	    }

	    public String getCitta() {
	        return citta;
	    }

	    public void setCitta(String citta) {
	        this.citta = citta;
	    }

	    public Date getDatanascita() {
	        return datanascita;
	    }

	    public void setDatanascita(Date datanascita) {
	        this.datanascita = datanascita;
	    }

	    public int getGruppo() {
	        return gruppo;
	    }

	    public void setGruppo(int gruppo) {
	        this.gruppo = gruppo;
	    }
	    
	    
	}
