package org.xiem.com;

import java.util.List;

@SuppressWarnings("unused")
public class CookieTest {// 2.4 COOKIES(仅PC流量使用)


	public static void CookieBatchAdd() {// 2.4.1 批量添加COOKIE
		// 一次请求只能处理20个COOKIE,请求频次限制为小于10次/秒
		String URL = "https://api.es.baidu.com/v1/viewconfig/cookie/batch/add";
	}

	public static void CookieBatchDelete() {// 2.4.2 批量删除COOKIE
		// 一次请求只能处理20个COOKIE,请求频次限制为小于10次/秒
		String URL = "https://api.es.baidu.com/v1/viewconfig/cookie/batch/del";
	}

	public static class APIAdviewCookieBatchAddRequest {
		public List<APIAdviewCookie> cookies;// COOKIE列表
		public static class APIAdviewCookie {
			public String cookieId;// COOKIEID
			public Long expireDate;// COOKIEID有效期,格式参考:1390370647017,即2013-11-04 18:37:17默认自上传日期开始7 天后失效
		}
	}

	public static class APIAdviewCookieBatchAddResponse {
		public List<String> invalidCookies;// 无效COOKIEID列表
		public List<String> missOperateCookies;// 操作失败COOKIEID列表
	}
	public static class APIAdviewCookieBatchDelRequest {
		public List<APIAdviewCookie> cookies;// COOKIE列表
		public static class APIAdviewCookie {
			public String cookieId;// COOKIEID
		}
	}

	public static class APIAdviewCookieBatchDelResponse {
		public List<String> invalidCookies;// 无效COOKIEID列表
		public List<String> missOperateCookies;// 操作失败COOKIEID列表
	}

	public static void main(String[] args) {

	}
}
