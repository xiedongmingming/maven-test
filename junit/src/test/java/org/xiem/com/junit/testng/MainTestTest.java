package org.xiem.com.junit.testng;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class MainTestTest {

	// TESTNG基本注解描述如下:
	// @BeforeSuite: 注解的方法将只运行一次(此套件中的所有测试都运行之前)
	// @AfterSuite: 注解的方法将只运行一次(此套件中的所有测试都运行之后)
	// @BeforeClass:	注解的方法将只运行一次先行先试在当前类中的方法调用.
	// @AfterClass:		注解的方法将只运行一次后已经运行在当前类中的所有测试方法.
	// @BeforeTest:		注解的方法将被运行之前的任何测试方法属于内部类的<TEST>标签的运行.
	// @AfterTest:		注解的方法将被运行后,所有的测试方法,属于内部类的<TEST>标签的运行.
	// @BeforeGroups:	组的列表,这种配置方法将之前运行.此方法是保证在运行属于任何这些组第一个测试方法,该方法被调用.
	// @AfterGroups:	组的名单,这种配置方法后,将运行.此方法是保证运行后不久,最后的测试方法,该方法属于任何这些组被调用.
	// @BeforeMethod:	注解的方法将每个测试方法之前运行.
	// @AfterMethod:	被注释的方法将被运行后，每个测试方法.
	// @DataProvider: 	标志着一个方法,提供数据的一个测试方法.注解的方法必须返回一个OBJECT[][],其中每个对象[]的测试方法的参数列表中可以分配.该@TEST方法,希望从这个DATAPROVIDER的接收数据,需要使用一个DATAPROVIDER名称等于这个注解的名字.
	// @Factory:		作为一个工厂.返回TESTNG的测试类的对象将被用于标记的方法.该方法必须返回OBJECT[].
	// @Listeners:		定义一个测试类的监听器.
	// @Parameters:		介绍如何将参数传递给@TEST方法.
	// @Test:			标记一个类或方法作为测试的一部分.

	// 使用TESTNG.XML文件来执行(把要执行的CASE放入TESTNG.XML文件中)右键点击TESTNG.XML点RUN AS TESTNG.XML
	// <?xml version="1.0" encoding="UTF-8"?><!DOCTYPE suite SYSTEM "http://TESTNG.org/TESTNG-1.0.dtd">
	// <suite name="Suite1">
	//		<test name="test12">
	// 			<classes>
	// 				<class name="TankLearn2.Learn.TestNGLearn1"/>
	// 			</classes>
	// 		</test>
	// </suite>



	@BeforeClass
	public void beforeClass() {
		System.out.println("this is before class");
	}

	// ************************************************************************************
	// TESTNG组测试:TESTNG中可以把测试用例分组(这样可以按组来执行测试用例)
	// 然后在TESTNG.XML中按组执行测试用例
	@Test(groups = { "systemtest" })
	public void testLogin() {
		System.out.println("this is test login");
	}
	@Test(groups = { "functiontest" })
	public void testOpenPage() {
		System.out.println("this is test Open Page");
	}

	// ************************************************************************************
	// TESTNG参数化测试:软件测试中经常需要测试大量的数据集.测试代码的逻辑完全一样只是测试的参数不一样.这样我们就需要一种传递测试参数的机制避免写重复的测试代码.TESTNG提供了2种传递参数的方式.
	// 第一种:TESTNG.XML方式使代码和测试数据分离方便维护
	// 第二种:@DATAPROVIDER能够提供比较复杂的参数.(也叫DATA-DRIVEN TESTING)
	@Test
	@Parameters("test1")
	public void ParaTest(String test1) {// 方法一:通过TESTNG.XML传递参数给测试代码
		System.out.println("this is " + test1);
	}

	@DataProvider(name = "user")
	public Object[][] Users() {
		return new Object[][] { { "root", "passowrd" }, { "cnblogs.com", "tankxiao" }, { "tank", "xiao" } };
	}
	@Test(dataProvider = "user")
	public void verifyUser(String userName, String password) {// 方式二:通过DATAPROVIDER传递参数
		System.out.println("Username: " + userName + " Password: " + password);
	}

	// ************************************************************************************
	// TESTNG异常测试:测试中有时候我们期望某些代码抛出异常
	// TESTNG通过@TEST(EXPECTEDEXCEPTIONS)来判断期待的异常(并且判断ERROR MESSAGE)
	@Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "NullPoint")
	public void TestNGLearn001() {
		throw new IllegalArgumentException("NullPoint");
	}

	// ************************************************************************************
	// TESTNG忽略测试:有时候测试用例还没准备好可以给测试用例加上@TEST(ENABLE=FALSE)来禁用此测试用例
	@Test(enabled = false)
	public void testIgnore() {
		System.out.println("This test case will ignore");
	}

	// ************************************************************************************
	// TESTNG依赖测试:有时候我们需要按顺序来调用测试用例(那么测试用例之间就存在依赖关系)TESTNG支持测试用例之间的依赖
	@Test
	public void setupEnv() {
		System.out.println("this is setup Env");
	}
	@Test(dependsOnMethods = { "setupEnv" })
	public void testMessage() {
		System.out.println("this is test message");
	}

	@Test
	public void TestNGLearn002() {
		System.out.println("this is testng test case");
	}

	@Test
	public void TestNGLearn003() {
		System.out.println("this is testng test case");
	}

	@AfterClass
	public void afterClass() {
		System.out.println("this is after class");
	}
}
