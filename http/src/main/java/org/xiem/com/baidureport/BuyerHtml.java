package org.xiem.com.baidureport;

import java.io.IOException;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.lang3.StringUtils;

public class BuyerHtml {// 用于获取登录页面(需求方)

	public static HttpClient client = new HttpClient();

	public static String executeLoginApi(final String url) {

		String jsonResponse = StringUtils.EMPTY;

		GetMethod method = new GetMethod(url);

		method.setRequestHeader("Upgrade-Insecure-Requests", "1");
		method.setRequestHeader("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143 Safari/537.36");

		try {

			client.executeMethod(method);

			jsonResponse = method.getResponseBodyAsString();

			for (Header header : method.getResponseHeaders()) {
				System.out.println("响应头: " + header.getName() + "-->" + header.getValue());
			}

		} catch (IOException e) {

		} finally {
			method.releaseConnection();
		}

		return jsonResponse;
	}

	public static String executeKeyApi(final String url) {
		
		String jsonResponse = StringUtils.EMPTY;

		GetMethod method = new GetMethod(url);

		method.setRequestHeader("Accept", "image/webp,image/*,*/*;q=0.8");
		method.setRequestHeader("Accept-Encoding", "gzip, deflate, sdch");
		method.setRequestHeader("Accept-Language", "zh-CN,zh;q=0.8");
		method.setRequestHeader("Connection", "keep-alive");
		method.setRequestHeader("Cookie",
				"GIMGSSID=29981gh76eb4cc81dd523; BAIDUID=784291103308A9CF8F887A37A4A276AE:FG=1");
		method.setRequestHeader("Host", "cas.baidu.com");
		method.setRequestHeader("Referer", "http://bes.baidu.com/html/buyer.html");
		method.setRequestHeader("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143 Safari/537.36");

		try {

			client.executeMethod(method);

			jsonResponse = method.getResponseBodyAsString();

			for (Header header : method.getResponseHeaders()) {
				System.out.println("响应头: " + header.getName() + "-->" + header.getValue());
			}

		} catch (IOException e) {

		} finally {
			method.releaseConnection();
		}

		System.out.println("响应体: " + jsonResponse);

		return jsonResponse;
	}

	public static void main(String[] args) {

		String buyerHtml = "http://cas.baidu.com/?action=image&key=&key=1487157026830";

		executeKeyApi(buyerHtml);

	}

}
