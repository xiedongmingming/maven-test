package org.xiem.com.http;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.spi.HttpServerProvider;

/**
 * �ο�����ԭ��
 * 
 * http://blog.csdn.net/maosijunzi/article/details/41045181
 */

@SuppressWarnings("restriction")
public class MyHttpServer {

	public static void start() throws IOException {// ��������������Կͻ��˵�����

		// Headers responseHeaders = httpExchange.getResponseHeaders();
		// responseHeaders.add("location", "http://www.baidu.com");
		// httpExchange.sendResponseHeaders(302, 0);
		// httpExchange.close();
		// OutputStream out = httpExchange.getResponseBody();
		// out.write(result.getBytes());
		// out.flush();

		Context.load();// ��������(����·��ӳ��HANDLER)

		HttpServerProvider provider = HttpServerProvider.provider();// HTTP���������ṩ��

		HttpServer httpserver = provider.createHttpServer(new InetSocketAddress(8180), 100);// �����˿�8080,��ͬʱ����100������

		httpserver.createContext(Context.contextPath, new MyHttpHandler());// ��ʾ�ܿ�����(������)
		httpserver.setExecutor(null);// ???
		httpserver.start();// ����������

		System.out.println("server started");
	}

	public static void main(String[] args) throws IOException {          
		start();
	}
}
