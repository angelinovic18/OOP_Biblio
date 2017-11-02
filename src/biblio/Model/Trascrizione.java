package biblio.Model;
/**
 * @author marco
 * @author MARCO
 *
 */
public class Trascrizione {
	
	private int id;
	private int indice;
	private String contenuto;
	private int id_pub;
	private boolean validato;
	
	
	public Trascrizione(int id, int indice, String contenuto, int id_pub, boolean validato) {
		super();
		this.id = id;
		this.indice = indice;
		this.contenuto = contenuto;
		this.id_pub = id_pub;
		this.validato = validato;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIndice() {
		return indice;
	}
	public void setIndice(int indice) {
		this.indice = indice;
	}
	public String getContenuto() {
		return contenuto;
	}
	public void setContenuto(String contenuto) {
		this.contenuto = contenuto;
	}
	public int getId_pub() {
		return id_pub;
	}
	public void setId_pub(int id_pub) {
		this.id_pub = id_pub;
	}
	public boolean isValidato() {
		return validato;
	}
	public void setValidato(boolean validato) {
		this.validato = validato;
	}
	
	
}
