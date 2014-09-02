package test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;

import model.Ordenador;
import model.Persona;

public class TestJQL {
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {
		EntityManager em = Persistence
				.createEntityManagerFactory("PersonaJpa")
				.createEntityManager();
		
		// ORDENADORES
		Query q = em.createQuery("SELECT o FROM Ordenador o");
		List<Ordenador>  ordenadores = q.getResultList();
		if (ordenadores != null) for (Ordenador o : ordenadores) 
			System.out.println(o);
		
		System.out.println();
		// PERSONAS
		List<Persona> personas = em
				.createQuery("FROM Persona p")
				.getResultList();
		if (personas != null) for (Persona p : personas)
			System.out.println(p);
		
		System.out.println();
		// QUERY CON PARAMS
		Date fecha = new SimpleDateFormat("yyyy-MM-dd").parse("1980-01-01");
		q = em.createQuery("FROM Persona p "
				+ "WHERE p.fechanacimiento > :fecha");
		q.setParameter("fecha", fecha);
		
		personas = q.getResultList();
		if (personas != null) for (Persona p : personas)
			System.out.println(p);

		System.out.println();
		// QUERY NATIVA
		q = em.createNativeQuery("SELECT * FROM persona", Persona.class);
		personas = q.getResultList();
		if (personas != null) for (Persona p : personas)
			System.out.println(p);
		
		System.out.println();
		// QUERY NATIVA (JOIN)
		q = em.createNativeQuery("SELECT o.* FROM persona p "
				+ "INNER JOIN ordenador o ON o.persona_id = p.id "
				+ "WHERE p.id = :id", Ordenador.class);
		q.setParameter("id", 1);
		
		ordenadores = q.getResultList();
		if (ordenadores != null) for (Ordenador o : ordenadores)
			System.out.println(o);

		em.close();
	}
}
