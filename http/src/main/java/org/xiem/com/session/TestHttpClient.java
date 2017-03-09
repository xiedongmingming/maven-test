package org.xiem.com.session;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HeaderIterator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.cookie.CookieSpecProvider;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.impl.cookie.BestMatchSpecFactory;
import org.apache.http.impl.cookie.BrowserCompatSpecFactory;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

@SuppressWarnings("deprecation")
public class TestHttpClient {// HTTPCLIENT4.X使用COOKIE保持会话

	// HTTPCLIENT4.X可以自带维持会话功能:
	// 只要使用同一个HTTPCLIENT且未关闭连接则可以使用相同会话来访问其他要求登录验证的服务(见LOGIN()方法中的"执行GET请求)部分).
	// 如果需要使用HTTPCLIENT池并且想要做到一次登录的会话供多个HttpClient连接使用，就需要自己保存会话信息。因为客户端的会话信息是保存在cookie中的（JSESSIONID），所以只需要将登录成功返回的cookie复制到各个HttpClient使用即可。
	// 使用Cookie的方法有两种，可以自己使用CookieStore来保存（见TestCookieStore()方法），也可以通过HttpClientContext上下文来维持（见TestContext()方法）。
	// 附带HttpClient4.3示例代码 http://www.myexception.cn/program/1459749.html 。

	static CookieStore cookieStore = null;// 创建COOKIESTORE实例

	static HttpClientContext context = null;

	String loginUrl = "http://127.0.0.1:8080/CwlProClient/j_spring_security_check";
	String testUrl = "http://127.0.0.1:8080/CwlProClient/account/querySubAccount.action";
	String loginErrorUrl = "http://127.0.0.1:8080/CwlProClient/login/login.jsp";

