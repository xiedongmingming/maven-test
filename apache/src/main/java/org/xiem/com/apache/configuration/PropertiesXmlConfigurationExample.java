package org.xiem.com.apache.configuration;

import java.net.URL;
import java.util.List;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationFactory;

@SuppressWarnings("deprecation")
public class PropertiesXmlConfigurationExample {

	public static void main(String[] args) throws Exception {

		ConfigurationFactory factory = new ConfigurationFactory();

		URL configURL = PropertiesXmlConfigurationExample.class.getResource("additional-xml-configuration.xml");

		factory.setConfigurationURL(configURL);

		Configuration config = factory.getConfiguration();

		List<?> startCriteria = config.getList("start-criteria.criteria");

		for (int i = 0; i < startCriteria.size(); i++) {
			System.out.println("Role: " + startCriteria.get(i));
		}

		int horsepower = config.getInt("horsepower");

		System.out.println("Horsepower: " + horsepower);

		System.out.println("Speed: " + config.getFloat("speed"));
		System.out.println("Names: " + config.getString("name"));

	}
}