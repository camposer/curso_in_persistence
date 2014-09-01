package dao;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;

import exception.AppDaoException;

public abstract class GenericDao<T, K> {
	protected EntityManager em;
	protected Class<T> clase;
	
	@SuppressWarnings("unchecked")
	public GenericDao() {
		em = Persistence
				.createEntityManagerFactory("PersonaJpa")
				.createEntityManager();
		
		// Obtiene en tiempo de ejecución la clase de la tabla (primer genérico de la superclase BaseDao)
		// Utiliza la API de Reflection de Java
		clase = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
	@Override
	protected void finalize() throws Throwable {
		em.close();
	}
	
	@SuppressWarnings("unchecked")
	public List<T> obtenerTodos() {
		try {
			return 	em
						.createQuery("SELECT t FROM " + 
								clase.getSimpleName() + " t")
						.getResultList();
		} catch (PersistenceException pe) {
			pe.printStackTrace();
			throw new AppDaoException(pe);
		}
	}

	public void agregar(T t) {
		EntityTransaction tx = null;
		try {
			tx = em.getTransaction();
			tx.begin();
			
			em.persist(t);
			
			tx.commit();
		} catch (PersistenceException pe) {
			pe.printStackTrace();
			if (tx != null)
				tx.rollback();
			throw new AppDaoException(pe);
		}
	}

	public T obtener(K id) {
		try {
			return em.find(clase, id);
		} catch (PersistenceException pe) {
			pe.printStackTrace();
			throw new AppDaoException(pe);
		}
	}

	public void modificar(T t) {
		EntityTransaction tx = null;
		try {
			tx = em.getTransaction();
			tx.begin();
			
			em.merge(t);
			
			tx.commit();
		} catch (PersistenceException pe) {
			pe.printStackTrace();
			if (tx != null)
				tx.rollback();
			throw new AppDaoException(pe);
		}
	}

	public void eliminar(K id) {
		EntityTransaction tx = null;
		try {
			tx = em.getTransaction();
			tx.begin();
			
			T t = em.find(clase, id);
			em.remove(t);
			
			tx.commit();
		} catch (PersistenceException pe) {
			pe.printStackTrace();
			if (tx != null)
				tx.rollback();
			throw new AppDaoException(pe);
		}
	}
	
}
