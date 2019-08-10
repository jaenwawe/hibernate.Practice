package com.revature.services;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.revature.entities.Professor;

public class CacheService {
	SessionFactory sessionFactory;

	public CacheService(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	
	/**
	 * L1 Cache - (Level 1 Cache)
	 * The basic cache built into Hibernate.  The L1 cache is associated
	 * with the Session object and caches data within the scope of a Session.
	 * This data is cached for the lifecycle of the Session object for which
	 * it originated.
	 * 
	 * This is essential hibernate behavior. It cannot be disabled, and there
	 * is no way to use Hibernate without it.
	 */
	public void testingTheL1Cache() {
		Professor professora = null;
		
		try(Session session = sessionFactory.openSession()) {
			professora = session.get(Professor.class, 5);
			Professor professorb = session.get(Professor.class, 5);
			Professor professorc = session.load(Professor.class, 5);
			Professor professord = session.get(Professor.class, 5);
			
			System.out.println(professora != null);
			System.out.println(professorb != null);
			System.out.println(professorc != null);
			System.out.println(professord != null);
			
		//	System.out.println(professora.equals(professorb));
			System.out.println(professora == professorc);
		}
		
		try(Session session = sessionFactory.openSession()) {
			Professor newProfessor = session.get(Professor.class, 5);
			System.out.println(professora == newProfessor);
		}
	}
}
