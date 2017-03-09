package org.xiem.com.log4j;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

public class Log4jTest003 {// 使用配置文件(都直接放到项目文件夹下)

	static Logger logger = Logger.getLogger(Log4jTest003.class);

	public static void main(String args[]) {

		DOMConfigurator.configure("src/main/resources/log4jconfig0.xml");

		logger.debug("Here is some DEBUG");
		logger.info ("Here is some INFO ");
		logger.warn ("Here is some WARN ");
		logger.error("Here is some ERROR");
		logger.fatal("Here is some FATAL");

    }
}