package org.xiem.com.junit.mock;

public class UserServiceImpl {
	private UserDao userDao;

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao dao) {
		this.userDao = dao;
	}

	public User query(String id) throws Exception {
		try {
			return userDao.getById(id);
		} catch (Exception e) {
			throw e;
		}
	}
}
