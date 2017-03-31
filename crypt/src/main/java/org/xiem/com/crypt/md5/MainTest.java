package org.xiem.com.crypt.md5;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class MainTest {

	public static void main(String[] args) {

	}

	private static StringBuffer getBeforeSign(TreeMap<String, String> params, StringBuffer orgin) {// 添加参数的封装方法

		if (params == null) {
			return null;
		}

		Map<String, String> treeMap = new TreeMap<String, String>();

		treeMap.putAll(params);

		Iterator<String> iter = treeMap.keySet().iterator();

		while (iter.hasNext()) {

			String name = iter.next();

			orgin.append(name).append(params.get(name));
		}

		return orgin;
	}

	@SuppressWarnings("unused")
	public static String md5Signature(TreeMap<String, String> params, String secret) {

		// 新的MD5签名(首尾放SECRET分配给您的APPSECRET)

		String result = null;

		StringBuffer orgin = getBeforeSign(params, new StringBuffer(secret));
		if (orgin == null) {
			return result;
		}

		orgin.append(secret);

		try {

			MessageDigest md = MessageDigest.getInstance("MD5");

			// result = byte2hex(md.digest(orgin.toString().getBytes("utf-8")));

		} catch (Exception e) {
			throw new java.lang.RuntimeException("sign error !");
		}

		return result;
	}
	public static String md5(String text) {// 对字符串进行MD5加密

		MessageDigest msgDigest = null;

		try {

			msgDigest = MessageDigest.getInstance("MD5");

		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException("system doesn't support md5 algorithm.");
		}

		msgDigest.update(text.getBytes());

		byte[] bytes = msgDigest.digest();

		byte tb;
		char low;
		char high;
		char tmpChar;

		String md5Str = "";

		for (int i = 0; i < bytes.length; i++) {

			tb = bytes[i];

			tmpChar = (char) ((tb >>> 4) & 0x000f);

			if (tmpChar >= 10) {
				high = (char) (('a' + tmpChar) - 10);
			} else {
				high = (char) ('0' + tmpChar);
			}

			md5Str += high;

			tmpChar = (char) (tb & 0x000f);

			if (tmpChar >= 10) {
				low = (char) (('a' + tmpChar) - 10);
			} else {
				low = (char) ('0' + tmpChar);
			}

			md5Str += low;
		}

		return md5Str;
	}
}
