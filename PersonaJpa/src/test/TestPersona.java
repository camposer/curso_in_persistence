package test;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import model.Ordenador;
import model.Persona;

public class TestPersona {
	private EntityManagerFactory emf;
	private EntityManager em;
	private EntityTransaction tx;
	
	public TestPersona() {
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
	
	// INSERT INTO persona(nombre, apellido, altura, fechaNacimiento)
	// VALUES('Nuevo', 'Nuevo', 80, now());
	public void agregar() {
		Persona p = new Persona();
		p.setNombre("Nuevo");
		p.setApellido("Nuevo");
		p.setAltura(80.0);
		p.setFechanacimiento(new Date());

		tx.begin();
		try {
			em.persist(p); // => INSERT
			tx.commit();
		} catch (PersistenceException pe) {
			pe.printStackTrace();
			tx.rollback();
		}
	}
	
	@SuppressWarnings("unchecked")
	public void listar() {
		Query q = em.createNamedQuery("Persona.findAll");
		List<Persona> personas = q.getResultList();
		for (Persona p : personas) {
			System.out.println(p);
			
			List<Ordenador> ordenadores = p.getOrdenadores(); // SELECT * FROM ordenador WHERE persona_id = ?
			if (ordenadores != null) for (Ordenador o : ordenadores) { 
				System.out.println("\t" + o);
			}
		}
	}
	
	public void modificar() {
		Persona p = em.find(Persona.class, 1); // SELECT * FROM persona WHERE id = 1
		p.setNombre("Juanito " + Math.random());
		
		tx.begin();
		try {
			em.merge(p); // => UPDATE
			tx.commit();
		} catch (PersistenceException pe) {
			pe.printStackTrace();
			tx.rollback();
		}
	}

	public void eliminar() {
		Persona p = new Persona();
		p.setNombre("Nuevo");
		p.setApellido("Nuevo");
		p.setAltura(80.0);
		p.setFechanacimiento(new Date());

		tx.begin();
		try {
			em.persist(p); // => INSERT
			System.out.println("Recién insertado: " + p);
			em.remove(p); // => DELETE

			tx.commit();
		} catch (PersistenceException pe) {
			pe.printStackTrace();
			tx.rollback();
		}

	}
	
	public static void main(String[] args) {
		TestPersona tc = new TestPersona();
//		tc.agregar();
//		tc.modificar();
//		tc.eliminar();
		tc.listar();
	}
}
