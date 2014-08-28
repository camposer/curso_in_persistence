package test;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import model.Ordenador;
import model.Persona;

public class TestOrdenador {
	private EntityManagerFactory emf;
	private EntityManager em;
	private EntityTransaction tx;
	
	public TestOrdenador() {
		// Obteniendo la factoría de EntityManagers
		emf = Persistence.createEntityManagerFactory("PersonaJpa");
		// Obteniendo la conexión (sesión)
		em = emf.createEntityManager();
		// Obteniendo la transacción dentro de la sesión
		tx = em.getTransaction();
	}
	
	@Override
	protected void finalize() throws Throwable {
		em.close();
	}
	
	public void agregar() {
		Persona p = em.find(Persona.class, 80); // SELECT * FROM persona WHERE id = 1
		
		Ordenador o = new Ordenador();
		o.setNombre("Ordenador Nuevo");
		o.setSerial("8967782");
		o.setPersona(p);

		tx.begin();
		try {
			em.persist(o); // => INSERT
			tx.commit();
		} catch (PersistenceException pe) {
			pe.printStackTrace();
			tx.rollback();
		}
	}
	
	@SuppressWarnings("unchecked")
	public void listar() {
		Query q = em.createNamedQuery("Ordenador.findAll");
		List<Ordenador> ordenadores = q.getResultList();
		for (Ordenador o : ordenadores) {
			System.out.println(o);
		}
	}
	
	public void modificar() {
	}

	public void eliminar() {
	}
	
	public static void main(String[] args) {
		TestOrdenador tc = new TestOrdenador();
		tc.agregar();
//		tc.modificar();
//		tc.eliminar();
		tc.listar();
	}
}
