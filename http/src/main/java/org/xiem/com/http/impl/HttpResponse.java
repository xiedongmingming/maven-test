package org.xiem.com.http.impl;

import java.io.IOException;
import java.io.OutputStream;

import org.xiem.com.http.core.Response;

import com.sun.net.httpserver.HttpExchange;

@SuppressWarnings("restriction")
public class HttpResponse implements Response{
	
	private HttpExchange httpExchange;
	
	public HttpResponse(HttpExchange httpExchange){
		this.httpExchange = httpExchange;
	}

	@Override
	public void write(String result) {
		try {
			httpExchange.sendResponseHeaders(200, result.length());//
			OutputStream out = httpExchange.getResponseBody(); //
			out.write(result.getBytes());
			out.flush();
			httpExchange.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
