package biblio.Model;

import java.util.Date;
/**
 * @author marco
 * @author MARCO
 *
 */
public class Opera {
		
	private int id;
	private String nome;
	private Date data;
	private String autore;
	private String lingua;
	private String utente;
	
	
	
	public Opera(int id, String nome, Date data, String autore, String lingua, String utente) {
		super();
		this.id = id;
		this.nome = nome;
		this.data = data;
		this.autore = autore;
		this.lingua = lingua;
		this.utente = utente;
	}
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public String getAutore() {
		return autore;
	}
	public void setAutore(String autore) {
		this.autore = autore;
	}
	public String getLingua() {
		return lingua;
	}
	public void setLingua(String lingua) {
		this.lingua = lingua;
	}
	public String getUtente() {
		return utente;
	}
	public void setUtente(String utente) {
		this.utente = utente;
	}
	
	
	
}
