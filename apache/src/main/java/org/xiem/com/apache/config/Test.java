package org.xiem.com.apache.config;

import java.util.List;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.XMLConfiguration;

public class Test { // 混合读取二种文件

	// 相关API说明：
	// # PropertiesConfiguration 从一个 property 文件中加载配置 .
	// # XMLConfiguration 从 XML 文件中加载配置信息 .
	// # XMLPropertyListConfiguration 也可以读取 XML 被 Mac OSX 使用变量 .
	// # JNDIConfiguration 利用 jndi 树的一个键值，可以返回一个值，这个值来作为配置信息
	// # BaseConfiguration 访问内存中的一个配置对象 .
	// # HierarchicalConfiguration 一个在内存中的配置对象，这个对象可以处理复杂的数据结构 .
	// # SystemConfiguration 一个利用系统属性的配置信息
	// # ConfigurationConverter 把 java.util.Properties 或者
	// org.apache.collections.commons.ExtendedProperties 转化为一个 Configuration 对象
	//
	// 参考资料：
	// common-configuration读取xml,properties文件
	// http://javamy.iteye.com/blog/252619
	// 使用apache common configuration读取配置文件或修改配置文件
	// http://www.suneca.com/article.asp?id=9

	public static void main(String[] args) throws ConfigurationException {
		CompositeConfiguration config = new CompositeConfiguration();
		config.addConfiguration(new PropertiesConfiguration("main/global.properties"));
		config.addConfiguration(new XMLConfiguration("main/global.xml"));

		List<?> startCriteria = config.getList("start-criteria.criteria");
		int horsepower = config.getInt("horsepower");
		for (int i = 0; i < startCriteria.size(); i++) {
			System.out.println("Role: " + startCriteria.get(i));
		}
		System.out.println(horsepower);
		System.out.println("Speed: " + config.getFloat("speed"));
		System.out.println("Names: " + config.getString("name"));
	}
}