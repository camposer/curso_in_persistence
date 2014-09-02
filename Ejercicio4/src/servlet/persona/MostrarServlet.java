package servlet.persona;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Persona;
import service.PersonaService;

@WebServlet("/persona/Mostrar")
public class MostrarServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession sesion = request.getSession();
		List<String> errores = new ArrayList<String>();
		String sid = request.getParameter("id");
		Integer id = null;
		Persona p = null;
		
		try {
			id = Integer.parseInt(sid);
		} catch (NumberFormatException ne) {
			errores.add("Id inválido");
			ne.printStackTrace();
		}
		
		try {
			if (id != null)
				p = new PersonaService().obtenerPersona(id);
		} catch (Exception e) {
			errores.add("Error al consultar la persona en BD");
			e.printStackTrace();
		}

		if (errores.size() > 0)
			sesion.setAttribute("errores", errores);
		else if (p != null)
			sesion.setAttribute("persona", p);
		
		response.sendRedirect("Inicio");
	}

}
