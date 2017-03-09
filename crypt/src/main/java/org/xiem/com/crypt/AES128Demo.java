package org.xiem.com.crypt;

import java.security.AlgorithmParameters;
import java.security.Key;
import java.security.Security;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AES128Demo {

	// �㷨��
	public static final String KEY_ALGORITHM = "AES";
	// �ӽ����㷨/ģʽ/��䷽ʽ
	// ��������ѡ��Ϊ�˷��������iOS�˵ļ��ܽ��ܣ�����������ͬ��ģʽ����䷽ʽ
	// ECBģʽֻ����Կ���ɶ����ݽ��м��ܽ��ܣ�CBCģʽ��Ҫ���һ������iv
	public static final String CIPHER_ALGORITHM = "AES/CBC/PKCS7Padding";

	// ������Կ
	public static byte[] generateKey() throws Exception {
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		KeyGenerator keyGenerator = KeyGenerator.getInstance(KEY_ALGORITHM);
		keyGenerator.init(128);
		SecretKey key = keyGenerator.generateKey();
		return key.getEncoded();
	}

	// ����iv
	public static AlgorithmParameters generateIV() throws Exception {
		// iv Ϊһ�� 16 �ֽڵ����飬������ú� iOS ��һ���Ĺ��췽��������ȫΪ0
		byte[] iv = new byte[16];
		Arrays.fill(iv, (byte) 0x00);
		AlgorithmParameters params = AlgorithmParameters.getInstance(KEY_ALGORITHM);
		params.init(new IvParameterSpec(iv));
		return params;
	}

	// ת����JAVA����Կ��ʽ
	public static Key convertToKey(byte[] keyBytes) throws Exception {
		SecretKey secretKey = new SecretKeySpec(keyBytes, KEY_ALGORITHM);
		return secretKey;
	}

	// ����
	public static byte[] encrypt(byte[] data, byte[] keyBytes, AlgorithmParameters iv) throws Exception {
		// ת��Ϊ��Կ
		Key key = convertToKey(keyBytes);
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
		// ����Ϊ����ģʽ
		cipher.init(Cipher.ENCRYPT_MODE, key, iv);
		return cipher.doFinal(data);
	}

	// ����
	public static byte[] decrypt(byte[] encryptedData, byte[] keyBytes, AlgorithmParameters iv) throws Exception {
		Key key = convertToKey(keyBytes);
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
		// ����Ϊ����ģʽ
		cipher.init(Cipher.DECRYPT_MODE, key, iv);
		return cipher.doFinal(encryptedData);
	}

	public static void main(String[] args) {

		// ����

		String plainTextString = "Hello,Bouncy Castle";

		System.out.println("���� : " + plainTextString);

		byte[] key;

		try {
			// ��ʼ����Կ

			key = generateKey();
			// ��ʼ��iv

			AlgorithmParameters iv = generateIV();

			System.out.println("��Կ : ");

			// ��ӡ��Կ
			for (int i = 0; i < key.length; i++) {
				System.out.printf("%x", key[i]);
			}

			System.out.println();

			// ���м���
			byte[] encryptedData = encrypt(plainTextString.getBytes(), key, iv);
			// ������ܺ������
			System.out.println("���ܺ������ : ");
			for (int i = 0; i < encryptedData.length; i++) {
				System.out.printf("%x", encryptedData[i]);
			}
			System.out.println();

			// ���н���
			byte[] data = decrypt(encryptedData, key, iv);
			System.out.println("���ܵõ������� : " + new String(data));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}