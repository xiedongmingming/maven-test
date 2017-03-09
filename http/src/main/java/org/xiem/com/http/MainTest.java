package org.xiem.com.http;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

@SuppressWarnings("deprecation")
public class MainTest {

	// HTTPCOMPONENTS之HTTPCLIENT基本使用方法: HTTPCLIENT的版本是4.3

	public static void main(String[] args) throws IOException {
		
		Test001();
	}

	private static void Test001() throws IOException {// 第一种方式

		List<NameValuePair> formparams = new ArrayList<NameValuePair>();

		formparams.add(new BasicNameValuePair("account", "xxxxxxxxxx"));
		formparams.add(new BasicNameValuePair("password", "xxxxxxxxxxxx"));

		HttpEntity reqEntity = new UrlEncodedFormEntity(formparams, "utf-8");

		RequestConfig requestConfig = RequestConfig.custom()
				.setSocketTimeout(5000)
				.setConnectTimeout(5000)
				.setConnectionRequestTimeout(5000).build();

		@SuppressWarnings("resource")
		HttpClient client = new DefaultHttpClient();

		HttpPost post = new HttpPost("http://localhost:8080/hello");

		post.setEntity(reqEntity);
		post.setConfig(requestConfig);

		HttpResponse response = client.execute(post);

		if (response.getStatusLine().getStatusCode() == 200) {

			HttpEntity resEntity = response.getEntity();

			String message = EntityUtils.toString(resEntity, "UTF-8");

			System.out.println(message);

		} else {
			System.out.println("请求失败");
		}
	}
}
