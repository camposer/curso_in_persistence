package servlet.ordenador;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Ordenador;
import model.Persona;
import service.OrdenadorService;
import exception.AppServiceException;

@WebServlet("/ordenador/Agregar")
public class AgregarServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<String> errores = new ArrayList<String>();
		Integer pId = null;
		
		// Obteniendo parámetros de la petición
		request.setCharacterEncoding("UTF-8");
		String nombre = request.getParameter("inputNombre");
		String serial = request.getParameter("inputSerial");
		String spId = request.getParameter("inputPersona");

		// Validaciones
		if (nombre.trim().equals("")) // Validando nombre
			errores.add("Nombre inválido"); 
		if (serial.trim().equals("")) // Validando serial
			errores.add("Serial inválido"); 
		try { // Validando personaId
			pId = Integer.parseInt(spId);
			
			if (pId <= 0)
				throw new Exception();
		} catch (Exception e) {
			errores.add("Persona id inválida");
		}
		
		if (errores.size() == 0) { // No hay errores
			// Agregando el ordenador
			Persona p = new Persona();
			p.setId(pId);
			
			Ordenador o = new Ordenador();
			o.setNombre(nombre);
			o.setSerial(serial);
			o.setPersona(p);
			
			OrdenadorService os = new OrdenadorService();
			try {
				os.agregarOrdenador(o);
			} catch (AppServiceException e) {
				errores.add("Error de acceso a datos");
				e.printStackTrace();
			}
		}
		
		if (errores.size() > 0) {
			request
				.getSession()
				.setAttribute("errores", errores);
		}
		
		// Redireccionando (ejecuta el cliente!!!)
		response.sendRedirect("Inicio"); 
	}
}
