package service;

import java.util.ArrayList;
import java.util.List;

import model.Ordenador;
import model.Persona;
import transaction.TransactionManager;
import dao.OrdenadorDao;
import dao.PersonaDao;
import exception.AppDaoException;
import exception.AppServiceException;

public class PersonaService {

	public List<Persona> obtenerPersonasOrdenadasPorNombreApellido() {
		try {
			return new PersonaDao()
				.obtenerTodosOrdenadosPorNombreApellido();
		} catch (AppDaoException e) {
			throw new AppServiceException(e);
		}
	}

	public List<Persona> obtenerPersonas() {
		try {
			return new PersonaDao().obtenerTodos();
		} catch (AppDaoException e) {
			throw new AppServiceException(e);
		}
	}

	public void agregarPersona(Persona p, Ordenador o ) {
		List<Ordenador> ordenadores = new ArrayList<Ordenador>();
		ordenadores.add(o);
		p.setOrdenadores(ordenadores);
		
		agregarPersona(p);
	}
	
	public void agregarPersona(Persona p) {
		TransactionManager tm = null;
		try {
			tm = new TransactionManager();
			PersonaDao personaDao = new PersonaDao(false);
			OrdenadorDao ordenadorDao = new OrdenadorDao(false);
			tm.join(personaDao);
			tm.join(ordenadorDao);
			
			// Agregar persona
			personaDao.agregar(p);
			
			// Agregar ordenador
			List<Ordenador> ordenadores = p.getOrdenadores();
			if (ordenadores != null) for (Ordenador o : ordenadores) {
				o.setPersona(p);
				ordenadorDao.agregar(o);
			}
			
			tm.commit();
		} catch (AppDaoException e) {
			if (tm != null)
				tm.rollback();
			throw new AppServiceException(e);
		}
	}

	public Persona obtenerPersona(Integer id) {
		try {
			return new PersonaDao().obtener(id);
		} catch (AppDaoException e) {
			throw new AppServiceException(e);
		}
	}

	public void modificarPersona(Persona p) {
		// TODO Validar si la persona existe en BD
		try {
			new PersonaDao().modificar(p);
		} catch (AppDaoException e) {
			throw new AppServiceException(e);
		}
	}

	public void eliminarPersona(Integer id) {
		// TODO Validar si la persona existe en BD
		TransactionManager tm = null;
		try {
			tm = new TransactionManager(); // new em y tx.begin()
			PersonaDao personaDao = new PersonaDao(false); // El EntityManager no lo maneja el DAO (lo maneja un externo)
			OrdenadorDao ordenadorDao = new OrdenadorDao(false); // em = null
			tm.join(personaDao); // em = tm.getEntityManager()
			tm.join(ordenadorDao);
			
			// Buscando la persona
			Persona p = personaDao.obtener(id);

			// Eliminando los ordenadores de la persona
			List<Ordenador> ordenadores = p.getOrdenadores();
			if (ordenadores != null) for (Ordenador o : ordenadores) 
				ordenadorDao.eliminar(o.getId());

			// Eliminando la persona
			personaDao.eliminar(id); 
			
			tm.commit();
		} catch (AppDaoException e) {
			if (tm != null)
				tm.rollback();
			throw new AppServiceException(e);
		}
	}
	
}
