package org.xiem.com.guice;

import com.google.inject.Inject;

public class CommentServiceImpl implements CommentService {

	private CommentDao commentDao;

	@Inject
	public CommentServiceImpl(CommentDao commentDao) {
		this.commentDao = commentDao;
	}

	public void comment() {
		commentDao.comment("This is a comment message!");
	}

	public void setCommentDao(CommentDao commentDao) {
		this.commentDao = commentDao;
	}
}
