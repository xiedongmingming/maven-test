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

		// 需要为服务绑定连接器和处理器
		// 服务本身就是一个处理器和线程池(供连接器使用)
		Server server = new Server();// 新建一个服务

		// 当向该连接器传入连接工厂时将会按照该工厂(对应的协议)来产生连接器
		// 默认的连接工厂为: HttpConnectionFactory
		ServerConnector connector = new ServerConnector(server);// 用于监听连接--参数表示

		connector.setPort(8080);// 设置监听的端口

		server.setConnectors(new Connector[] { connector });// 将监听器注册到服务中

		ServletContextHandler context = new ServletContextHandler();
		context.setContextPath("/");// 用于指定根目录
		// context.addServlet(HelloServlet.class, "/hello");
		context.addServlet(AsyncEchoServlet.class, "/echo/*");

		HandlerCollection handlers = new HandlerCollection();
		handlers.setHandlers(new Handler[] { context, new DefaultHandler() });
		server.setHandler(handlers);

		server.start();
		server.join();
	}
}
