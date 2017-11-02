package biblio.Servlet;
/**
 * @author MARCO
 *
 */
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import static java.util.Objects.isNull;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import biblio.Model.Opera;
import biblio.Model.Utente;
import biblio.Model.DAO.OperaDAO;
import biblio.Model.DAO.UtenteDAO;
import biblio.Util.Database;
import biblio.Util.FreeMarker;
import biblio.Util.SecurityLayer;

/**
 * Servlet implementation class Upload2
 */
@WebServlet("/Upload2")
public class Upload2 extends HttpServlet {

	    Map<String, Object> data = new HashMap<String, Object>();
	   
	    private static final long serialVersionUID = 1L;
	    private static final String UPLOAD_DIRECTORY = "upload";
	    private static final int THRESHOLD_SIZE = 1024 * 1024 * 3;  // 3MB
	    private static final int MAX_FILE_SIZE = 1024 * 1024 * 40; // 40MB
	    private static final int MAX_REQUEST_SIZE = 1024 * 1024 * 50; // 50MB

	    

	    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    	
	    	int id = 0;
	         int index = 0;
	         String nome = "";

	        // checks if the request actually contains upload file
	        Map<String, Object> map = new HashMap<String, Object>();
	        if (!ServletFileUpload.isMultipartContent(request)) {

	            PrintWriter writer = response.getWriter();
	            writer.println("Request does not contain upload data");
	            writer.flush();
	            return;
	        }

	        // Configurazione impostazioni di upload
	        DiskFileItemFactory factory = new DiskFileItemFactory();
	        factory.setSizeThreshold(THRESHOLD_SIZE);
	        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

	        ServletFileUpload upload = new ServletFileUpload(factory);

	        upload.setFileSizeMax(MAX_FILE_SIZE);
	        upload.setSizeMax(MAX_REQUEST_SIZE);

	        //Costruisce il path della cartella dove fare l'upload del file
	        String uploadPath = "C:\\Users\\marco\\Desktop\\progetto OOP\\OOP_Biblio\\WebContent\\template\\" + UPLOAD_DIRECTORY;
	        

	        // creates the directory if it does not exist
	        File uploadDir = new File(uploadPath);

	        if (!uploadDir.exists()) {
	            System.out.println("DENTRO IF-EXIST");

	            uploadDir.mkdir();
	        }

	        try {
	            // parses the request's content to extract file data
	        	List<FileItem> formItems = upload.parseRequest(request);
	            Iterator<FileItem> iter = formItems.iterator();
	            
	          

	            // iterates over form's fields
	            while (iter.hasNext()) {
	                FileItem item = (FileItem) iter.next();
	             

	                String fieldname = item.getFieldName();
	                // processes only fields that are not form fields
	                if (!item.isFormField()) {
	                    String fileName2 = new File(item.getName()).getName();
	                    

	                    String fileName = item.getName();
	                    

	                    String filePath = uploadPath + File.separator + fileName2;
	                    

	                    File storeFile = new File(filePath);
	                    

	                    map.put("path", filePath);
	                    data.put("path","C:/Users/marco/Desktop/progetto OOP/OOP_Biblio/WebContent/template/" + UPLOAD_DIRECTORY +"/"+fileName2);
	                    // saves the file on disk
	                    System.out.println(fileName2);
	                    item.write(storeFile);

	                   
	                    data.put("revisionato", 0);

	                   
	                }
	            
	            else{
               	// String fieldname = item.getFieldName();
             	System.out.println(fieldname);
                 String fieldvalue = item.getString();
                 if (fieldname.equals("id")) 
                 {
                	 id = Integer.parseInt(fieldvalue);
                	 map.put("id", id);
                	 data.put("id_pub",id);
                	 System.out.println(id);
                	 
                 }
                 else if (fieldname.equals("index")) 
                 {
                	 index = Integer.parseInt(fieldvalue);
                	 map.put("index", index);
                	 data.put("indice",index);
                	 System.out.println(index);
                	 
                 }
                 else if (fieldname.equals("nome")) 
                 {
                	 nome = fieldvalue;
                	 map.put("nomeopera", nome);
                	 System.out.println(nome);
                	 
                 }
             }
	            }
	            
	            try {
       				Database.connect();
       				Database.insertRecord("immagini", data);
       		         Database.close();
       	         	 } catch (Exception e) {
       				// TODO Auto-generated catch block
       				e.printStackTrace();
       			}
       	         	 
       			
	            
	        } catch (Exception ex) {
	            request.setAttribute("message", "There was an error: " + ex.getMessage());
	        }
	        
	   
		      
		      
	         	
	         //List<Opera> lista_opere= new ArrayList<Opera>();
      		 //List<Utente> lista_utenti= new ArrayList<Utente>();

      		 /* Inserimento lista in lista_opere */
      		 
      		/* try {
				lista_opere= OperaDAO.returnList();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
      		 try {
				lista_utenti= UtenteDAO.returnListutenti();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
      		 
      		 /* Passaggio di dati alla mappa "data" */
      		 
      		
   	        //data.put("lista_opere", lista_opere);
   	        //data.put("lista_utenti", lista_utenti);
      		response.sendRedirect("details?id="+ data.get("id_pub") +"&index="+data.get("indice"));
	 	                   
		  
		  try {
			Database.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	        

	        
	    }

	    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
	
	    
	    	HttpSession s = SecurityLayer.checkSession(request);

	    	  if (s != null) {
	    		  	
	    		  	String nome= request.getParameter("nome");
	    		  	int idd= Integer.parseInt(request.getParameter("id"));
	    			int indice= Integer.parseInt(request.getParameter("index"));
	   
	    		  	data.put("nomeopera", nome);
	    		  	data.put("index", indice);
	    		  	data.put("id", idd);
	                FreeMarker.process("backoffice/upload.html", data, response, getServletContext());
	                
	            } else FreeMarker.process("login.html", data, response, getServletContext());
	    }
}
