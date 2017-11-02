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
import biblio.Model.DAO.ImageDAO;
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

public class Opera_Single extends HttpServlet {

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
	   
	      

	     
		
		/* Avanzo alla pagina successiva */

	       if(!isNull(request.getParameter("next"))){	    	     	
	    	
	    	 int idd= Integer.parseInt(request.getParameter("id"));
			 int indice= Integer.parseInt(request.getParameter("index"));
			 
			 data.put("id", idd);
			 data.put("nomeopera", nome);
			 data.put("index", indice+1);
			 
			 response.sendRedirect("opera_single?id="+ data.get("id") +"&index="+data.get("index"));
	    	
	       }
	    
		/* Torno alla pagina precedente */

	       if(!isNull(request.getParameter("prev"))){
	    	
	    	 int idd= Integer.parseInt(request.getParameter("id"));
			 int indice= Integer.parseInt(request.getParameter("index"));
			 
			 
			 data.put("id", idd);
			 data.put("nomeopera", nome);
			 data.put("index", indice-1);
			 
			 response.sendRedirect("opera_single?id="+ data.get("id") +"&index="+data.get("index"));

			 
	    }
	    
	   /* Vado alla pagina relativa all'upload dell'immagine */

	    
	    
	      

	        String path= null;
	        int index= Integer.parseInt(request.getParameter("index"));
		    int id=Integer.parseInt(request.getParameter("id"));
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
	      System.out.println(listapagine);
	      System.out.println(id);
	      
	      
	      data.put("listapagine", listapagine);
	      
	      int max_ind = listapagine.size()-1;      
	       data.put("max_ind", max_ind);
	      
	      try {
	    	    test = DataUtil.getUsername((String) s.getAttribute("username"));
	    	   } catch (Exception e) {
	    	    e.printStackTrace();
	    	   }
	    	   data.put("test", test);
	      
	  FreeMarker.process("opera_single.html", data, response, getServletContext());
	  Database.close();	
	
	}

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
