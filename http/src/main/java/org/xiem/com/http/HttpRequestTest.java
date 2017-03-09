package org.xiem.com.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.lang3.StringUtils;


public class HttpRequestTest {

	private static final HttpClient client = new HttpClient();

	public static void main(String[] args) {

	}

	@SuppressWarnings("unused")
	public void executeExchangeAPI(final String url, final List<NameValuePair> params) {

		String jsonResponse = null;

		BufferedReader br = null;

		PostMethod method = new PostMethod(url);

		method.getParams().setParameter("http.protocol.content-charset", StandardCharsets.UTF_8.toString());

		method.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		for (NameValuePair kv : params) {
			method.addParameter(kv.getName(), kv.getValue());
		}

		try {
			int returnCode = client.executeMethod(method);
			jsonResponse = method.getResponseBodyAsString();
		} catch (Exception e) {

		} finally {
			method.releaseConnection();
			if (br != null) {
				try {
					br.close();
				} catch (Exception fe) {
				}
			}
		}

	}

	public String executeExchangeAPI(final String url, final String queryStr) {

		HttpClient client = new HttpClient();

		String jsonResponse = StringUtils.EMPTY;

		PostMethod method = new PostMethod(url);

		method.getParams().setParameter("http.protocol.content-charset", StandardCharsets.UTF_8.toString());

		method.setRequestHeader("Content-Type", "application/json; charset=utf-8");

		try {
			method.setRequestEntity(new StringRequestEntity(queryStr, null, null));
			client.executeMethod(method);
			jsonResponse = method.getResponseBodyAsString();
		} catch (IOException e) {

		} finally {
			method.releaseConnection();
		}
		return jsonResponse;
	}
}
