package org.xiem.com.junit.mock;

import java.io.IOException;

public class SystemPropertyMockDemo {
	public String getSystemProperty() throws IOException {
		return System.getProperty("property");
	}
}
