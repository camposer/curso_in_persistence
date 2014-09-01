package servlet.ordenador;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Ordenador;
import service.OrdenadorService;

@WebServlet("/ordenador/Mostrar")
public class MostrarServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession sesion = request.getSession();
		List<String> errores = new ArrayList<String>();
		String sid = request.getParameter("id");
		Integer id = null;
		Ordenador o = null;
		
		try {
			id = Integer.parseInt(sid);
		} catch (NumberFormatException ne) {
			errores.add("Id inválido");
			ne.printStackTrace();
		}
		
		try {
			if (id != null)
				o = new OrdenadorService().obtenerOrdenador(id);
		} catch (Exception e) {
			errores.add("Error al consultar la ordenador en BD");
			e.printStackTrace();
		}

		if (errores.size() > 0)
			sesion.setAttribute("errores", errores);
		else if (o != null)
			sesion.setAttribute("ordenador", o);
		
		response.sendRedirect("Inicio");
	}

}
