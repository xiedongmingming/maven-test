package org.xiem.com.hibernate;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

public class HibernateMain {

	public static void main(String[] args) {

		SessionFactory sessionFactory = new AnnotationConfiguration().configure("src/main/resources/hibernate.cfg.xml")
				.buildSessionFactory();

		Session session = sessionFactory.openSession();

		Query query = session.createQuery(" from Team ");

		@SuppressWarnings("unchecked")
		List<Team> teams = query.list();

		System.out.println(" ----- " + teams.size());
		System.out.println(" ----- " + teams.toString());
	}
}
