package biblio.Servlet;

import static java.util.Objects.isNull;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.mysql.jdbc.Blob;

import biblio.Model.Opera;
import biblio.Model.Trascrizione;
import biblio.Model.Utente;
import biblio.Model.DAO.ImageDAO;
import biblio.Model.DAO.OperaDAO;
import biblio.Model.DAO.TrascrizioneDAO;
import biblio.Model.DAO.UtenteDAO;
import biblio.Util.DataUtil;
import biblio.Util.Database;
import biblio.Util.FreeMarker;
import biblio.Util.SecurityLayer;

/**
 * @author MARCO
 *
 */

@MultipartConfig(maxFileSize = 56177215) // upload file's size up to 16MB

public class Detail extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final int BUFFER_SIZE = 50177215;
	
	private static final String UPLOAD_DIRECTORY = "upload";
	private static final int THRESHOLD_SIZE     = 1024 * 1024 * 3;  // 3MB
	private static final int MAX_FILE_SIZE      = 1024 * 1024 * 40; // 40MB
	private static final int MAX_REQUEST_SIZE   = 1024 * 1024 * 50; // 50MB

	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Map<String,Object> data= new HashMap<String,Object>();
		Map<String,Object> Map= new HashMap<String,Object>();

		HttpSession s = SecurityLayer.checkSession(request);
		String test = null;
		String contenuto_editor2= null;
	    String nome = null;
		
	 /** metodo che permette di inserire il ruolo nel data
	    in modo da gestirlo meglio con freemarker   **/
	    
	       int gruppo=0;
	        try {
			 gruppo= UtenteDAO.getGroup((String)s.getAttribute("username"));
	        } catch (Exception e) {
			e.printStackTrace();
	        }
	        
	       data.put("gruppo",gruppo);
	       
	       if(!isNull(request.getParameter("indietro"))){
		    	
	    	   List<Opera> lista_opere= new ArrayList<Opera>();
	     	   List<Utente> lista_utenti= new ArrayList<Utente>();

	     		 /* Inserimento lista in lista_opere */
	     		 
	     		 lista_opere= OperaDAO.returnList();
	     		 lista_utenti= UtenteDAO.returnListutenti();
	     		 
	     		 /* Passaggio di dati alla mappa "data" */
	     		 
	     		test= DataUtil.getUsername((String) s.getAttribute("username"));
	  	        data.put("test", test);
	  	        data.put("lista_opere", lista_opere);
	  	        data.put("lista_utenti", lista_utenti);
	  	       
	  	        System.out.print(data.get("gruppo"));
	  	       
	  	        FreeMarker.process("backoffice/home_bo.html", data, response, getServletContext());

		    }
	   
	       /* Elimina immagine relativa all'indice corrente */
	       
	       if (!isNull(request.getParameter("elimina"))) {
	    	
	    	   int idd = Integer.parseInt(request.getParameter("idd"));
	    	   int indice = Integer.parseInt(request.getParameter("indice"));

	    	   data.put("id", idd);
	    	   data.put("nomeopera", nome);
	    	   data.put("index", indice);

	    	   ImageDAO.delete_image(indice, idd);

	    	   response.sendRedirect("details?id=" + data.get("id") + "&index=" + data.get("index"));
	       }

	     /* Mette l'opera tra i file revisionati tramite un booleano */
	       
	       if (!isNull(request.getParameter("revisiona"))) {
	    	   
	    	   int idd = Integer.parseInt(request.getParameter("idd"));
	    	   int indice = Integer.parseInt(request.getParameter("indice"));

	    	   data.put("id", idd);
	    	   data.put("nomeopera", nome);
	    	   data.put("index", indice);

	    	   ImageDAO.revisiona(indice, idd);

	    	   response.sendRedirect("details?id=" + data.get("id") + "&index=" + data.get("index"));
	       }
		
		/* Avanzo alla pagina successiva */

	       if(!isNull(request.getParameter("next"))){
	    	
	    	 int idd= Integer.parseInt(request.getParameter("idd"));
			 int indice= Integer.parseInt(request.getParameter("indice"));
			 
			 data.put("id", idd);
			 data.put("nomeopera", nome);
			 data.put("index", indice+1);
			 response.sendRedirect("details?id="+ data.get("id") +"&index="+data.get("index"));
	    	
	       }
	    
		/* Torno alla pagina precedente */

	       if(!isNull(request.getParameter("prev"))){
	    	
	    	 int idd= Integer.parseInt(request.getParameter("idd"));
			 int indice= Integer.parseInt(request.getParameter("indice"));
			 
			 data.put("id", idd);
			 data.put("nomeopera", nome);
			 data.put("index", indice-1);
			 response.sendRedirect("details?id="+ data.get("id") +"&index="+data.get("index"));
	    }
	    
	   /* Vado alla pagina relativa all'upload dell'immagine */

	    if(!isNull(request.getParameter("upload"))){
	    	
	    	 int idd= Integer.parseInt(request.getParameter("idd"));
			 int indice= Integer.parseInt(request.getParameter("indice"));
			 String opera= request.getParameter("nomeopera");
			 
			 
			 data.put("id", idd);
			 data.put("nomeopera", opera);
			 data.put("index", indice);
			 
					 
			 FreeMarker.process("backoffice/upload.html", data, response, getServletContext());

	    }
	    
	    /* Vado alla pagina relativa alla validazione della trascrizione */

	    if(!isNull(request.getParameter("editor"))){
	    	
	    	 int idd= Integer.parseInt(request.getParameter("idd"));
			 int indice= Integer.parseInt(request.getParameter("indice"));
			 String opera= request.getParameter("nomeopera");
			 
			 
			 data.put("id", idd);
			 data.put("nomeopera", opera);
			 data.put("index", indice);
			 
			 /* Prendo TRASCRIZIONE da passare*/
		      ResultSet sss= Database.selectRecord("trascrizioni", "indice="+indice+"&&id_pub="+idd+"");
		      while(sss.next()){
		    	  
		    	contenuto_editor2=sss.getString("contenuto");
		      }
			 data.put("contenuto_editor", contenuto_editor2);
			 
					 
			 FreeMarker.process("backoffice/trascrizione.html", data, response, getServletContext());

	    }
	    
	    
	    /* Inserisco testo trascrizione su DB */

	    if(!isNull(request.getParameter("inserisci"))){
	    	
	    	 String textarea= request.getParameter("textarea");
	    	 int idd= Integer.parseInt(request.getParameter("idd"));
			 int indice= Integer.parseInt(request.getParameter("indice"));
			 
			 
			TrascrizioneDAO.insert(indice, idd, textarea);
			 
			data.put("id", idd);
			data.put("nomeopera", nome);
			
			data.put("index", indice);
			response.sendRedirect("details?id="+ data.get("id") +"&index="+data.get("index"));

	    }
	    
	   

	    
	        data.put("test", test);
	        String path= null;
	        int index= Integer.parseInt(request.getParameter("index"));
		    int id=Integer.parseInt(request.getParameter("id")) ;
	      try {
	    	  
			Database.connect();
			ResultSet rs= Database.selectRecord("pub", "id_op="+id);
			while( rs.next() ){
				 nome= rs.getString("nome");
			}
			
			Database.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	      
	      /* Prendo immagine da mostrare a video (se esiste) */
	      Database.connect();
	      ResultSet cs= Database.selectRecord("immagini", "indice="+index+"&&id_pub="+id+"");
	      while(cs.next()){
	    	  
	    	path=cs.getString("path");
	    	
	      }
	      
	      /* Prendo TRASCRIZIONE da mostrare a video (se esiste) */
	      ResultSet sss= Database.selectRecord("trascrizioni", "indice="+index+"&&id_pub="+id+"");
	      while(sss.next()){
	    	  
	    	contenuto_editor2=sss.getString("contenuto");
	    	System.out.println(contenuto_editor2);
	    	
	      }
	      
	      data.put("contenuto_editor", contenuto_editor2);
	      data.put("path",path);
	      data.put("id", id);
	      data.put("nomeopera", nome);
	      data.put("index", index);
	      
	      /* Metodo che prende la lista del num di pag di un 
	       *  Opera specifica
	       */
	      
	      List listapagine= new ArrayList();
	      int numero=0;
	      ResultSet pagine= Database.selectRecord("pub","id_op="+id );
	      while(pagine.next()){ numero= pagine.getInt("num_pagine"); }
	      for (int i=0; i<numero; i++){
	    	  listapagine.add(i);
	      }
	      System.out.println(listapagine);
	      
	      data.put("listapagine", listapagine);
	      
	      int max_ind = listapagine.size()-1;      
	       data.put("max_ind", max_ind);
	      
	  FreeMarker.process("backoffice/details.html", data, response, getServletContext());
	  Database.close();	
	
	}

	private void action_error(HttpServletRequest request, HttpServletResponse response) {
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			processRequest(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			processRequest(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
