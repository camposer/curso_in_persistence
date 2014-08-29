package servlet.persona;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import exception.AppServiceException;
import model.Persona;
import service.PersonaService;

@WebServlet("/persona/Modificar")
public class ModificarServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<String> errores = new ArrayList<String>();
		Integer id = null;
		Date fecha = null;
		Float altura = null;
		
		// Obteniendo parámetros de la petición
		request.setCharacterEncoding("UTF-8");
		String sid = request.getParameter("inputId");
		String nombre = request.getParameter("inputNombre");
		String apellido = request.getParameter("inputApellido");
		String sfecha = request.getParameter("inputFecha");
		String saltura = request.getParameter("inputAltura");

		// Validaciones
		try {
			id = Integer.parseInt(sid);
		} catch (NumberFormatException e1) {
			errores.add("Id inválido");
		}
		if (nombre.trim().equals("")) // Validando nombre
			errores.add("Nombre inválido"); 
		if (apellido.trim().equals("")) // Validando apellido
			errores.add("Apellido inválido"); 
		try { // Validando fecha
			fecha = new SimpleDateFormat("yyyy-MM-dd").parse(sfecha);
		} catch (ParseException e) {
			errores.add("Fecha inválida");
		}
		try { // Validando altura
			altura = Float.parseFloat(saltura);
		} catch (NumberFormatException e) {
			errores.add("Altura inválida");
		}
		
		if (errores.size() == 0) { // No hay errores
			// Agregando a la persona
			Persona p = new Persona(nombre, apellido, altura, fecha);
			p.setId(id);
			PersonaService ps = new PersonaService();
			try {
				ps.modificarPersona(p);
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
