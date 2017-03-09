package org.xiem.com.guice;

import com.google.inject.AbstractModule;;

public class CommentModule extends AbstractModule {

	// MODULE������ʹ�� GUICE API��һ��Ӧ�ó����д���BINDINGS
	// ͨ��BINDER������Խ�һЩBINDINGS���õ�ĳ��MODULE��
	@Override
	protected void configure() {// CommentModule���ʾ��Ҫ��CommentDao��CommentDaoImpl��ע�������.
		bind(CommentDao.class).to(CommentDaoImpl.class);// Bindings
	}

}
