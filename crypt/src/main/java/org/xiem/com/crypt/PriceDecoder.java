package org.xiem.com.crypt;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import com.google.common.io.BaseEncoding;
import com.yosemitecloud.etl.protobuf.QaxRtbV2_5;

import yc.etl.com.google.protobuf.ByteString;
import yc.etl.com.google.protobuf.InvalidProtocolBufferException;

public class PriceDecoder {

	private static final String ALGORITHM = "AES";// 使用的加密方式
	private static final String ENCRIPTION_KEY = "46356afe55fa3cea9cbe73ad442cad47";// 解密秘钥
	private static final char[] HEXCHARS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	// ********************************************************************************************

	private final String encryptionKey;// 每个DSP有一个唯一的解密密钥(32位16进制字符串)
	private final char paddingchar;// 填充字符(爱奇艺使用了自定义的编码字典表)

	// 构造函数
	public PriceDecoder(final String encryptionKey, final char padchar) {
		this.encryptionKey = encryptionKey;
		this.paddingchar = padchar;
	}

	// ********************************************************************************************
	public String decodePrice(String encryptedValue) {// 解密价格--参数为BASE64编码过的密文

		try {
			return decrypt(encryptedValue, encryptionKey, paddingchar);
		} catch (NumberFormatException e) {
			return "0";
		}
	}

	public String encodePrice(String price) {// 加密价格--返回的是加密字节数组的BASE64编码
		return encrypt(price, encryptionKey, paddingchar);
	}

	// ********************************************************************************************
	public static String decrypt(String encrypted, String token, char paddingchar) {

		// 第一个参数表示BASE64编码过的密文字符串
		// 第二个参数表示加密密钥
		// 第三个参数表示BASE编码填充字符

		try {

			byte[] key = toBytes(token);

			return decryptBase64URLSafe(encrypted, key, paddingchar);

		} catch (Exception e) {
			return null;
		}
	}

	public static String encrypt(String s, String token, char paddingchar) {

		// 第一个参数表示待加密的价格字符串
		// 第二个参数表示加密密钥
		// 第三个参数表示BASE编码填充字符

		try {

			byte[] key = toBytes(token);// 将TOKEN转换为16字节的二进制数据

			return encryptBase64URLSafe(s, key, paddingchar);

		} catch (Exception e) {
			return null;
		}
	}

	// ********************************************************************************************
	private static String encryptBase64URLSafe(String s, byte[] key, char paddingchar) {

		// 第一个参数表示待加密的价格字符串
		// 第二个参数表示加密密钥的字节形式(16位)
		// 第三个参数表示BASE编码填充字符

		BaseEncoding base64 = BaseEncoding.base64Url().omitPadding().withPadChar(paddingchar);

		byte[] encryptedBytes = encryptBytes(s, key);// 先加密

		return base64.encode(encryptedBytes);// 对加密后得到的字节数组进行BASE64编码
	}

	private static String decryptBase64URLSafe(String encrypted, byte[] key, char paddingchar) {

		// 第一个参数表示BASE64编码过的密文字符串
		// 第二个参数表示加密密钥的字节形式(16位)
		// 第三个参数表示BASE编码填充字符

		BaseEncoding base64 = BaseEncoding.base64Url().omitPadding().withPadChar(paddingchar);// 先进行BASE64解码

		byte[] encryptedBytes = base64.decode(encrypted);// 解码得到的密文字节数组

		String decrypted = decryptBytes(encryptedBytes, key);

		return decrypted;// 返回解密后的字符串
	}

	// ********************************************************************************************
	private static byte[] encryptBytes(String s, byte[] key) {// 加密过程

		// 第一个参数表示待加密的价格字符串
		// 第二个参数表示加密密钥的字节形式(16位)
		// 第三个参数表示BASE编码填充字符

		if (key == null) {
			return null;
		}

		if (key.length != 16) {
			return null;
		}

		SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");// 生成秘钥

		try {

			Cipher cipher = Cipher.getInstance(ALGORITHM);

			cipher.init(Cipher.ENCRYPT_MODE, skeySpec);

			byte[] encrypted = cipher.doFinal(s.getBytes("utf-8"));

			return encrypted;

		} catch (Exception e) {
			return null;
		}
	}

	private static String decryptBytes(byte[] encrypted, byte[] key) {// 解密过程

		// 第一个参数为待解密的字节数组
		// 第二个参数为秘钥的字节数组
		// 第三个参数表示BASE编码填充字符

		try {

			if (key == null) {
				return null;
			}

			if (key.length != 16) {
				return null;
			}

			SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");

			Cipher cipher = Cipher.getInstance(ALGORITHM);// 创建密码器

			cipher.init(Cipher.DECRYPT_MODE, skeySpec);// 初始化

			try {

				byte[] original = cipher.doFinal(encrypted);

				String originalString = new String(original, "utf-8");

				return originalString;

			} catch (Exception e) {
				return null;
			}
		} catch (Exception ex) {
			return null;
		}
	}

	// ********************************************************************************************
	public static String asHex(byte[] bytes) {

		char chars[] = new char[bytes.length * 2];

		for (int i = 0; i < chars.length; i = i + 2) {

			byte b = bytes[i / 2];

			chars[i] = HEXCHARS[(b >>> 0x4) & 0xf];
			chars[i + 1] = HEXCHARS[b & 0xf];
		}

		return new String(chars);
	}

	public static byte[] toBytes(String s) {// 将字符串转换为十六字节的二进制数组

		int len = s.length();

		byte[] data = new byte[len / 2];

		for (int i = 0; i < len; i += 2) {
			data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
		}

		return data;
	}

	// ********************************************************************************************

	public static void main(String[] args) throws InvalidProtocolBufferException {

		// 加密算法是AES128位的ECB模式(PADDING方式为PKC)--正常情况下密文会经过BASE64编码后发送(所以需要首先进行BASE64解码)

		// 注:我们使用的BASE64编码字典表是自己定制的(不要误认为跟其他常用的编码表是一致的)

		byte[] test = { 0x40, (byte) 0xdd, (byte) 0x88, (byte) 0xe1, 0x15, (byte) 0xaa, (byte) 0xb3, 0x4f, (byte) 0xfa,
				(byte) 0x94, (byte) 0x9d, (byte) 0xfb, 0x24, 0x5e, (byte) 0x8e, (byte) 0x97 };

		QaxRtbV2_5.BidResponse.Settlement.Builder settlementBiulder = QaxRtbV2_5.BidResponse.Settlement.newBuilder();
		settlementBiulder.setVersion(717171);
		settlementBiulder.setPrice(ByteString.copyFrom(test));

		BaseEncoding base = BaseEncoding.base64Url().omitPadding().withPadChar('!');// 先获取BASE64编解码器

		System.out.println(base.encode(settlementBiulder.build().toByteArray()));// CPPiKxIQQN2I4RWqs0_6lJ37JF6Olw!!

		byte[] bs = base.decode("CPPiKxIQQN2I4RWqs0_6lJ37JF6Olw!!");// 解码上面得到的密文字节数组

		QaxRtbV2_5.BidResponse.Settlement settlement = QaxRtbV2_5.BidResponse.Settlement.parseFrom(bs);
		System.out.println(settlement.toString());

		System.out.println(decryptBytes(settlement.getPrice().toByteArray(), toBytes(ENCRIPTION_KEY)));
	}
}