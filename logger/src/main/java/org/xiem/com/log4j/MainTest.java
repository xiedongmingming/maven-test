package org.xiem.com.log4j;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class MainTest {// APACHE的开源项目LOG4J是一个功能强大的日志组件(提供方便的日志记录功能)

	// 官网地址: jakarta.apache.org/log4j

	// JAR名称: log4j-1.2.17.jar

	// LOG4J基本使用方法:http://www.codeceo.com/article/log4j-usage.html

	// LOG4J由三个重要的组件构成:
	// 1、日志信息的优先级:优先级从高到低有OFF、FATAL、ERROR、WARN、 INFO、DEBUG、ALL或者您定义的级别--LOG4J建议只使用四个级别(优先级从高到低分别是ERROR、WARN、INFO、DEBUG)
	// 2、日志信息的输出目的地:日志信息的输出目的地指定了日志将打印到控制台还是文件中等
	// 3、日志信息的输出格式:输出格式则控制了日志信息的显示内容

	// 定义配置文件(其实您也可以完全不使用配置文件而是在代码中配置LOG4J环境)LOG4J支持两种配置文件格式:
	// 1、XML格式的文件
	// 2、JAVA特性文件(键值对)

	// 下面我们介绍使用JAVA特性文件做为配置文件的方法:
	//
	// 1.配置根LOGGER:其语法为:
	// 		log4j.rootLogger = [level], appenderName, appenderName, ...
	// 其中LEVEL是日志记录的优先级,分为OFF、FATAL、ERROR、WARN、INFO、DEBUG、ALL或者您定义的级别.
	// LOG4J建议只使用四个级别,优先级从高到低分别是ERROR、WARN、INFO、DEBUG.
	// APPENDERNAME(自定义名称)就是指日志信息输出到哪个地方(可以同时指定多个输出目的地)
	//
	// 2.配置日志信息输出目的地APPENDER:其语法为:
	// 		log4j.appender.appenderName = fully.qualified.name.of.pender.class
	// 		log4j.appender.appenderName.option1 = value1
	// 		...
	// 		log4j.appender.appenderName.optionN = valueN
	// 其中LOG4J提供的APPENDER有以下几种(对应语法第一条的值):
	// org.apache.log4j.ConsoleAppender 控制台
	// org.apache.log4j.FileAppender 文件
	// org.apache.log4j.DailyRollingFileAppender 每天产生一个日志文件
	// org.apache.log4j.RollingFileAppender 文件大小到达指定尺寸的时候产生一个新的文件
	// org.apache.log4j.WriterAppender 将日志信息以流格式发送到任意指定的地方
	//
	// 3.配置日志信息的格式(布局):其语法为:
	// 		log4j.appender.appenderName.layout = fully.qualified.name.of.layout.class
	// 		log4j.appender.appenderName.layout.option1 = value1
	// 		...
	// 		log4j.appender.appenderName.layout.optionN = valueN
	// 其中LOG4J提供的LAYOUT有以下几种(对应语法第一条的值):
	// org.apache.log4j.HTMLLayout 以HTML表格形式布局
	// org.apache.log4j.PatternLayout 可以灵活地指定布局模式
	// org.apache.log4j.SimpleLayout 包含日志信息的级别和信息字符串
	// org.apache.log4j.TTCCLayout 包含日志产生的时间、线程、类别等等信息
	// LOG4J采用类似C语言中的PRINTF函数的打印格式格式化日志信息打印参数如下:
	// %m 输出代码中指定的消息
	// %p 输出优先级(即DEBUG、INFO、WARN、ERROR、FATAL)
	// %r 输出自应用启动到输出该LOG信息耗费的毫秒数
	// %c 输出所属的类目(通常就是所在类的全名)
	// %t 输出产生该日志事件的线程名
	// %n 输出一个回车换行符(WINDOWS平台为"RN",UNIX平台为"N")
	// %d 输出日志时间点的日期或时间(默认格式为ISO8601也可以在其后指定格式,比如:%d{yyy MMM dd HH:mm:ss,SSS}输出类似:2002年10月18日22:10:28,921)
	// %l 输出日志事件的发生位置(包括类目名、发生的线程以及在代码中的行数.举例:Testlog4.main(TestLog4.java:10)
	//
	// 2.2、在代码中使用LOG4J
	// 1.得到记录器:使用LOG4J第一步就是获取日志记录器(这个记录器将负责控制日志信息)其语法为:
	// 		public static Logger getLogger(String name)
	// 通过指定的名字获得记录器,如果必要的话则为这个名字创建一个新的记录器,NAME一般取本类的名字,比如:
	// 		static Logger logger = Logger.getLogger(ServerWithLog4j.class.getName())
	// 2.读取配置文件:当获得了日志记录器之后第二步将配置LOG4J环境,其语法为:
	// 		BasicConfigurator.configure():自动快速地使用缺省LOG4J环境
	// 		PropertyConfigurator.configure(String configFilename):读取使用JAVA的特性文件编写的配置文件
	// 		DOMConfigurator.configure(Stringfilename):读取XML形式的配置文件
	// 3.插入记录信息(格式化日志信息):当上两个必要步骤执行完毕,您就可以轻松地使用不同优先级别的日志记录语句插入到您想记录日志的任何地方,其语法如下:
	// 		Logger.debug(Object message);
	// 		Logger.info (Object message);
	//		 Logger.warn(Object message);
	// 		Logger.error(Object message);
	// 2.3、日志级别:每个LOGGER都对应了一个日志级别,用来控制日志信息的输出.日志级别从高到低分为:
	// A:off	最高等级(用于关闭所有日志记录)
	// B:fatal	指出每个严重的错误事件将会导致应用程序的退出
	// C:error	指出虽然发生错误事件(但仍然不影响系统的继续运行)
	// D:warn	表明会出现潜在的错误情形
	// E:info	一般和在粗粒度级别上(强调应用程序的运行全程)
	// F:debug	一般用于细粒度级别上(对调试应用程序非常有帮助)
	// G:all	最低等级(用于打开所有日志记录)
	// 上面这些级别是定义在ORG.APACHE.LOG4J.LEVEL类中

	private static Logger logger = Logger.getLogger(MainTest.class);// 获取日志记录器

	public static void main(String[] args) {

		// SRC同级创建并设置配置文件(放在):log4j.properties

		// 路径问题:通过该MAIN函数运行时(即不指定配置文件相关信息(配置文件放在了SRC/MAIN/RESOURCES中))MAVEN可以自动找到该文件并加载
		logger.debug("This is debug message."); // 记录DEBUG级别的信息
		logger.info ("This is info  message."); // 记录INFO级别的信息
		logger.error("This is error message."); // 记录ERROR级别的信息

	}

	public static void Log4jTest() {

		System.out.println("使用默认配置测试>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

		// 会自动搜索配置文件并使用

		logger.debug("This is debug message."); // 记录DEBUG级别的信息
		logger.info ("This is info  message."); // 记录INFO级别的信息
		logger.error("This is error message."); // 记录ERROR级别的信息
		
		System.out.println("使用缺省配置测试>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

		BasicConfigurator.configure();// 自动快速地使用缺省LOG4J环境(也会搜索配置文件)
		
		logger.debug("This is debug message."); // 记录DEBUG级别的信息
		logger.info ("This is info  message."); // 记录INFO级别的信息
		logger.error("This is error message."); // 记录ERROR级别的信息
		
		System.out.println("使用JAVA特性配置>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

		// 路径问题:当自己指定配置文件路径时必须要使用全名称(相对路径的根位置为项目目录)
		PropertyConfigurator.configure("src/main/resources/log4j.properties");// 读取使用JAVA的特性文件编写的配置文件

		logger.debug("this is debug message."); // 记录DEBUG级别的信息
		logger.info ("this is info  message."); // 记录INFO级别的信息
		logger.error("this is error message."); // 记录ERROR级别的信息

		System.out.println("使用XMLS特性配置>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

		// DOMConfigurator.configure("");
	}

}
