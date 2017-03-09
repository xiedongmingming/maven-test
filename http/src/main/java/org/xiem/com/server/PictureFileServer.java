package org.xiem.com.server;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.xiem.com.servlet.PictureFileServlet;

public class PictureFileServer {

	public static void main(String[] args) throws Exception {// http://localhost:8080/static/xieming030.swf

		Server server = new Server();

		ServerConnector connector = new ServerConnector(server);

		connector.setPort(8080);

		server.setConnectors(new Connector[] { connector });

		ServletContextHandler context = new ServletContextHandler();
		context.setContextPath("/");
		context.addServlet(PictureFileServlet.class, "/static/*");// 注意:如果没有后面的路径匹配则只能严格按照路径(不能携带后续路径)

		HandlerCollection handlers = new HandlerCollection();

		handlers.setHandlers(new Handler[] { context, new DefaultHandler() });

		server.setHandler(handlers);

		server.start();
		server.join();
	}

}
