package org.xiem.com.guice;

public class MainTest {

	public static void main(String[] args) {

		System.out.println("\u4e0a\u6d77\u6590\u8baf\u901a\u8baf\u6280\u672f\u6709\u9650\u516c\u53f8");

		// Injector injector = Guice.createInjector(new CommentModule());
		//
		// CommentService commentService =
		// injector.getInstance(CommentServiceImpl.class);
		//
		// commentService.comment();

		// �ҵ����:
		// ����:injector.getInstance(CommentServiceImpl.class)
		// injector�ͱ�ʾ����CommentModule��ָ���İ󶨹�ϵ������CommentServiceImpl

		// ����CommentServiceImpl�Ĺ��캯���Ѿ���@Inject����
		// @Inject��ʾGuice����ʽ����CommentServiceImpl�Ĺ��췽��
	}
}
