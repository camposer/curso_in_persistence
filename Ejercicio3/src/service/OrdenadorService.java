package service;

import java.util.List;

import model.Ordenador;
import dao.OrdenadorDao;
import exception.AppDaoException;
import exception.AppServiceException;

public class OrdenadorService {

	public List<Ordenador> obtenerOrdenadores() {
		try {
			return new OrdenadorDao().obtenerTodos();
		} catch (AppDaoException e) {
			throw new AppServiceException(e);
		}
	}

	public void agregarOrdenador(Ordenador o) {
		try {
			new OrdenadorDao().agregar(o);
		} catch (AppDaoException e) {
			throw new AppServiceException(e);
		}
	}

	public Ordenador obtenerOrdenador(Integer id) {
		try {
			return new OrdenadorDao().obtener(id);
		} catch (AppDaoException e) {
			throw new AppServiceException(e);
		}
	}

	public void modificarOrdenador(Ordenador o) {
		// TODO Validar si la persona existe en BD
		try {
			new OrdenadorDao().modificar(o);
		} catch (AppDaoException e) {
			throw new AppServiceException(e);
		}
	}

	public void eliminarOrdenador(Integer id) {
		// TODO Validar si la persona existe en BD
		try {
			new OrdenadorDao().eliminar(id);
		} catch (AppDaoException e) {
			throw new AppServiceException(e);
		}
	}
	
}
