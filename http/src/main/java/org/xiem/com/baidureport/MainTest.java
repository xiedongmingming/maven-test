package org.xiem.com.baidureport;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.lang3.StringUtils;
import org.xiem.com.baidureport.JsonProcessor.JsonException;

@SuppressWarnings("unused")
public class MainTest {

	public static HttpClient client = new HttpClient();

	private static final String PROD_URL_ADVERTISER_ADD = "https://api.es.baidu.com/v1/advertiser/add";// 新增广告主
	private static final String PROD_URL_ADVERTISER_UPDATE = "https://api.es.baidu.com/v1/advertiser/update";// 修改广告主

	private static String URL_ADVERTISER_ADD = PROD_URL_ADVERTISER_ADD;
	private static String URL_ADVERTISER_UPDATE = PROD_URL_ADVERTISER_UPDATE;

	private static final int PROD_DSP_ID = 19797210;
	private static final String PROD_TOKEN = "634ca14dbe8573f21f92c19bac159e46";

	private static int DSP_ID = PROD_DSP_ID;
	private static String TOKEN = PROD_TOKEN;

	// http://bes.baidu.com
	// 账号: youshan-ming
	// 密码: Abcd1234
	private static String BAIDUUID = "700033F224FEDB53CEE79DEB548F07B0:FG=1";
	private static String SIGNIN_UC = "70a2711cf1d3d9b1a82d2f87d633bd8a02371556111";
	private static String uc_login_unique = "63db3c9bc135ba69be1a62f4cfca1bf1";
	private static String __cas__st__177 = "c6f0d864a25151ca035ad7cd9c63a857a71763e9843ca6d4502844abb92ac5078de683225a67667d6d2bdcb1";
	private static String __cas__id__177 = "22445039";
	private static String __cas__rn__ = "237155611";
	private static String __VCAP_ID__ = "c67d860edfa7a27b9e73d1049c03dc4c33ec1e5c5ffea211bf7341081fdb23f7";
	private static String JSESSIONID = "B41F701A90D723D086331AD220DB689A";

	private AuthHeader header = new AuthHeader(DSP_ID, TOKEN);

