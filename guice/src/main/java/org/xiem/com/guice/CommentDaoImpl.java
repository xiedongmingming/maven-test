package org.xiem.com.guice;

public class CommentDaoImpl implements CommentDao {

	public CommentDaoImpl() {

	}

	@Override
	public void comment(String message) {
		System.out.println(message);
	}
}
