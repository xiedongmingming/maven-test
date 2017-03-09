package org.xiem.com.server;

public class MainServer {

	// LOGBACK.XML放在SRC根目录下
	//
	// 		1.LOGBACK首先会试着查找LOGBACK.GROOVY文件;
	// 		2.当没有找到时继续试着查找LOGBACK-TEST.XML文件;
	// 		3.当没有找到时继续试着查找LOGBACK.XML文件;
	// 		4.如果仍然没有找到则使用默认配置(打印到控制台)
	//
	// private static final Logger logger = LoggerFactory.getLogger(LogDemo.class);
	// public static void main(String[] args) {
	// 		LoggerContext lc = (LoggerContext)LoggerFactory.getILoggerFactory();
	// 		JoranConfigurator configurator = new JoranConfigurator();
	// 		configurator.setContext(lc);
	// 		lc.reset();
	// 		try {
	// 			configurator.doConfigure("src/com/bag/resources/logback-log.xml");
	// 		} catch (JoranException e) {
	// 			e.printStackTrace();
	// 		}
	// 		StatusPrinter.printInCaseOfErrorsOrWarnings(lc);
	// 		System.out.println("===================");
	// 		logger.debug("Hello {}","debug message");
	// }

	// static {// 手动加载配置文件
	// 		// 可以为绝对路径: E:\\eclipse-jee-mars-2-win32\\eclipse\\workspace\\JettyServer\\src\\org\\xiem\\com\\logback.xml
	// 		// 下面是相对路径: \\logback.xml
	// 		File logbackFile = new File("E:\\eclipse-jee-mars-2-win32\\eclipse\\workspace\\JettyServer\\src\\org\\xiem\\com\\logback.xml");
	//
	// 		if (logbackFile.exists()) {
	//
	// 			LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
	//
	// 			JoranConfigurator configurator = new JoranConfigurator();// 这个类不能引用错误: import ch.qos.logback.classic.joran.JoranConfigurator;
	//
	// 			configurator.setContext(lc);
	//
	// 			lc.reset();
	// 			try {
	// 				configurator.doConfigure(logbackFile);
	// 			} catch (JoranException e) {
	// 				e.printStackTrace(System.err);
	// 				System.exit(-1);
	// 			}
	// 		}
	// 	}

	public static void main(String[] args) throws Exception {
		

	}

}
