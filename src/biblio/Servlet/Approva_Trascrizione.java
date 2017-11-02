package biblio.Servlet;

import static biblio.Util.DataUtil.crypt;
import static java.util.Objects.isNull;

import java.io.IOException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import biblio.Model.Opera;
import biblio.Model.DAO.TrascrizioneDAO;
import biblio.Util.DataUtil;
import biblio.Util.Database;
import biblio.Util.FreeMarker;
import biblio.Util.SecurityLayer;
/**
 * @author marco
 *
 */
public class Approva_Trascrizione extends HttpServlet {

	Map<String, Object> data = new HashMap<String, Object>();

	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession s = SecurityLayer.checkSession(request);

		/* Mette la trascrizione tra i validati tramite un booleano */
		
		if (!isNull(request.getParameter("valida"))) {
			int idd = Integer.parseInt(request.getParameter("id"));
			int indice = Integer.parseInt(request.getParameter("index"));

			data.put("id", idd);
			data.put("index", indice);

			TrascrizioneDAO.valida(indice, idd);

			response.sendRedirect("details?id=" + data.get("id") + "&index=" + data.get("index"));
		}

		/* Elimina trascrizione relativa all'indice corrente  */
		if (!isNull(request.getParameter("elimina"))) {
			int idd = Integer.parseInt(request.getParameter("id"));
			int indice = Integer.parseInt(request.getParameter("index"));

			data.put("id", idd);
			data.put("index", indice);

			TrascrizioneDAO.delete(indice, idd);

			response.sendRedirect("details?id=" + data.get("id") + "&index=" + data.get("index"));
		}
		
		FreeMarker.process("backoffice/trascrizione.html", data, response, getServletContext());
		
	}

	/**
	 * Caricamento pagina di Home
	 *
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 * @throws ServletException
	 *             if a servlet-specific error occurs
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		try {
			processRequest(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			processRequest(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}