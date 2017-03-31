package org.xiem.com.apache.configuration;

import java.util.List;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

public class PropertiesConfigurationDemo {// 读取PROPERTIES文件的方式

	public static void main(String[] args) throws ConfigurationException {

		Configuration config = new PropertiesConfiguration(PropertiesConfigurationDemo.class.getResource("system-config.properties"));

		String ip = config.getString("ip");
		System.out.println(ip);

		String[] colors = config.getStringArray("colors.pie");
		for (int i = 0; i < colors.length; i++) {
			System.out.println(colors[i]);
		}

		List<?> colorList = config.getList("colors.pie");
		for (int i = 0; i < colorList.size(); i++) {
			System.out.println(colorList.get(i));
		}
	}
}
