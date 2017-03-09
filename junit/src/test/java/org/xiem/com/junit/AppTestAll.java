package org.xiem.com.junit;

import junit.framework.Test;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

public class AppTestAll extends TestSuite {

	public static Test suite() {

		TestSuite suite = new TestSuite("TestSuite Test");

		suite.addTestSuite(AppTestCase2.class);
		suite.addTestSuite(AppTestCase1.class);

		return suite;
	}

	public static void main(String args[]) {
		TestRunner.run(suite());
	}
}
