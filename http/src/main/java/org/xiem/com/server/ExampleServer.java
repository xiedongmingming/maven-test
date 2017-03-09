package org.xiem.com.server;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.xiem.com.servlet.AsyncEchoServlet;
import org.xiem.com.servlet.HelloServlet;

public class ExampleServer {

	public static void main(String[] args) throws Exception {

        Server server = new Server();

		ServerConnector connector = new ServerConnector(server);// 首先设置连接器
        connector.setPort(8080);
        server.setConnectors(new Connector[] { connector });

		ServletContextHandler context = new ServletContextHandler();// 设置根处理器
        context.setContextPath("/");
        context.addServlet(HelloServlet.class, "/hello");
        context.addServlet(AsyncEchoServlet.class, "/echo/*");

		HandlerCollection handlers = new HandlerCollection();// 将各种处理包装起来添加到服务器中
        handlers.setHandlers(new Handler[] { context, new DefaultHandler() });
        server.setHandler(handlers);

        server.start();
        server.join();
    }
}
