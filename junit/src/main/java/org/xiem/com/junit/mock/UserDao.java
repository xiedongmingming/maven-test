package org.xiem.com.junit.mock;

public class UserDao {
	public User getById(String id) throws Exception {
		try {
			return new User(id);
		} catch (Exception e) {
			throw e;
		}
	}
}
