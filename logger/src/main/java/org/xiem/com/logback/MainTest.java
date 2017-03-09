package org.xiem.com.logback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainTest {// SLF4J+LOGBACK

	// 参考资料: http://www.cppblog.com/fwxjj/archive/2012/08/16/187345.html

	// 需要用到的包:
	// logback-core
	// logback-classic
	// logback-access

	// slf4j-api

	private static Logger log = LoggerFactory.getLogger(MainTest.class);

	public static void main(String[] args) {

		log.debug("TestB-debug");
		log.info ("TestB-info ");
		log.warn ("TestB-warn ");
		log.error("TestB-error");
	}

}
