package org.xiem.com.apache.config;

import java.util.List;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;

public class XmlConfigurationExample {

	public static void main(String[] args) throws Exception, ConfigurationException {

		String resource = "main/global.xml";
		Configuration config = new XMLConfiguration(resource);

		// 只有new一个XMLConfiguration的实例就可以了.
		List<?> startCriteria = config.getList("start-criteria.criteria");
		for (int i = 0; i < startCriteria.size(); i++) {
			System.out.println("Role: " + startCriteria.get(i));
		}
		int horsepower = config.getInt("horsepower");
		System.out.println(horsepower);
	}
}