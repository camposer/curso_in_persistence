package dao;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;

import transaction.TransactionManager;
import exception.AppDaoException;

public abstract class GenericDao<T, K> {
	protected EntityManager em;
	protected Class<T> clase;
	protected boolean autoCommit;
	
	@SuppressWarnings("unchecked")
	public GenericDao(boolean autoCommit) {
		this.autoCommit = autoCommit;
		
		if (autoCommit) {
			em = TransactionManager.createEntityManager();
		}
		
		// Obtiene en tiempo de ejecución la clase de la tabla (primer genérico de la superclase BaseDao)
		// Utiliza la API de Reflection de Java
		clase = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}
	
	@Override
	protected void finalize() throws Throwable {
		if (autoCommit)
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
			if (autoCommit) {
				tx = em.getTransaction();
				tx.begin();
			}
			
			em.persist(t);
			
			if (tx != null)
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
			if (autoCommit) {
				tx = em.getTransaction();
				tx.begin();
			}
			
			em.merge(t);

			if (tx != null)
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
			if (autoCommit) {
				tx = em.getTransaction();
				tx.begin();
			}
			
			T obj = em.find(clase, id); // => SELECT * FROM clase WHERE id = ?
			em.remove(obj); // => DELETE FROM clase WHERE id = ?
			
			/*
			Query q = em.createQuery("DELETE FROM " + 
					clase.getSimpleName() + 
					" t WHERE t.id = :id");
			q.setParameter("id", id);
			q.executeUpdate();
			*/

			if (tx != null)
				tx.commit();
		} catch (PersistenceException pe) {
			pe.printStackTrace();
			if (tx != null)
				tx.rollback();
			throw new AppDaoException(pe);
		}
	}
	
	
}
