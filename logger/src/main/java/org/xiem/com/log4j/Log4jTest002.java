package org.xiem.com.log4j;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

public class Log4jTest002 {

	static Logger logger = Logger.getLogger(Log4jTest002.class);

	public static void main(String args[]) {

		// 注意: %n is newline

		String pattern = "Milliseconds since program start: %r %n";
		pattern += "Classname of caller: %C %n";
		pattern += "Date in ISO8601 format: %d{ISO8601} %n";
		pattern += "Location of log event: %l %n";
		pattern += "Message: %m %n %n";

		PatternLayout layout = new PatternLayout(pattern);
		ConsoleAppender appender = new ConsoleAppender(layout);

		logger.addAppender(appender);
		logger.setLevel(Level.DEBUG);

		logger.debug("Here is some DEBUG");
		logger.info ("Here is some INFO ");
		logger.warn ("Here is some WARN ");
		logger.error("Here is some ERROR");
		logger.fatal("Here is some FATAL");
	}
}
