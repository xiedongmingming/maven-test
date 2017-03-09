package org.xiem.com.session;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpClientParams;

import net.sf.json.JSONObject;

public class ClientDemo { // HTTPCLIENTJSON网络传输(二进制的BYTE流)

	public void login() {

		String url = "http://192.168.0.3:8081/httpjson/demoHttpJson";

		JSONObject json = new JSONObject();

		json.put("username", "test");
		json.put("password", "test");

		doPostClient(json, url);
	}

	@SuppressWarnings({ "deprecation" })
	public static void doPostClient(JSONObject json, String url) {

		HttpClient httpClient = new HttpClient();

		// httpClient.getState().setCookiePolicy(CookiePolicy.COMPATIBILITY);

		System.setProperty("apache.commons.httpclient.cookiespec", "COMPATIBILITY");

		PostMethod postMethod = new PostMethod(url);

		InputStream in = new ByteArrayInputStream(json.toString().getBytes());

		postMethod.setRequestBody(in);

		HttpClientParams params = new HttpClientParams();

		params.setConnectionManagerTimeout(10000L);

		httpClient.setParams(params);

		try {

			httpClient.executeMethod(postMethod);

			byte[] b = postMethod.getResponseBody();// 获取二进制的byte流

			String str = new String(b, "UTF-8");

			System.out.println(str);

		} catch (Exception e) {

			System.out.println(e.getMessage() + "," + e.getStackTrace());

		} finally {
			postMethod.releaseConnection();
		}
	}
}