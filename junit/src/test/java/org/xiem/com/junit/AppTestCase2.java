package org.xiem.com.junit;

import junit.framework.TestCase;

public class AppTestCase2 extends TestCase {


	public void testAdd() {

		App calculator = new App();

		calculator.clear();

		calculator.add(1);
		calculator.add(2);

		assertEquals(3, calculator.getResult(), 0);
	}
}
