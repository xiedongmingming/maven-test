package org.xiem.com.log4j;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Log4jTest004 {

	static Logger logger = Logger.getLogger(Log4jTest004.class);

	public static void main(String args[]) {

		PropertyConfigurator.configure("src/main/resources/plainlog4jconfig.txt");

		logger.debug("Here is some DEBUG");
		logger.info ("Here is some INFO ");
		logger.warn ("Here is some WARN ");
		logger.error("Here is some ERROR");
		logger.fatal("Here is some FATAL");
	}
}
