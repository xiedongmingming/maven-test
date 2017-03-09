package org.xiem.com.crypt.cipher;

import java.security.Key;

public class TestMain {

	public static void main(String[] args) {

		String algorithm = "DES"; // 定义加密算法(可用 DES、DESede、Blowfish)

		String message = "Hello World. 这是待加密的信息"; // 生成个DES密钥

		CipherMessage cm = new CipherMessage(algorithm, message);

		Key key = cm.initKey();

		byte[] msg = cm.CipherMsg();// 执行加密操作

		System.out.println("加密后的密文为: " + new String(msg));

		System.out.println("获取包裹的秘钥: " + new String(cm.getBinaryKey(key)));
		for (byte b : cm.getBinaryKey(key)) {
			System.out.print(String.format("%x ", b));
		}
		System.out.println("\n >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		System.out.println("KEY: " + key.getFormat());
		System.out.println("KEY: " + key.getAlgorithm());
		for (byte b : key.getEncoded()) {
			System.out.print(String.format("%x ", b));
		}
		System.out.println("\n >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		Key unwarpKey = cm.getKey(cm.getBinaryKey(key));
		System.out.println("KEY: " + unwarpKey.getFormat());
		System.out.println("KEY: " + unwarpKey.getAlgorithm());
		for (byte b : unwarpKey.getEncoded()) {
			System.out.print(String.format("%x ", b));
		}
		System.out.println("\n >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

		System.out.println("解密后的明文为: " + cm.EncipherMsg(msg, key));

	}

}
