package servlet.persona;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Persona;
import service.PersonaService;

@WebServlet("/persona/Inicio")
public class InicioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Recuperas las personas de la BD
		List<Persona> personas = null;
		try {
			personas = new PersonaService().obtenerPersonas();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Pasar las personas a la vista
		request.setAttribute("personas", personas);
		
		// Redireccionando a la vista
		request
			.getRequestDispatcher("/jsp/persona/inicio.jsp")
			.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}
}
