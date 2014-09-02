package transaction;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import dao.GenericDao;

public class TransactionManager {
	protected EntityManager em;
	protected EntityTransaction tx;

	public TransactionManager() {
		em = createEntityManager();
		tx = em.getTransaction();
		tx.begin();
	}
	
	public void commit() {
		tx.commit();
	}
	
	public void rollback() {
		tx.rollback();
	}
	
	public void close() {
		if (em != null)
			em.close();
	}
	
	@Override
	protected void finalize() throws Throwable {
		close();
	}
	
	@SuppressWarnings("rawtypes")
	public void join(GenericDao dao) {
		dao.setEntityManager(em);
	}
	
	public static EntityManager createEntityManager() {
		return Persistence
				.createEntityManagerFactory("PersonaJpa")
				.createEntityManager(); 
	}
	

}
