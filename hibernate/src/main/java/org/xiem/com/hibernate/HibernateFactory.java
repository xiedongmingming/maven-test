package org.xiem.com.hibernate;

import java.net.URL;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateFactory {//

	final static String CORE_CONFIG = "/org/xiem/com/hibernate/hibernate_core.xml";

    private static SessionFactory coreFactory = null;

    public static SessionFactory core() {
        if (coreFactory == null) {
            throw new IllegalStateException("core factory not setup yet");
        }
        return coreFactory;
    }

	public static SessionFactory setup(String configFile, final Properties props) {// 创建会话工厂
        
		URL url = HibernateFactory.class.getResource(configFile);

		Configuration config = new Configuration().configure(url);// 创建配置对象--通过配置文件

		if (props != null) {// 属性文件中的属性优先
            config.addProperties(props);
        }

		System.out.println("打印属性之前..................................");
		Properties properties = config.getProperties();
		Set<Entry<Object, Object>> entrySet = properties.entrySet();
		for (Entry<Object, Object> entry : entrySet) {
			System.out.println(entry);
		}
		System.out.println("打印属性之后..................................");

		final ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
				.applySettings(config.getProperties()).build();// 注册服务
        
		return config.buildSessionFactory(serviceRegistry);// 根据服务来获得对应的会话工厂
    }
    public static SessionFactory setup(final Properties props) {
        return setup(CORE_CONFIG, props);
    }
	public static void setupCore(final Properties props) {//
        coreFactory = setup(props);
    }
}