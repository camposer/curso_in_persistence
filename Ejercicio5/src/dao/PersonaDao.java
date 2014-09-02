package dao;

import java.util.List;

import javax.persistence.Query;

import model.Persona;

@SuppressWarnings("unchecked")
public class PersonaDao extends GenericDao<Persona, Integer>{
	public PersonaDao() {
		this(true);
	}
	
	public PersonaDao(boolean autoCommit) {
		super(autoCommit);
	}
	
	public List<Persona> obtenerTodosOrdenadosPorNombreApellido() {
		return em.createQuery("SELECT p FROM Persona p "
				+ "ORDER BY upper(p.nombre) ASC, upper(p.apellido) ASC")
				.getResultList();
	}
	
	public List<Persona> obtenerTodosPorOrdenadorSerial(String serial) {
		Query q = em.createQuery("SELECT p FROM Persona p "
				+ "JOIN p.ordenadores o WHERE o.serial LIKE :serial");
		q.setParameter("serial", "%" + serial + "%");
				
		return q.getResultList();
	}
}