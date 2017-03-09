package org.xiem.com.junit;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class AppTest2 {

	private static App calculator = new App();

	private int param;
	private int result;

	// *****************************************************************************
	// 参数化测试:
	// 1、要为这种测试专门生成一个新的类而不能与其他测试共用同一个类
	// 2、要为这个类指定一个RUNNER而不能使用默认的RUNNER了,因为特殊的功能要用特殊的RUNNER嘛
	// @RunWith(Parameterized.class)-->ParameterizedRunner
	@Test
	public void square1() {
		calculator.square(2);
		assertEquals(4, calculator.getResult());
	}

	@Test
	public void square2() {
		calculator.square(0);
		assertEquals(0, calculator.getResult());
	}

	@Test
	public void square3() {
		calculator.square(-3);
		assertEquals(9, calculator.getResult());
	}

	@SuppressWarnings("rawtypes")
	@Parameters
	public static Collection data() {
		return Arrays.asList(new Object[][] {
                { 2 ,  4 } ,
                { 0 ,  0 } ,
				{ -3, 9 },
       } );
	}

	public AppTest2(int param, int result) {
		this.param = param;
		this.result = result;
	}

	@Test
	public void square() {
		calculator.square(param);
		assertEquals(result, calculator.getResult());
	}

}
