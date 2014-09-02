package dao;

import java.util.List;

import model.Ordenador;

public class OrdenadorDao extends GenericDao<Ordenador, Integer> {
	@SuppressWarnings("unchecked")
	public List<Ordenador> obtenerTodosOrdenadosPorEdad() {
		return em
				.createQuery("FROM Ordenador o ORDER BY "
						+ "o.persona.fechanacimiento DESC")
				.getResultList();
	}
}
