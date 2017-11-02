package biblio.Servlet;
/**
 * @author marco
 *
 */
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import biblio.Util.Database;
import biblio.Util.FreeMarker;
import biblio.Util.SecurityLayer;

/**
 * Servlet implementation class FileUpload
 */
@WebServlet("/FileUpload")
@MultipartConfig

public class FileUpload extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FileUpload() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    Map<String,Object> data= new HashMap<String,Object>();
	 
	    private static final String UPLOAD_DIRECTORY = "upload";
	    private static final int THRESHOLD_SIZE     = 1024 * 1024 * 3;  // 3MB
	    private static final int MAX_FILE_SIZE      = 1024 * 1024 * 40; // 40MB
	    private static final int MAX_REQUEST_SIZE   = 1024 * 1024 * 50; // 50MB

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	       HttpSession s = SecurityLayer.checkSession(request);

	    	  if (s != null) {
	    		  	
	    		  	String nome= request.getParameter("nome");
	    		  	int idd= Integer.parseInt(request.getParameter("id"));
	    			int indice= Integer.parseInt(request.getParameter("index"));
	   
	    			System.out.println(nome);
	    			System.out.println(idd);
	    			
	    		  	data.put("nomeopera", nome);
	    		  	data.put("index", indice);
	    		  	data.put("id", idd);
	                FreeMarker.process("backoffice/upload2.html", data, response, getServletContext());
	                
	            } else FreeMarker.process("login.html", data, response, getServletContext());
	    }
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Map<String,Object> map= new HashMap<String,Object>();
		Part filePart = request.getPart("nomefile");
		 
	      String nomeFile = getFilename(filePart);
	      System.out.println(nomeFile);
	      
	      String uploadPath = "C:\\Users\\MARCO\\Desktop\\OOP_DEF\\OOP_Biblio\\WebContent\\template\\" + UPLOAD_DIRECTORY +"\\";
	         System.out.println("UPLOAD PATH: " + uploadPath);
	         
	            
	          
	      
         
	      BufferedReader reader = new BufferedReader(new InputStreamReader(filePart.getInputStream()));
	      String line = null;
	      //while ((line = reader.readLine()) != null)
	        //System.out.println(line);
	      
	      String filePath = uploadPath + nomeFile;
	      System.out.println(filePath);
	      System.out.println(data.get("id"));
	      System.out.println(data.get("index"));
	      
          File storeFile = new File(filePath);
          
         
		map.put("path","C:/Users/MARCO/Desktop/OOP_DEF/OOP_Biblio/WebContent/template/" + UPLOAD_DIRECTORY +"/"+nomeFile);
		map.put("id_pub",7);
        map.put("indice",0);
        map.put("revisionato", 0);
        
        try {
			Database.connect();
			Database.insertRecord("immagini", map);
	         Database.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        data.put("messaggio", "Upload effettuato correttamente!");
		 FreeMarker.process("backoffice/upload2.html", data, response, getServletContext());
          
	      
	      
	      
	   }
	
	private static String getFilename(Part part) {
		   for (String cd : part.getHeader("content-disposition").split(";")) {
		      if (cd.trim().startsWith("filename")) {
		         String filename = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
		         return filename.substring(filename.lastIndexOf('/') + 1).substring(filename.lastIndexOf('\\') + 1); // MSIE fix.
		      }
		   }
		 
		   return null;
		}
	
	
	}