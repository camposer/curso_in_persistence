package dao;

import java.util.List;

import model.Persona;

public class PersonaDao extends GenericDao<Persona, Integer>{
	@SuppressWarnings("unchecked")
	public List<Persona> obtenerPersonasOrdenadorPorEdad() {
		// FIXME Agregar query válida!
		return em.createQuery("...").getResultList();
	}
}