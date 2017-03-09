package org.xiem.com.junit.mock;

import static org.junit.Assert.assertEquals;

import org.easymock.EasyMock;
import org.junit.Test;

public class UserServiceImplTest {
	@Test
	public void testQuery() {

		User expectedUser = new User();
		expectedUser.setId("1001");

		UserDao mock = EasyMock.createMock(UserDao.class);//
		try {
			// EasyMock.expect(mock.getById("1001")).andReturn(expectedUser);
			// EasyMock.expect(mock.getById("1001")).andReturn(expectedUser).times(3);
			// EasyMock.expect(mock.getById("1001")).andThrow(new
			// RuntimeException());
			EasyMock.expect(mock.getById(EasyMock.isA(String.class))).andReturn(expectedUser).times(3);
		} catch (Exception e) {
			System.out.println("");
			e.printStackTrace();
		} //

		EasyMock.replay(mock);//

		UserServiceImpl service = new UserServiceImpl();
		service.setUserDao(mock);
		User user = null;
		try {
			user = service.query("1001");
			user = service.query("1001");
			user = service.query("1002");
		} catch (Exception e) {
			System.out.println("");
			e.printStackTrace();
		} //
		assertEquals(expectedUser, user); //
		EasyMock.verify(mock);//
	}
}
