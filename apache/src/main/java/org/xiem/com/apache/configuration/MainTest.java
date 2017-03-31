package org.xiem.com.apache.configuration;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Properties;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class MainTest {// JAVA加载配置文件的方式

	public static void main(String[] args) throws IOException {
		Test0000();
	}

	// public class Mytest {
	//
	// private static ApplicationContext applicationContext;
	//
	// public static void main(String[] args) throws Exception {
	//
	// //获取SPRING上下文
	// applicationContext = new
	// ClassPathXmlApplicationContext("applicationContext-config.xml");
	//
	// CompositeConfiguration configuration = new CompositeConfiguration();
	//
	// XMLConfiguration config =
	// (XMLConfiguration)applicationContext.getBean("xmlConfiguration");
	// configuration.addConfiguration(config);
	//
	// String value = configuration.getString("test.url");
	// System.out.println(value);//运行结果http://www.baidu.com
	// }
	// }
	//
	// applicationContext-config.xml
	// <beans xmlns="http://www.springframework.org/schema/beans"
	// xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	// xsi:schemaLocation="http://www.springframework.org/schema/beans
	// http://www.springframework.org/schema/beans/spring-beans.xsd">
	//
	// <bean id="xmlConfiguration"
	// class="org.apache.commons.configuration.XMLConfiguration">
	// <constructor-arg index="0" type="Java.net.URL"
	// value="classpath:local-app.xml"/>
	// </bean>
	// </beans>
	//
	// local-app.xml
	// <?xml version="1.0" encoding="UTF-8"?>
	// <appconfig>
	// <test>
	// <url>http://www.baidu.com</url>
	// </test>
	// </appconfig>
	public static void Test0000() throws IOException {// 使用原生方式读取配置文件

		System.out.println("***********************************************************************");
		System.out.println("文件系统加载:");
		InputStream in = new FileInputStream("config.properties");// 相对于项目的根目录
		Properties p = new Properties();
		p.load(in);
		System.out.println("测试: " + p.getProperty("ip"));// 测试: 127.0.0.1

		System.out.println("***********************************************************************");
		System.out.println("类加载方式:");

		System.out.println("***********************************************************************");
		System.out.println("与类同级目录:");
		InputStream inA = MainTest.class.getResourceAsStream("config.properties");
		byte[] byteA = new byte[1024];
		int nA;
		while ((nA = inA.read(byteA)) != -1) {
			System.out.println("测试: " + new String(byteA, 0, nA));// 测试:
																	// ip=127.0.0.2
		}

		System.out.println("***********************************************************************");
		System.out.println("在类的下一级目录:");
		InputStream inB = MainTest.class.getResourceAsStream("resource/config.properties");
		byte[] byteB = new byte[1024];
		int nB;
		while ((nB = inB.read(byteB)) != -1) {
			System.out.println("测试: " + new String(byteB, 0, nB));// 测试:
																	// ip=127.0.0.3
		}

		System.out.println("***********************************************************************");
		System.out.println("指定加载资源配置文件的CLASSES相对路径:");
		InputStream inC = MainTest.class.getResourceAsStream("/test/resource/config.properties");
		byte[] byteC = new byte[1024];
		int nC;
		while ((nC = inC.read(byteC)) != -1) {
			System.out.println("测试: " + new String(byteC, 0, nC));// 测试:
																	// ip=127.0.0.4
		}

		// 注意事项:如上以/开头的是指从根目录开始加载.
		System.out.println("***********************************************************************");
		System.out.println("使用类加载器的方式:");
		InputStream inD = MainTest.class.getClassLoader().getResourceAsStream("test/resource/config.properties");
		byte[] byteD = new byte[1024];
		int nD;
		while ((nD = inD.read(byteD)) != -1) {
			System.out.println("测试: " + new String(byteD, 0, nD));// 测试:
																	// ip=127.0.0.4
		}

		System.out.println("***********************************************************************");
		System.out.println("资源配置文件在CLASSES下:");
		InputStream inE = MainTest.class.getClassLoader().getResourceAsStream("config.properties");
		byte[] byteE = new byte[1024];
		int nE;
		while ((nE = inE.read(byteE)) != -1) {
			System.out.println("测试: " + new String(byteE, 0, nE));// 测试:
																	// ip=127.0.0.5
		}

		inA.close();
		inB.close();
		inC.close();
		inD.close();
		inE.close();

	}

	public static void Test0001() throws IOException {// 使用原生方式读取配置文件

		// 1、使用JAVA.UTIL.PROPERTIES类的LOAD()方法
		InputStream in = new BufferedInputStream(new FileInputStream("config.properties"));
		Properties p = new Properties();
		p.load(in);

		// 2、使用JAVA.UTIL.RESOURCEBUNDLE类的GETBUNDLE()方法
		ResourceBundle rb2 = ResourceBundle.getBundle("config.properties", Locale.getDefault());
		rb2.getString("ip");

		// 3、使用JAVA.UTIL.PROPERTYRESOURCEBUNDLE类的构造函数
		InputStream inA = new BufferedInputStream(new FileInputStream("config.properties"));
		ResourceBundle rbA = new PropertyResourceBundle(inA);
		rbA.getString("ip");

		// 4、使用CLASS变量的GETRESOURCEASSTREAM()方法
		InputStream inB = MainTest.class.getResourceAsStream("config.properties");
		Properties pB = new Properties();
		pB.load(inB);

		// 5、使用CLASS.GETCLASSLOADER()所得到的JAVA.LANG.CLASSLOADER的GETRESOURCEASSTREAM()方法
		InputStream inC = MainTest.class.getClassLoader().getResourceAsStream("config.properties");
		Properties pC = new Properties();
		pC.load(inC);

		// 6、使用JAVA.LANG.CLASSLOADER类的GETSYSTEMRESOURCEASSTREAM()静态方法
	}
}