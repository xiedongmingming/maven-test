package org.xiem.com.slf4j;

public class MainTest {

	// 简单日记门面(SIMPLE LOGGING FACADE FOR JAVA)SLF4J是为各种LOGGINGAPIS提供一个简单统一的接口,从而使得最终用户能够在部署的时候配置自己希望的LOGGINGAPIS实现.
	
	// JAR包:
	
	// LOGGINGAPI实现既可以选择直接实现SLF4J接口的LOGGINGAPIS如: NLOG4J、SimpleLogger
	// 也可以通过SLF4J提供的API实现来开发相应的适配器如: Log4jLoggerAdapter、JDK14LoggerAdapter
	// 在SLF4J发行版本中包含了几个JAR包如:
	// slf4j-nop.jar
	// slf4j-simple.jar
	// slf4j-log4j12.jar
	// slf4j-log4j13.jar
	// slf4j-jdk14.jar
	// slf4j-jcl.jar
	// 通过这些JAR文件可以使编译期与具体的实现脱离.或者说可以灵活的切换

	// 官方的网站: http://www.slf4j.org/manual.html

	// 为何使用SLF4J?
	// 我们在开发过程中可能使用各种LOG,每个LOG有不同的风格、布局,如果想灵活的切换,那么SLF4J是比较好的选择.
	// 如何使用SLF4J?
	// 下边一段程序是经典的使用SLF4J的方法(Wombat.java).

	// 下边介绍一下运行上边程序的过程:
	// 1、编译上边的程序需要在CLASSPATH中加入文件:slf4j-api-1.4.1.jar
	// 2、运行时需要在CLASSPATH中加上文件(这个是SIMPLELOG风格):slf4j-simple-1.4.1.jar
	// 3、切换(JDK14的LOG风格):如果想切换到JDK14的LOG的风格只需要把SLF4J-SIMPLE-1.4.1.JAR从CLASSPATH中移除同时在CLASSPATH中加入SLF4J-JDK14-1.4.1.JAR
	// 4、再次切换到LOG4J:同样移除SLF4J-JDK14-1.4.1.JAR,加入SLF4J-LOG4J12-1.4.1.JAR,同时加入LOG4J-1.2.X.JAR和LOG4J.PROPERTIES

	public static void main(String[] args) {

	}

}
