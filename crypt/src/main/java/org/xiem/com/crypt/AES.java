package org.xiem.com.crypt;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class AES {// 对称加密算法

	// 高级加密标准: Advanced Encryption Standard

	// 近些年DES使用越来越少(原因就在于其使用56位密钥比较容易被破解)
	// 近些年来逐渐被AES替代(AES已经变成目前对称加密中最流行算法之一)
	// AES可以使用128、192和256位密钥(并且用128位分组加密和解密数据)

	public static void main(String[] args) throws Exception {

		Test001();

		// Test002();

		Test003();

		// byte[] ceshi2 = toBytes("40dd88e115aab34ffa949dfb245e8e97");
		//
		// for (int i = 0; i < ceshi2.length; i++) {
		// // System.out.print(ceshi2[i] + " ");
		// System.out.format("%x", ceshi2[i]);
		// }
		// System.out.println(new String(ceshi2));
		//
		// String key = "46356afe55fa3cea9cbe73ad442cad47";//
		// �˴�ʹ��AES-128-ECB����ģʽ(KEY��ҪΪ16λ)
		//
		// String src = "1234567890";// ��Ҫ���ܵ��ִ�
		//
		// String enString = AES.Encrypt(src, key);// ����
		//
		// byte[] ceshi = enString.getBytes();
		//
		// for (int i = 0; i < ceshi.length; i++) {
		// System.out.print(ceshi[i] + " ");
		// }
		//
		// String DeString = AES.Decrypt(enString, key);// ����
		// System.out.println("���ܺ���ִ��ǣ�" + DeString);
	}

	private static void Test003() {

		String content = "test";
		String password = "12345678";

		byte[] encryptResult = encrypt(content, password);
		String encryptResultStr = parseByte2HexStr(encryptResult);

		byte[] decryptFrom = parseHexStr2Byte(encryptResultStr);
		byte[] decryptResult = decrypt(decryptFrom, password);

		System.out.println("加密前：" + content);
		System.out.println("加密后：" + encryptResultStr);
		System.out.println("解密后：" + new String(decryptResult));
	}

	@SuppressWarnings("unused")
	private static void Test002() {

		String content = "test";
		String password = "12345678";

		byte[] encryptResult = encrypt(content, password);

		try {
			String encryptResultStr = new String(encryptResult, "UTF-8");
			byte[] decryptResult = decrypt(encryptResultStr.getBytes("UTF-8"), password);

			System.out.println("加密前: " + content);
			System.out.println("解密后: " + new String(decryptResult));

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		// 系统会报错:
		// javax.crypto.IllegalBlockSizeException: Input length must be multiple of 16 when decrypting with padded cipher
		// 		at com.sun.crypto.provider.CipherCore.doFinal(CipherCore.java:934)
		// 		at com.sun.crypto.provider.CipherCore.doFinal(CipherCore.java:845)
		// 		at com.sun.crypto.provider.AESCipher.engineDoFinal(AESCipher.java:446)
		// 		at javax.crypto.Cipher.doFinal(Cipher.java:2165)
		// 		at org.xiem.com.crypt.AES.decrypt(AES.java:149)
		// 		at org.xiem.com.crypt.AES.Test002(AES.java:64)
		// 		at org.xiem.com.crypt.AES.main(AES.java:28)
		// 这主要是因为加密后的BYTE数组是不能强制转换成字符串的(换言之:字符串和BYTE数组在这种情况下不是互逆的;要避免这种情况我们需要做一些修订,可以考
		// 虑将二进制数据转换成十六进制表示,主要有如下两个方法:
		// 1、将二进制转换成16进制
		// 2、将16进制转换为二进制
	}

	public static String parseByte2HexStr(byte buf[]) {// 将二进制转换成16进制

		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < buf.length; i++) {

			String hex = Integer.toHexString(buf[i] & 0xFF);

			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}

		return sb.toString();
	}

	public static byte[] parseHexStr2Byte(String hexStr) {

		if (hexStr.length() < 1) {
			return null;
		}

		byte[] result = new byte[hexStr.length() / 2];

		for (int i = 0; i < hexStr.length() / 2; i++) {

			int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);

			result[i] = (byte) (high * 16 + low);
		}
		return result;
	}

	private static void Test001() {

		String content = "test";
		String password = "12345678";

		byte[] encryptResult = encrypt(content, password);
		byte[] decryptResult = decrypt(encryptResult, password);

		System.out.println("加密前: " + content);
		System.out.println("解密后: " + new String(decryptResult));
	}

	public static byte[] encrypt(String content, String password) { // 加密

		// 第一个参数表示需要加密的内容
		// 第二个参数表示加密密码

		try {

			KeyGenerator kgen = KeyGenerator.getInstance("AES");

			kgen.init(128, new SecureRandom(password.getBytes()));

			SecretKey secretKey = kgen.generateKey();

			byte[] enCodeFormat = secretKey.getEncoded();

			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");

			Cipher cipher = Cipher.getInstance("AES");// 创建密码器

			byte[] byteContent = content.getBytes("utf-8");

			cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化

			byte[] result = cipher.doFinal(byteContent);

			return result; // 加密
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static byte[] encrypt2(String content, String password) {// 另外一种加密方式

		// 这种加密方式有两种限制
		// 1、密钥必须是16位的
		// 2、待加密内容的长度必须是16的倍数.如果不是16的倍数就会出如下异常:
		// javax.crypto.IllegalBlockSizeException: Input length not multiple of 16 bytes
		// 		at com.sun.crypto.provider.SunJCE_f.a(DashoA13*..)
		// 		at com.sun.crypto.provider.SunJCE_f.b(DashoA13*..)
		// 		at com.sun.crypto.provider.SunJCE_f.b(DashoA13*..)
		// 		at com.sun.crypto.provider.AESCipher.engineDoFinal(DashoA13*..)
		// 		at javax.crypto.Cipher.doFinal(DashoA13*..)
		// 要解决如上异常可以通过补全传入加密内容等方式进行避免

		try {

			SecretKeySpec key = new SecretKeySpec(password.getBytes(), "AES");

			Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");

			byte[] byteContent = content.getBytes("UTF-8");

			cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化

			byte[] result = cipher.doFinal(byteContent);

			return result; // 加密

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static byte[] decrypt(byte[] content, String password) {// 解密

		// 第一个参数表示待解密内容
		// 第二个参数表示解密密钥

		// 注意:解密的时候要传入BYTE数组
		try {

			KeyGenerator kgen = KeyGenerator.getInstance("AES");

			kgen.init(128, new SecureRandom(password.getBytes()));

			SecretKey secretKey = kgen.generateKey();

			byte[] enCodeFormat = secretKey.getEncoded();

			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");

			Cipher cipher = Cipher.getInstance("AES");// 创建密码器

			cipher.init(Cipher.DECRYPT_MODE, key);// 初始化

			byte[] result = cipher.doFinal(content);

			return result; // 加密

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String Encrypt(String sSrc, String sKey) throws Exception {// ����

		// ��һ������Ϊ�������ַ���
		// �ڶ�������Ϊ��Կ

		if (sKey == null) {
			System.out.print("KEYΪ��NULL");
			return null;
		}

		if (sKey.length() != 32) {// �ж�KEY�Ƿ�Ϊ32λ
			System.out.print("KEY���Ȳ���32λ");
			return null;
		}

		// byte[] raw = sKey.getBytes("utf-8");
		byte[] raw = toBytes(sKey);

		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");

		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");// "�㷨/ģʽ/���뷽ʽ"

		cipher.init(Cipher.ENCRYPT_MODE, skeySpec);

		byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));

		return new Base64().encodeToString(encrypted);// �˴�ʹ��BASE64��ת�빦�ܣ�ͬʱ����2�μ��ܵ����á�
	}

	public static String Decrypt(String sSrc, String sKey) throws Exception {// ����

		// ��һ������Ϊ�������ַ���
		// �ڶ�������Ϊ��Կ

		try {

			if (sKey == null) {// �ж�KEY�Ƿ���ȷ
				System.out.print("KEYΪ��NULL");
				return null;
			}

			if (sKey.length() != 32) {// �ж�KEY�Ƿ�Ϊ16λ
				System.out.print("Key���Ȳ���16λ");
				return null;
			}

			byte[] raw = toBytes(sKey);// sKey.getBytes("utf-8");

			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");

			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");

			cipher.init(Cipher.DECRYPT_MODE, skeySpec);

			byte[] encrypted1 = new Base64().decode(sSrc);// ����BASE64����

			try {

				byte[] original = cipher.doFinal(encrypted1);

				String originalString = new String(original, "utf-8");

				return originalString;
			} catch (Exception e) {
				System.out.println(e.toString());
				return null;
			}
		} catch (Exception ex) {
			System.out.println(ex.toString());
			return null;
		}
	}

	public static byte[] toBytes(String s) {// ���ַ���ת��Ϊʮ���ֽڵĶ���������

		int len = s.length();

		byte[] data = new byte[len / 2];

		for (int i = 0; i < len; i += 2) {
			data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
		}

		return data;
	}

	public static final char[] HEXCHARS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e',
			'f' };

	public static String asHex(byte[] bytes) {

		char chars[] = new char[bytes.length * 2];

		for (int i = 0; i < chars.length; i = i + 2) {

			byte b = bytes[i / 2];

			chars[i] = HEXCHARS[(b >>> 0x4) & 0xf];
			chars[i + 1] = HEXCHARS[b & 0xf];
		}
		return new String(chars);
	}


}


