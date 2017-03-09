package org.xiem.com.junit.mock;

import org.easymock.EasyMock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import junit.framework.Assert;

@SuppressWarnings("deprecation")
@RunWith(PowerMockRunner.class)
@PrepareForTest({ SystemPropertyMockDemo.class }) //
public class SystemPropertyMockDemoTest {
	@Test
	public void demoOfFinalSystemClassMocking() throws Exception {
		PowerMock.mockStatic(System.class); //
		EasyMock.expect(System.getProperty("property")).andReturn("my property"); //
		PowerMock.replayAll(); //
		Assert.assertEquals("my property", new SystemPropertyMockDemo().getSystemProperty());
		PowerMock.verifyAll(); //
	}
}