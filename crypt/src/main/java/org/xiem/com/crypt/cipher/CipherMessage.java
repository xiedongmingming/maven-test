package org.xiem.com.crypt.cipher;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;

public class CipherMessage {// 对称加密器

	private String algorithm; // 算法(如DES)
	private String plainText; // 明文

	private Key key; // 根据算法生成对应的密钥

	KeyGenerator keyGenerator;
	Cipher cipher;

	CipherMessage(String alg, String msg) {// 函数进行初始化
		algorithm = alg;
		plainText = msg;
	}

	// *****************************************************************************
	public byte[] CipherMsg() {// 加密函数(将原文加密成密文)

		byte[] cipherText = null;// 加密后生成的字节数组

		try {

			cipher = Cipher.getInstance(algorithm);// 生成CIPHER对象

			cipher.init(Cipher.ENCRYPT_MODE, key); // 操作模式为加密(KEY为密钥)

			cipherText = cipher.doFinal(plainText.getBytes()); // 得到加密后的字节数组

		} catch (Exception e) {
			e.printStackTrace();
		}

		return cipherText;
	}
	public String EncipherMsg(byte[] cipherText, Key k) {// 解密函数(将密文解密回原文)

		byte[] sourceText = null;

		try {
			cipher.init(Cipher.DECRYPT_MODE, k); // 操作模式为解密(K为密钥)

			sourceText = cipher.doFinal(cipherText);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new String(sourceText);

	}
	// *****************************************************************************

	public Key initKey() {// 生成密钥

		try {

			keyGenerator = KeyGenerator.getInstance(algorithm);// 初始化密钥KEY

			keyGenerator.init(56); // 选择DES算法(密钥长度必须为56位)

			key = keyGenerator.generateKey(); // 生成密钥

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return key;
	}

	public Key getKey() {// 获取KEY类型的密钥
		return key;
	}

	public Key getKey(byte[] k) {// 获取KEY类型的密钥

		try {

			cipher.init(Cipher.UNWRAP_MODE, key);

			key = cipher.unwrap(k, algorithm, Cipher.SECRET_KEY);

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return key;
	}


	public byte[] getBinaryKey(Key key) {// 获取包装成BYTE[]类型的密钥--每次都一样

		byte[] bk = null;

		try {

			cipher.init(Cipher.WRAP_MODE, key);

			bk = cipher.wrap(key);

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return bk;
	}
}
