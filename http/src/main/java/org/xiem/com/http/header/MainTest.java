package org.xiem.com.http.header;

import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.http.HttpHeader;
import org.eclipse.jetty.server.Response;

public class MainTest {

	public static final String CONTENT_GIF = "image/gif";
	public static final String CONTENT_TEXT_HTML = "text/html";
	public static final String CONTENT_TEXT_PLAIN = "text/plain";
	public static final String CONTENT_JAVASCRIPT = "application/x-javascript";
	public static final String CONTENT_OCTET = "application/octet-stream";
	public static final String CONTENT_PROTOBUF = "application/x-protobuf";

	public static void main(String[] args) {

		HttpServletResponse response = new Response(null, null);

		response.setHeader(HttpHeader.CACHE_CONTROL.toString(), "no-cache");

		response.setContentType(CONTENT_TEXT_HTML);// 设置数据类型

		response.setContentLength(1000);// 设置带发送数据的长度
	}

}
