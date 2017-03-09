package org.xiem.com.http.cookie;

import java.math.BigInteger;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

public class MainTest {

	// COOKIE常用的字段设置:
	// 名字
	// 值
	// 域
	// 路径???
	// 过期时间

	// public static void main(String[] args) {
//        Cookie cookie = new Cookie(key.code, value);// 名值对
//        if (StringUtils.isBlank(domain)) {// 所在的域
//            cookie.setDomain(store.domain);// 采用请求中对应的域
//        } else {
//            cookie.setDomain(domain);// 设置新的域
//        }
//        cookie.setMaxAge(expiryTime);// 设置存活时间
//        cookie.setPath("/");// 设置路径????
	// }

	public static BigInteger makeKey(short inputType, long inputId) {// 生成KEY--36893488147419103465
		return (BigInteger.valueOf(inputType)).shiftLeft(64).add(BigInteger.valueOf(inputId));
	}

	public static void main(String[] args) {
		System.out.println(makeKey((short) 2, 233L));
		System.out.println();
	}
	public static String getCookieDomain(final HttpServletRequest request) {// 获取COOKIE的域信息

		if (request == null) {
			return "";
		}

		final String domain = request.getServerName();// 表示该请求的域名(就是主机名称如:
														// yinhao.bnmat.cn)

		if (StringUtils.isBlank(domain)) {
			return "";
		}

		int firstDot = domain.indexOf('.');

		if ("localhost".equals(domain) || firstDot < 0
				|| (domain.length() > 0 && Character.isDigit(domain.charAt(domain.length() - 1)))) {
			// 第一个条件表示如果域名是LOCALHOST则直接返回
			// 第二个条件表示????
			// 第三个条件表示有点且最后一个为数值(端口号)
			return domain;
		} else {
			return domain.substring(domain.indexOf("."));// 获取域名(例如: bnmat.cn)
		}
	}
}
