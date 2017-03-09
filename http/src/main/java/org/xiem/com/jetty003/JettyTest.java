package org.xiem.com.jetty003;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.servlet.ServletContextHandler;

public class JettyTest {

	public static void main(String[] args) throws Exception {

		// ��ҪΪ������������ʹ�����
		// ���������һ�����������̳߳�(��������ʹ��)
		Server server = new Server();// �½�һ������

		// ������������������ӹ���ʱ���ᰴ�ոù���(��Ӧ��Э��)������������
		// Ĭ�ϵ����ӹ���Ϊ: HttpConnectionFactory
		ServerConnector connector = new ServerConnector(server);// ���ڼ�������--������ʾ

		connector.setPort(8080);// ���ü����Ķ˿�

		server.setConnectors(new Connector[] { connector });// ��������ע�ᵽ������

		ServletContextHandler context = new ServletContextHandler();
		context.setContextPath("/");// ����ָ����Ŀ¼
		// context.addServlet(HelloServlet.class, "/hello");
		context.addServlet(AsyncEchoServlet.class, "/echo/*");

		HandlerCollection handlers = new HandlerCollection();
		handlers.setHandlers(new Handler[] { context, new DefaultHandler() });
		server.setHandler(handlers);

		server.start();
		server.join();
	}
}
