package org.xiem.com.log4j;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;

public class Log4jTest000 {// 直接使用

	static Logger logger = Logger.getLogger(Log4jTest000.class);

	public static void main(String args[]) {// 完全通过程序完成配置

		SimpleLayout layout = new SimpleLayout();

		FileAppender appenderfile = null;
		ConsoleAppender appenderconsole = null;

		try {
			appenderfile = new FileAppender(layout, "logs/output0.txt", false);
			appenderconsole = new ConsoleAppender(layout);
		} catch (Exception e) {

		}

		logger.addAppender(appenderfile);
		logger.addAppender(appenderconsole);

		logger.setLevel(Level.INFO);

		logger.debug("Here is some DEBUG");
		logger.info ("Here is some INFO ");
		logger.warn ("Here is some WARN ");
		logger.error("Here is some ERROR");
		logger.fatal("Here is some FATAL");
	}

}