	public void Login() throws Exception {

		// HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
		// CloseableHttpClient client = httpClientBuilder.build();

		CloseableHttpClient client = HttpClients.createDefault();// 直接创建CLIENT

		HttpPost httpPost = new HttpPost(loginUrl);

		Map<String, String> parameterMap = new HashMap<String, String>();
		parameterMap.put("j_username", "sunb012");
		parameterMap.put("j_password", "sunb012");

		UrlEncodedFormEntity postEntity = new UrlEncodedFormEntity(getParam(parameterMap), "UTF-8");// 首先转换成NAMEPAIR

		httpPost.setEntity(postEntity);

		System.out.println("请求行为:" + httpPost.getRequestLine());

		try {

			HttpResponse httpResponse = client.execute(httpPost);

			String location = httpResponse.getFirstHeader("Location").getValue();

			if (location != null && location.startsWith(loginErrorUrl)) {
				System.out.println("----loginError");
			}

			printResponse(httpResponse);

			// 执行get请求
			System.out.println("----the same client");
			HttpGet httpGet = new HttpGet(testUrl);
			System.out.println("request line:" + httpGet.getRequestLine());
			HttpResponse httpResponse1 = client.execute(httpGet);
			printResponse(httpResponse1);

			setCookieStore(httpResponse);
			setContext();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				client.close();// 关闭流并释放资源
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void testContext() throws Exception {
		System.out.println("----testContext");
		// 使用context方式
		CloseableHttpClient client = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(testUrl);
		System.out.println("request line:" + httpGet.getRequestLine());
		try {
			// 执行get请求
			HttpResponse httpResponse = client.execute(httpGet, context);
			System.out.println("context cookies:" + context.getCookieStore().getCookies());
			printResponse(httpResponse);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				// 关闭流并释放资源
				client.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void testCookieStore() throws Exception {
		System.out.println("----testCookieStore");
		// 使用cookieStore方式
		CloseableHttpClient client = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
		HttpGet httpGet = new HttpGet(testUrl);
		System.out.println("request line:" + httpGet.getRequestLine());
		try {
			// 执行get请求
			HttpResponse httpResponse = client.execute(httpGet);
			System.out.println("cookie store:" + cookieStore.getCookies());
			printResponse(httpResponse);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				// 关闭流并释放资源
				client.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void printResponse(HttpResponse httpResponse) throws ParseException, IOException {

		HttpEntity entity = httpResponse.getEntity();// 获取响应消息实体

		System.out.println("状态: " + httpResponse.getStatusLine());// 响应状态

		System.out.println("头部: ");

		HeaderIterator iterator = httpResponse.headerIterator();
		while (iterator.hasNext()) {
			System.out.println("\t" + iterator.next());
		}

		if (entity != null) {// 判断响应实体是否为空

			String responseString = EntityUtils.toString(entity);

			System.out.println("response length: " + responseString.length());
			System.out.println("response content: " + responseString.replace("\r\n", ""));
		}
	}

	public static void setContext() {

		System.out.println("----setContext");

		context = HttpClientContext.create();

		Registry<CookieSpecProvider> registry = RegistryBuilder.<CookieSpecProvider>create()
				.register(CookieSpecs.BEST_MATCH, new BestMatchSpecFactory())
				.register(CookieSpecs.BROWSER_COMPATIBILITY, new BrowserCompatSpecFactory()).build();

		context.setCookieSpecRegistry(registry);
		context.setCookieStore(cookieStore);
	}

	public static void setCookieStore(HttpResponse httpResponse) {

		System.out.println("----setCookieStore");

		cookieStore = new BasicCookieStore();

		// JSESSIONID
		String setCookie = httpResponse.getFirstHeader("Set-Cookie").getValue();
		String JSESSIONID = setCookie.substring("JSESSIONID=".length(), setCookie.indexOf(";"));

		System.out.println("JSESSIONID:" + JSESSIONID);
		// 新建一个Cookie
		BasicClientCookie cookie = new BasicClientCookie("JSESSIONID", JSESSIONID);
		cookie.setVersion(0);
		cookie.setDomain("127.0.0.1");
		cookie.setPath("/CwlProClient");

		// cookie.setAttribute(ClientCookie.VERSION_ATTR, "0");
		// cookie.setAttribute(ClientCookie.DOMAIN_ATTR, "127.0.0.1");
		// cookie.setAttribute(ClientCookie.PORT_ATTR, "8080");
		// cookie.setAttribute(ClientCookie.PATH_ATTR, "/CwlProWeb");

		cookieStore.addCookie(cookie);
	}

	public static List<NameValuePair> getParam(Map<String, String> parameterMap) {

		List<NameValuePair> param = new ArrayList<NameValuePair>();

		Iterator<Entry<String, String>> it = parameterMap.entrySet().iterator();

		while (it.hasNext()) {

			Entry<?, ?> parmEntry = it.next();

			param.add(new BasicNameValuePair((String) parmEntry.getKey(), (String) parmEntry.getValue()));
		}

		return param;
	}
	// // HttpClient4.3使用总结 1. Get
	//
	// public static String getResultWithGet(HttpServletRequest request, String
	// url) throws Exception {
	// String result = null;
	// HttpClient client = getClient();
	// try {
	// HttpGet get = new HttpGet(url);
	// HttpResponse response = client.execute(get);
	// result = getResponseBodyAsString(response);
	// } finally {
	// client.getConnectionManager().shutdown();
	// }
	// return result;
	// }
	//
	// // 2、Post
	//
	// public static String getResultWithPost(HttpServletRequest request, String
	// url) throws Exception {
	// String json = null;
	// HttpClient client = getClient();
	// try {
	// HttpPost post = new HttpPost(url);
	// @SuppressWarnings("unchecked")
	// Map<String, String[]> map = request.getParameterMap();
	// Set<String> keySet = map.keySet();
	// JSONObject jo = new JSONObject();
	// for (String s : keySet) {
	// if (!"".equals(map.get(s)[0])) {
	// jo.element(s, map.get(s)[0]);
	// }
	//
	// }
	// StringEntity reqEntity = new StringEntity(jo.toString(), "UTF-8");
	// reqEntity.setContentType("application/json");
	// post.setEntity(reqEntity);
	// HttpResponse response = client.execute(post);
	// json = getResponseBodyAsString(response);
	// } finally {
	// client.getConnectionManager().shutdown();
	// }
	// return json;
	// }
	//
	// // 3、Put
	//
	// public static String getResultWithPut(HttpServletRequest request, String
	// url) throws Exception{
	// String json = null;
	// HttpClient client =getClient();
	// try{
	// HttpPut put = new HttpPut(url);
	// @SuppressWarnings("unchecked")
	// Map<String, String[]> map = request.getParameterMap();
	// Set<String> keySet = map.keySet();
	// JSONObject jo = new JSONObject();
	// for(String s : keySet){
	// if(!"".equals(map.get(s)[0])){
	// jo.element(s, map.get(s)[0]);
	// }
	//
	// }
	// StringEntity reqEntity = new StringEntity(jo.toString(),"UTF-8");
	// reqEntity.setContentType("application/json");
	// put.setEntity(reqEntity);
	// HttpResponse response = client.execute(put);
	// json = getResponseBodyAsString(response);
	// }finally{
	// client.getConnectionManager().shutdown();
	// }
	// return json;
	// }
	//
	// // 4、Delete
	//
	// public static String getResultWithDelete(HttpServletRequest request,
	// String url) throws Exception {
	// String result = null;
	// HttpClient client = getClient();
	// try {
	// HttpDelete delete = new HttpDelete(url);
	// HttpResponse response = client.execute(delete);
	// result = getResponseBodyAsString(response);
	// } finally {
	// client.getConnectionManager().shutdown();
	// }
	// return result;
	// }
	//
	// // 5、getResponseBodyAsString
	//
	// public static String getResponseBodyAsString(HttpResponse response)
	// throws Exception {
	// StringBuilder sb = new StringBuilder();
	// HttpEntity httpEntity = response.getEntity();
	// if (httpEntity != null) {
	// httpEntity = new BufferedHttpEntity(httpEntity);
	// InputStream is = httpEntity.getContent();
	// BufferedReader br = new BufferedReader(new InputStreamReader(is,
	// "UTF-8"));
	// String str;
	// while ((str = br.readLine()) != null) {
	// sb.append(str);
	// }
	// is.close();
	// }
	// return sb.toString();
	// }
	//
	// // 6、文件上传
	//
	// public String uploadAttachment(HttpServletRequest request) {
	// String json = null;
	// HttpClient client = TicketUtils.getClient();
	// try {
	//
	// HttpPost post = new HttpPost(url);
	//
	// DiskFileItemFactory fac = new DiskFileItemFactory();
	// ServletFileUpload upload = new ServletFileUpload(fac);
	// upload.setHeaderEncoding("UTF-8");
	// @SuppressWarnings("unchecked")
	// List<FileItem> fileList = upload.parseRequest(request);
	// Iterator<FileItem> it = fileList.iterator();
	// List<File> tempFileList = new ArrayList<File>();
	// while (it.hasNext()) {
	// FileItem item = it.next();
	// if (!item.isFormField()) {
	// String fileName = item.getName();
	// if (fileName != null) {
	// File file = new File(fileName);
	// item.write(file);
	// MultipartEntity multipartEntity = new
	// MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE,
	// null, Charset.forName("UTF-8"));
	// FileBody fileBody = new FileBody(file);
	// multipartEntity.addPart(fileName, fileBody);
	// post.setEntity(multipartEntity);
	// tempFileList.add(file);
	// }
	// }
	// }
	// HttpResponse response = client.execute(post);
	// json = TicketUtils.getResponseBodyAsString(response);
	// // delete temp files
	// for (File file : tempFileList) {
	// file.delete();
	// }
	// } catch (Exception e) {
	// log.error(e);
	// json = JsonUtil.getJsonString(Const.ERROR_MESSAGE, EM.TICKET_EXCEPTION);
	// } finally {
	// client.getConnectionManager().shutdown();
	// }
	// return json;
	// }
	//
	// // 7、文件下载
	// public void downloadAttachment(HttpServletRequest request,
	// HttpServletResponse response,
	// @PathVariable("fileId") Integer fileId) {
	// HttpClient client = TicketUtils.getClient();
	// try {
	// HttpGet get = new HttpGet(urlStr);
	//
	// ResponseHandler<byte[]> handler = new ResponseHandler<byte[]>() {
	// public byte[] handleResponse(HttpResponse response) throws
	// ClientProtocolException, IOException {
	// HttpEntity entity = response.getEntity();
	// if (entity != null) {
	// return EntityUtils.toByteArray(entity);
	// } else {
	// return null;
	// }
	// }
	// };
	// byte[] charts = client.execute(get, handler);
	//
	// URL url = new URL(urlStr);
	// HttpURLConnection uc = (HttpURLConnection) url.openConnection();
	// response.reset();
	// response.addHeader("Content-disposition",
	// uc.getHeaderField("Content-disposition"));
	// OutputStream output = new
	// BufferedOutputStream(response.getOutputStream());
	// output.write(charts);
	// output.flush();
	// output.close();
	// get.releaseConnection();
	// } catch (Exception e) {
	// log.error(e);
	// } finally {
	// client.getConnectionManager().shutdown();
	// }
	// }
	public static void main(String[] args) {

	}
}