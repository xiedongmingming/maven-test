package org.xiem.com.apache.configuration;

import java.util.List;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;

public class XMLConfigurationDemo {

	public static void main(String[] args) throws ConfigurationException {

		Configuration config = new XMLConfiguration(XMLConfigurationDemo.class.getResource("system-config.xml"));
		String ip = config.getString("ip");
		String account = config.getString("account");
		String password = config.getString("password");
		List<?> roles = config.getList("roles.role");
		System.out.println("IP: " + ip);
		System.out.println("Account: " + account);
		System.out.println("Password: " + password);
		for (int i = 0; i < roles.size(); i++) {
			System.out.println("Role: " + roles.get(i));
		}
	}
}
