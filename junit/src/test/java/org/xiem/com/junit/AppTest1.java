package org.xiem.com.junit;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

// 使用了系统默认的TESTCLASSRUNNER与下面代码完全一样--JUNIT4.4中才有
// @RunWith(TestClassRunner.class)
public class AppTest1 {

	// 当你把测试代码提交给JUNIT框架后框架如何来运行你的代码呢?答案就是RUNNER
	// 在JUNIT中有很多个RUNNER他们负责调用你的测试代码(每一个RUNNER都有各自的特殊功能你要根据需要选择不同的
	// RUNNER来运行你的测试代码.可能你会觉得奇怪前面我们写了那么多测试并没有明确指定一个RUNNER啊?这是因为JUNIT
	// 中有一个默认RUNNER,如果你没有指定那么系统自动使用默认RUNNER来运行你的代码.换句话说下面两段代码含义是完全一样的:

	private static App calculator = new App();

	public AppTest1(int param, int result) {

	}

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {

	}

	@Before
	public void setUp() throws Exception {// 在任何一个测试执行之前必须执行的代码
		calculator.clear();
	}

	@After
	public void tearDown() throws Exception {// 在任何测试执行之后需要进行的收尾工作

	}

	@Test
	public void testAdd() {
		calculator.add(2);
		calculator.add(3);
		assertEquals(5, calculator.getResult());
	}

	@Test
	public void testSubstract() {
		calculator.add(10);
		calculator.substract(2);
		assertEquals(8, calculator.getResult());
	}

	@Ignore("Multiply() Not yet implemented")
	@Test
	public void testMultiply() {

	}

	@Test
	public void testDivide() {
		calculator.add(8);
		calculator.divide(2);
		assertEquals(4, calculator.getResult());
	}

	@Test(timeout = 1000)
	public void squareRoot() {// 限时测试(单位为毫秒)
		calculator.squareRoot(4);
		assertEquals(2, calculator.getResult());
	}

	@Test(expected = ArithmeticException.class)
	public void divideByZero() {// 测试异常
		calculator.divide(0);
	}



	// *****************************************************************************
}
