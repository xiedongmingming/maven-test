package org.xiem.com.http;

import java.io.IOException;

import org.xiem.com.http.core.Handler;
import org.xiem.com.http.impl.HttpRequest;
import org.xiem.com.http.impl.HttpResponse;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

@SuppressWarnings("restriction")
public class MyHttpHandler implements HttpHandler {//�ڲ���Ϣ������

	public void handle(HttpExchange httpExchange) throws IOException {// ����????

		HttpRequest request = new HttpRequest(httpExchange);

		HttpResponse response = new HttpResponse(httpExchange);

		Handler handler = Context.getHandler(request.getReuestURI().getPath());// ��ȡ��Ӧ�Ĵ�����

		handler.service(request, response);
	}
}