package org.xiem.com;

import java.io.BufferedReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

@SuppressWarnings("unused")
public class MainTest {

	private static final String PROD_URL_REPORT_DETAIL = "http://open.adx.qq.com/report/detail";
	private static final String PROD_URL_ORDER_REPORT = "http://open.adx.qq.com/order/report";

	private static final String PROD_DSP_ID = "10092";
	private static final String PROD_TOKEN = "19f5d2229b69d6a1b40f2d562a9b804f";

	private static String URL_REPORT_DETAIL = PROD_URL_REPORT_DETAIL;
	private static String URL_ORDER_REPORT = PROD_URL_ORDER_REPORT;

	private static String DSP_ID = PROD_DSP_ID;
	private static String TOKEN = PROD_TOKEN;

	private static final HttpClient client = new HttpClient();

	public String executeExchangeAPI(final String url, final List<NameValuePair> params) {

		String jsonResponse = null;

		BufferedReader br = null;

		PostMethod method = new PostMethod(url);

		method.getParams().setParameter("http.protocol.content-charset", StandardCharsets.UTF_8.toString());

		method.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

		for (NameValuePair kv : params) {
			method.addParameter(kv.getName(), kv.getValue());
		}

		for (NameValuePair parm : method.getParameters()) {
			System.out.println("参数: " + parm.getName() + "-->" + parm.getValue());
		}
		try {

			client.executeMethod(method);

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

		return jsonResponse;
	}

	protected List<NameValuePair> getIdentifyParamList() {

		List<NameValuePair> params = new ArrayList<NameValuePair>();

		params.add(new NameValuePair("dsp_id", DSP_ID));
		params.add(new NameValuePair("token", TOKEN));

		return params;
	}

	public String getOrderReport() {// 5.1.2 获取相关效果数据接口(DSP_ORDER_ID推荐)

		List<NameValuePair> params = getIdentifyParamList();

		params.add(new NameValuePair("start_date", "2017-01-07"));// 报表查询的开始日期
		params.add(new NameValuePair("end_date", "2017-01-07"));// 报表查询的结束日期

		String ret = executeExchangeAPI(URL_ORDER_REPORT, params);

		return ret;
	}

	public static void main(String[] args) {
		new MainTest().getOrderReport();
	}

}
