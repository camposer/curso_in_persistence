package servlet.ordenador;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Ordenador;
import model.Persona;
import service.OrdenadorService;
import service.PersonaService;

@WebServlet("/ordenador/Inicio")
public class InicioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Recuperas los ordenadores de la BD
		List<Ordenador> ordenadores = null;
		try {
			ordenadores = new OrdenadorService()
				.obtenerOrdenadoresOrdenadosPorEdad();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Recuperas las personas de la BD
		List<Persona> personas = null;
		try {
			personas = new PersonaService().obtenerPersonas();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Pasar las personas a la vista
		request.setAttribute("ordenadores", ordenadores);
		request.setAttribute("personas", personas);

		// Redireccionando a la vista
		request
			.getRequestDispatcher("/jsp/ordenador/inicio.jsp")
			.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}
}
