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

@WebServlet("/ordenador/Modificar")
public class ModificarServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<String> errores = new ArrayList<String>();
		Integer id = null;
		Integer pId = null;
		
		// Obteniendo parámetros de la petición
		request.setCharacterEncoding("UTF-8");
		String sid = request.getParameter("inputId");
		String nombre = request.getParameter("inputNombre");
		String serial = request.getParameter("inputSerial");
		String spId = request.getParameter("inputPersona");

		// Validaciones
		try {
			id = Integer.parseInt(sid);
		} catch (NumberFormatException e1) {
			errores.add("Id inválido");
		}
		if (nombre.trim().equals("")) // Validando nombre
			errores.add("Nombre inválido"); 
		if (serial.trim().equals("")) // Validando apellido
			errores.add("Serial inválido"); 
		try { // Validando altura
			pId = Integer.parseInt(spId);
		} catch (NumberFormatException e) {
			errores.add("Persona id inválido");
		}
		
		if (errores.size() == 0) { // No hay errores
			// Agregando a la persona
			Persona p = new Persona();
			p.setId(pId);
			
			Ordenador o = new Ordenador();
			o.setId(id);
			o.setNombre(nombre);
			o.setSerial(serial);
			o.setPersona(p);
			
			OrdenadorService os = new OrdenadorService();
			try {
				os.modificarOrdenador(o);
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
