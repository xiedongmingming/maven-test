package org.xiem.com.session;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

@SuppressWarnings("deprecation")
public class Test2 {

	// HttpComponents(Apache HttpComponents Client 4.1.3)

	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {// 通过POST方式访问网页或传输参数

		DefaultHttpClient httpclient = new DefaultHttpClient(); // 实例化一个HttpClient

		HttpResponse response = null;
		HttpEntity entity = null;

		httpclient.getParams().setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.BROWSER_COMPATIBILITY); // 设置COOKIE的兼容性

		// 以下两行设置通过代理访问(访问本机站点应去掉以下两行)
		// HttpHost proxy = new HttpHost("10.10.228.43", 808, "http");
		// httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);

		HttpPost httpost = new HttpPost("http://localhost:8080/httpclient-4.1.3Demo/servlet/TestServlet"); // 引号中的参数是SERVLET的地址

		// BasicNameValuePair("name", "value"):
		// NAME是POST方法里的属性
		// VALUE是传入的参数值
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();// 以下两行是参数传递测试

		nvps.add(new BasicNameValuePair("jqm", "fb1f7cbdaf2bf0a9cb5d43736492640e0c4c0cd0232da9de"));
		nvps.add(new BasicNameValuePair("sqm", "1bb5b5b45915c8"));

		httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8)); // 将参数传入POST方法中

		response = httpclient.execute(httpost); // 执行
		entity = response.getEntity(); // 返回服务器响应

		try {
			System.out.println("--------------------HTTP头信息--------------------");
			System.out.println(response.getStatusLine()); // 服务器返回状态
			Header[] headers = response.getAllHeaders(); // 返回的HTTP头信息
			for (int i = 0; i < headers.length; i++) {
				System.out.println(headers[i]);
			}
			System.out.println("---------------------显示服务器响应的HTML代码-------------------");
			String responseString = null;
			if (response.getEntity() != null) {
				responseString = EntityUtils.toString(response.getEntity()); // 返回服务器响应的HTML代码
				System.out.println(responseString); // 打印出服务器响应的HTML代码
			}
		} finally {
			if (entity != null) {
				entity.consumeContent();
			}
		}

		System.out.println("---------------------完成服务器响应的HTML代码的显示-------------------");
		System.out.println("Login form get: " + response.getStatusLine());
		if (entity != null) {
			entity.consumeContent();
		}
	}
}
