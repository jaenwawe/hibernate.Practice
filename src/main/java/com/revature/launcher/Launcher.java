package com.revature.launcher;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.StringType;

import com.revature.entities.Professor;
import com.revature.entities.School;
import com.revature.entities.Text;
import com.revature.services.TextService;
import com.revature.services.CacheService;
import com.revature.services.SchoolService;
public class Launcher {
	// generation code: alt+shift+s
	// format code: ctrl+shift+f
	// import: ctrl+shift+o
	static SessionFactory sessionFactory;
	
	public static void main(String[] args) {
		sessionFactory = configure();
		SchoolService professorService = new SchoolService(sessionFactory);
		TextService textService = new TextService(sessionFactory);
		CacheService cacheService = new CacheService(sessionFactory);
		
		cacheService.testingTheL1Cache();
		
	}
	
	private static void nativeQuerySample() {
		try(Session session = sessionFactory.openSession()) {
			String sql = "SELECT * FROM Professors WHERE favorite_food = :favoriteFood";
			List<Professor> Professors = session.createNativeQuery(sql)
			.setParameter("favoriteFood", "Salmon")
			.addEntity(Professor.class)
			.getResultList();
			System.out.println(Professors);
		}
	}

	private static void namedQuerySample() {
		try(Session session = sessionFactory.openSession()) {
			List<Professor> Professors = session.getNamedQuery("favoriteFoodQuery")
				.setParameter("favoriteClass", "trout")
				.list();
			System.out.println(Professors);
		}
	}

	/*
	 * HQL is Hibernate Query Language
	 * HQL is a Domain Specific Language (DSL) for Hibernate to query
	 * a database based on the Entity definition rather than Table definition
	 * 
	 * As a result, we reference classes and fields, rather than tables and 
	 * columns.
	 */
	private static List<Professor> sampleHQLQuery(String breed) {
		try(Session session = sessionFactory.openSession()) {
//			String hql = "select b from Professor b WHERE b.school LIKE :school";
			String hql = "from Professor b WHERE b.school like : school";
			School science = null;
			List<Professor> Professors = session.createQuery(hql, Professor.class)
					.setParameter("school", science, StringType.INSTANCE)
					.list();
			return Professors;
		}
	}

	/*
	 * Criteria - It's Gross
	 *
	 * A purely object oriented way to query your database. The lack of a
	 * string being used to define the query means that your development tools
	 * can give you feedback when the syntax is incorrect.
	 * 
	 * Because Criteria uses builders, we can pass the builder around to make
	 * queries in a more piecemeal fashion.
	 * 
	 * Session.getCriteriaBuilder() to start a criteria query
	 * CriteriaBuilder & CriteriaQuery & Query interfaces
	 * 
	 * 
	 */
	private static List<Professor> sampleCriteriaQuery(String favoriteFood) {
		try(Session session = sessionFactory.openSession()) {
			CriteriaBuilder cb = session.getCriteriaBuilder();
			CriteriaQuery<Professor> ProfessorQuery = cb.createQuery(Professor.class);
			Root<Professor> root = ProfessorQuery.from(Professor.class);
			
			ProfessorQuery.select(root)
				.where(cb.equal(root.get("favoriteClass"), favoriteFood));

			Query query = session.createQuery(ProfessorQuery);
			List<Professor> results = (List<Professor>) query.getResultList();
			return results;
			
		}
	}
	
	private static void createProfessorFamily() {
		try(Session session = sessionFactory.openSession()) {
			Transaction tx = session.beginTransaction();
			
			Professor ProfessorA = session.get(Professor.class, 4);
			Professor ProfessorB = session.get(Professor.class, 5);
			Professor ProfessorC = session.get(Professor.class, 6);
			Professor ProfessorD = session.get(Professor.class, 7);
			
			ProfessorA.getSchools().add(ProfessorC);
			ProfessorA.getSchools().add(ProfessorD);
			ProfessorB.getSchools().add(ProfessorC);
			ProfessorB.getSchools().add(ProfessorD);
			
			tx.commit();
		}
	}
	
	private static void putProfessorsInSchool() {
		try(Session session = sessionFactory.openSession()) {
			Transaction tx = session.beginTransaction();
			School school  = new School();
			school.setStudentSize(40);
			school.setHasAuditorium(true);
			school.setStaff(new ArrayList());
			session.save(school);
			
			Professor professorA = session.get(Professor.class, 4);
			school.getStaff().add(professorA);
			
			Professor ProfessorB = session.get(Professor.class, 5);
			school.getStaff().add(ProfessorB);
			
			/**
			 * Automatic dirty checking
			 * Hibernate feature that allows it to automatically persist
			 * changes to persistent context objects when a transaction/session
			 * ends. This is possible because objects in the persistent context
			 * are being actively tracked by Hibernate.
			 */
			
			tx.commit();
		}
	}

	public static void deleteAProfessor(Professor Professor) {
		try(Session session = sessionFactory.openSession()) {
			Transaction tx = session.beginTransaction();
			session.delete(Professor);
			tx.commit();
		}
	}

	public static void createAProfessor(String ProfessorSmile) {
		School science = new School();
		Professor professor = new Professor();
		professor.setSchool(science);
		professor.setFavoriteClass("Science");
		
		
		// Using a try block to autoclose the session object
		try(Session session = sessionFactory.openSession()) {
			Transaction tx = session.beginTransaction();
			session.save(professor);
			tx.commit();
		}
		System.out.println(professor);
	}
	
	public static Professor getAProfessor(int id) {
		try(Session session = sessionFactory.openSession()) {
			return session.get(Professor.class, id);
		}
	}
	
	public static void updateAProfessor(Professor professor) {
		professor.setFavoriteClass("Science");
		
		// Try-With-Resources - Autocloses the Autocloseable variable declared
		// within the try () parenthesis
		try(Session session = sessionFactory.openSession()) {
			Transaction tx = session.beginTransaction();
			Professor professorCopy = session.get(Professor.class, professor.getId());
			System.out.println(professorCopy);
			Professor professorC = (Professor) session.merge(professor);
			System.out.println(professorCopy);
			tx.commit();
			System.out.println(professorC == professorCopy);
		}
	}
	
	public static SessionFactory configure() {
		// Configuration is one of the primary interfaces of Hibernate
		
		// Builder pattern
		Configuration configuration = new Configuration()
			.configure() // Loads the configuration from hibernate.cfg.xml
			.addAnnotatedClass(Professor.class)
			.addAnnotatedClass(School.class)
			.addAnnotatedClass(Text.class); 
//			.setProperty("hibernate.connection.username", System.getenv("DB_PASSWORD")); 
			// Used to set property values programmatically
			
			
			
		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
				.applySettings(configuration.getProperties()).build();
		return configuration.buildSessionFactory(serviceRegistry);
	}
}
