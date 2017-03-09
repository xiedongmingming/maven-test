package org.xiem.com.http;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.spi.HttpServerProvider;

/**
 * 参考代码原文
 * 
 * http://blog.csdn.net/maosijunzi/article/details/41045181
 */

@SuppressWarnings("restriction")
public class MyHttpServer {

	public static void start() throws IOException {// 启动服务监听来自客户端的请求

		// Headers responseHeaders = httpExchange.getResponseHeaders();
		// responseHeaders.add("location", "http://www.baidu.com");
		// httpExchange.sendResponseHeaders(302, 0);
		// httpExchange.close();
		// OutputStream out = httpExchange.getResponseBody();
		// out.write(result.getBytes());
		// out.flush();

		Context.load();// 加载配置(请求路径映射HANDLER)

		HttpServerProvider provider = HttpServerProvider.provider();// HTTP服务器的提供器

		HttpServer httpserver = provider.createHttpServer(new InetSocketAddress(8180), 100);// 监听端口8080,能同时接受100个请求

		httpserver.createContext(Context.contextPath, new MyHttpHandler());// 表示总控制器(处理器)
		httpserver.setExecutor(null);// ???
		httpserver.start();// 启动服务器

		System.out.println("server started");
	}

	public static void main(String[] args) throws IOException {          
		start();
	}
}
