package com.revature.services;

import org.hibernate.Hibernate;


import org.hibernate.Session;
import org.hibernate.SessionFactory;



import com.revature.entities.School;


public class SchoolService {
	SessionFactory sessionFactory;

	public SchoolService(SessionFactory sessionFactory) {
		super();
		this.sessionFactory = sessionFactory;
	}
	
	public School getSchool() {
		try(Session session = sessionFactory.openSession()) {
			School school = session.load(School.class, 2);
	
			// Better way to load in a proxy explicitly
			Hibernate.initialize(school);
			Hibernate.initialize(school.getClass());
			return school;			
		}
	}
}
