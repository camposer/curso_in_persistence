package service;

import java.util.List;

import dao.PersonaDao;
import exception.AppDaoException;
import exception.AppServiceException;
import model.Persona;

public class PersonaService {

	public List<Persona> obtenerPersonas() {
		try {
			return new PersonaDao().obtenerTodos();
		} catch (AppDaoException e) {
			throw new AppServiceException(e);
		}
	}

	public void agregarPersona(Persona p) {
		try {
			new PersonaDao().agregar(p);
		} catch (AppDaoException e) {
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
		try {
			new PersonaDao().eliminar(id);
		} catch (AppDaoException e) {
			throw new AppServiceException(e);
		}
	}
	
}
