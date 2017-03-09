package org.xiem.com.apache.lang3;
import org.apache.commons.lang3.StringEscapeUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class CommonsLangTest {

	@Before
	public void beforeTest() {
		System.out.println("test: before");
	}

	@Test
	public void test() {
		System.out.println("test: testing...");
	}

	@Test
	public void testArrayUtils() {
		System.out.println(StringEscapeUtils.escapeHtml4("http:\\/\\/baidu.com/adfasdf/afadsf"));
	}

	@After
	public void afterTest() {
		System.out.println("test: after");
	}
}
