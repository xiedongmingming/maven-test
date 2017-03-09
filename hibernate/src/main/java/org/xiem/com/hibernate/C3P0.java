package org.xiem.com.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class C3P0 {// HIBERNATE整合C3P0实现连接池

	// C3P0是一个开源的JDBC连接池,它实现了数据源和JNDI绑定,支持JDBC3规范和JDBC2的标准扩展



	@SuppressWarnings("unused")
	public static void main(String[] args) {

		Configuration confuguration = new Configuration().configure();

		StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
				.applySettings(confuguration.getProperties());

		StandardServiceRegistry sr = builder.build();

		SessionFactory sessionFactory = confuguration.buildSessionFactory(sr);
	}

}