	public String executeExchangeAPI(final String url, final String queryStr) {

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

	public String executeGetDataAPI(final String url) {

		String jsonResponse = StringUtils.EMPTY;

		GetMethod method = new GetMethod(url);

		method.setRequestHeader("Content-Type", "application/json; charset=utf-8");
		method.setRequestHeader("Accept", "*/*");
		method.setRequestHeader("Accept-Encoding", "gzip, deflate, sdch");
		method.setRequestHeader("Accept-Language", "zh-CN,zh;q=0.8");
		method.setRequestHeader("Connection", "keep-alive");
		method.setRequestHeader("Cookie",
				"BAIDUID=D2B494BF89F6CAB0A9F4425931540FB3:FG=1; SIGNIN_UC=70a2711cf1d3d9b1a82d2f87d633bd8a02371512533; uc_login_unique=aca1dfcc3b79612a7f427dc315c416ed; __cas__st__177=3e323a77f4b2c90cffa7534187c79c7f4e5ac2181bcf1b0ad23d709806eef572feddc0282bfb4b0e7df0e603; __cas__id__177=22445039; __cas__rn__=237151253; __VCAP_ID__=a52de58cc062b6e0f8648d98b56061d720f98f8b9968e17bd9d7c703b9e2fd53; JSESSIONID=7F613034CC695AE50C83FBDDE147D7F5");

		for (Header header : method.getRequestHeaders()) {
			System.out.println("请求头: " + header.getName() + "-->" + header.getValue());
		}

		try {

			client.executeMethod(method);

			jsonResponse = method.getResponseBodyAsString();

		} catch (IOException e) {

		} finally {
			method.releaseConnection();
		}

		return jsonResponse;
	}

	// http://bes.baidu.com/html/buyer.html
	
	public static void main(String[] args) {

		// String getUrl =
		// "http://buyer.bes.baidu.com/report/creativeStat/listByDay?order=desc&orderBy=showDate&page=0&pageSize=20&dspId=19797210&startDate=2017-02-09&endDate=2017-02-09&timeUnit=DAY&creativeId=13778&adviewType=WEB&&token=_tk1487126870506";

		// String retUrl = new MainTest().executeExchangeAPI(getUrl);

		String loginUrl = "http://buyer.bes.baidu.com/portal/initAll?token=_tk1487143164717";


		String retUrl = new MainTest().executeLoginAPI(loginUrl);

		System.out.println(retUrl);

	}

	public String executeLoginAPI(final String url) {

		String jsonResponse = StringUtils.EMPTY;

		GetMethod method = new GetMethod(url);

		method.setRequestHeader("Accept", "*/*");
		method.setRequestHeader("Accept-Encoding", "gzip, deflate, sdch");
		method.setRequestHeader("Accept-Language", "zh-CN,zh;q=0.8");
		method.setRequestHeader("Connection", "keep-alive");
		method.setRequestHeader("Host", "buyer.bes.baidu.com");
		method.setRequestHeader("Referer", "http://buyer.bes.baidu.com/static/main.html");
		method.setRequestHeader("X-Requested-With", "XMLHttpRequest");
		method.setRequestHeader("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143 Safari/537.36");
		method.setRequestHeader("Cookie",
				"BAIDUID=700033F224FEDB53CEE79DEB548F07B0:FG=1; SIGNIN_UC=70a2711cf1d3d9b1a82d2f87d633bd8a02371556111; uc_login_unique=63db3c9bc135ba69be1a62f4cfca1bf1; __VCAP_ID__=1ffcd75d2f2749d452026d78c0f13474a6494bfd9ecb840caf61bb7e20031d05; __cas__st__177=c6f0d864a25151ca035ad7cd9c63a857a71763e9843ca6d4502844abb92ac5078de683225a67667d6d2bdcb1; __cas__id__177=22445039; __cas__rn__=237155611; JSESSIONID=F30D7DA8A20977C0161369AD46875218");

		for (Header header : method.getRequestHeaders()) {
			System.out.println("请求头: " + header.getName() + "-->" + header.getValue());
		}

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

	public static class Advertiser {// 新增修改都使用该字段
		public long advertiserId;// 广告主ID
		public String advertiserLiteName;// 广告主名称

		// ***************************************************************************
		// 下面3个字段是百度才进行广告主状态检查必须的字段(如果这些字段修改会重新发起广告主状态检查)
		public String advertiserName;// 广告主主体资质名称(可选)--此名称与客户营业执照上的名称一致
		public String siteName;// 网站名(可选)
		public String siteUrl;// 网站URL(可选)--必须以HTTP://开头
		// ***************************************************************************

		public String telephone;// 联系电话(可选)
		public String address;// 通讯地址(可选)
		public int isWhiteUser;// 广告主是否白名单(可选)--返回时有效
	}

	public static class AuthHeader {
		public int dspId;
		public String token;

		public AuthHeader() {

		}

		public AuthHeader(int dspId, String token) {
			this.dspId = dspId;
			this.token = token;
		}
	}

	// 2.1.1、2.1.2 新增或修改
	public static class RequestForAdvertiserAddOrUpdate {// 一次最多新增/修改5个广告主
		public List<Advertiser> request;
		public AuthHeader authHeader;
	}

	private String addOrUpdateAdvertiser() {// 广告主的新增和修改--一次最多新增、修改5个广告主

		RequestForAdvertiserAddOrUpdate request = new RequestForAdvertiserAddOrUpdate();

		List<Advertiser> advertisers = new ArrayList<Advertiser>();

		Advertiser ad = new Advertiser();

		ad.advertiserId = 137;
		ad.advertiserLiteName = "招商银行";
		ad.advertiserName = "招商银行股份有限公司信用卡中心";
		ad.siteName = "招商银行";
		ad.siteUrl = "http://www.4008855855.com";
		ad.isWhiteUser = 0;

		advertisers.add(ad);

		request.request = advertisers;
		request.authHeader = header;

		String requestStr = null;

		try {
			requestStr = JsonProcessor.get().writeJsonFromObject(request);
		} catch (IOException | JsonException e) {
			return StringUtils.EMPTY;
		}

		String ret = "";

		ret = executeExchangeAPI(URL_ADVERTISER_ADD, requestStr);

		// ret = executeExchangeAPI(URL_ADVERTISER_UPDATE, requestStr);


		return ret;
	}

}
