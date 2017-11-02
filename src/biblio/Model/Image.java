package biblio.Model;

/**
 * @author marco
 * @author MARCO
 *
 */

public class Image {
		
	private int id;
	private int indice;
	private String path;
	private int id_pub;
	private boolean revisionato;
	
	
	
	public Image(int id, int indice, String path, int id_pub, boolean res) {
		super();
		this.id = id;
		this.indice = indice;
		this.path = path;
		this.id_pub = id_pub;
		this.revisionato= res;
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
	public String getpath() {
		return path;
	}
	public void setpath(String path) {
		this.path = path;
	}
	public int getId_pub() {
		return id_pub;
	}
	
	public void setRev(boolean t) {
		this.revisionato = t;
	}
	public boolean getRev() {
		return revisionato;
	}
	
	
	
}
