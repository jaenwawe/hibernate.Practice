package com.revature.services;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.revature.entities.Professor;
import com.revature.entities.School;
import com.revature.entities.Text;

public class TextService {
	SessionFactory sessionFactory;

	public TextService(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	
	public Text getText(int id) {
		return null;
	}
	
	public Text createText() {
		Text text = new Text();
		text.setPages(100);
		text.setTextWeight(2);
		try(Session session = sessionFactory.openSession()) {
			Transaction tx = session.beginTransaction();
			session.save(text);
			tx.commit();
			return text;
		}
	}
	
	public void giveText(Professor professor, Text text) {
		try(Session session = sessionFactory.openSession()) {
			Transaction tx = session.beginTransaction();
			professor.setText(text);
			session.merge(professor);
			tx.commit();
		}
	
	}
	
}
