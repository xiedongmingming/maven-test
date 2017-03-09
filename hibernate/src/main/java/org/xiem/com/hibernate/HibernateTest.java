package org.xiem.com.hibernate;

import java.sql.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.xiem.com.hibernate.object.VersionedAdvertiserAudit;

import gnu.trove.map.hash.TIntObjectHashMap;

public class HibernateTest {

	private SessionFactory sessionFactory = null;
	private Configuration configuration = new Configuration().configure();
	private Session session;
	private Transaction transaction;


	public void init() {
		System.out.println("before");
	}


	public void destroy() {
		System.out.println("after");
	}

	@SuppressWarnings({ "unused", "deprecation" })
	public void test() {

		SessionFactory buildSessionFactory = configuration.buildSessionFactory();

		StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
				.applySettings(configuration.getProperties()).build();

		Properties properties = configuration.getProperties();
		Set<Entry<Object, Object>> entrySet = properties.entrySet();
		for (Entry<Object, Object> entry : entrySet) {
			// System.out.println(entry);
		}

		sessionFactory = configuration.buildSessionFactory(registry);

		session = sessionFactory.openSession();

		transaction = session.beginTransaction();

		News news = new News("java", "SHU", new Date(new java.util.Date().getTime()));

		News load = (News) session.load(News.class, 1);

		System.out.println(load);

		load = (News) session.load(News.class, 1);
		load = (News) session.get(News.class, 1);
		System.out.println(load);

		load.setAuthor("FLUSH TEST...������");

		session.flush();
		// load = (News) session.load(News.class, 4);
		// System.out.println(load);
		// session.save(load);

		transaction.commit();
		session.close();
		sessionFactory.close();
	}

	public void testClear() {
		// session.clear();
	}

	public void testRefresh() {
		// News load = (News) session.load(News.class, 4);
		// session.refresh(load);
	}
	
	public static void main001(String[] args) {

		Locale local = Locale.getDefault();
		System.out.println(local.getLanguage());
		System.out.println(local.getCountry());
		Properties p = new Properties();
		p.setProperty("hibernate.connection.url", "jdbc:mysql://xtradb1.t1.shadc.yosemitecloud.com/yc_core");
		p.setProperty("connection.username", "yc_core");
		p.setProperty("connection.password", "yc_core");

		HibernateFactory.setupCore(p);
		SessionFactory factory = HibernateFactory.core();
		Session session = factory.openSession();

		TIntObjectHashMap<VersionedAdvertiserAudit> idToOrganization = null;
		idToOrganization = new TIntObjectHashMap<>();
		List<VersionedAdvertiserAudit> audits = HibernateUtils.getAllCurrentActive(VersionedAdvertiserAudit.class,
				session, null);
		for (VersionedAdvertiserAudit vAudit : audits) {

			idToOrganization.put(vAudit.getId(), vAudit);

		}

		HibernateUtils.closeQuietly(session);
	}
}