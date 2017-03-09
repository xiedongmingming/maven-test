package org.xiem.com.crypt;
import      java.math.BigInteger;
import java.security.InvalidKeyException;
import      java.security.MessageDigest;
import      java.security.NoSuchAlgorithmException;

import      javax.crypto.KeyGenerator;
import      javax.crypto.Mac;
import      javax.crypto.SecretKey;
import      javax.crypto.spec.SecretKeySpec;

import      org.apache.commons.codec.binary.Base64;
public class MainTest {

	// JAVA(计算机)常见加密算法详解
	// 来源: http://blog.csdn.net/janronehoo/article/category/1152295
	//
	// 如基本的单向加密算法:
	// 		BASE64(严格地说属于编码格式而非加密算法)
	// 		MD5(信息摘要算法)
	// 		SHA(安全散列算法)
	// 		HMAC(散列消息鉴别码)
	//
	// 复杂的对称加密(DES、PBE)、非对称加密算法:
	// 		DES(数据加密算法)
	// 		PBE(基于密码验证)
	// 		RSA
	// 		DH(DIFFIE-HELLMAN算法:密钥一致协议)
	//		DSA(数字签名)
	// 		ECC(椭圆曲线密码编码学)
	//
	// 本篇内容简要介绍BASE64、MD5、SHA、HMAC几种加密算法
	// BASE64编码算法不算是真正的加密算法.MD5、SHA、HMAC这三种加密算法可谓是非可逆加密,就是不可解密的加密方法,我们称之为单向加密算法.我们通常只把他们作为加密的基础.单纯的以上三种的加密并不可靠.
	//
	// BASE64
	// 按照RFC2045的定义，Base64被定义为：Base64内容传送编码被设计用来把任意序列的8位字节描述为一种不易被人直接识别的形式。（The
	// Base64 Content-Transfer-Encoding is designed to represent arbitrary
	// sequences of octets in a form that need not be humanly readable.）
	// 常见于邮件、http加密，截取http信息，你就会发现登录操作的用户名、密码字段通过BASE64加密的。
	//
	//
	//
	// 主要就是BASE64Encoder、BASE64Decoder两个类，我们只需要知道使用对应的方法即可。另，BASE加密后产生的字节位数是8的倍数，如果不够位数以=符号填充。
	//
	// sun不推荐使用它们自己的base64,所以用apache的挺好！
	//
	//
	// MD5
	// MD5 -- message-digest algorithm 5
	// （信息-摘要算法）缩写，广泛用于加密和解密技术，常用于文件校验。校验？不管文件多大，经过MD5后都能生成唯一的MD5值。好比现在的ISO校验，都是MD5校验。怎么用？当然是把ISO经过MD5后产生MD5的值。一般下载linux-ISO的朋友都见过下载链接旁边放着MD5的串。就是用来验证文件是否一致的。
	//
	//
	//
	// 通常我们不直接使用上述MD5加密。通常将MD5产生的字节数组交给BASE64再加密一把，得到相应的字符串。
	//
	//
	// SHA
	// SHA(Secure Hash
	// Algorithm，安全散列算法），数字签名等密码学应用中重要的工具，被广泛地应用于电子商务等信息安全领域。虽然，SHA与MD5通过碰撞法都被破解了，但是SHA仍然是公认的安全加密算法，较之MD5更为安全。
	//
	//
	//
	// HMAC
	// HMAC(Hash Message Authentication
	// Code，散列消息鉴别码，基于密钥的Hash算法的认证协议。消息鉴别码实现鉴别的原理是，用公开函数和密钥产生一个固定长度的值作为认证标识，用这个标识鉴别消息的完整性。使用一个密钥生成一个固定大小的小数据块，即MAC，并将其加入到消息中，然后传输。接收方利用与发送方共享的密钥进行鉴别认证等。
	//
	//
	//
	// BASE64的加密解密是双向的，可以求反解。
	// MD5、SHA以及HMAC是单向加密，任何数据加密后只会产生唯一的一个加密串，通常用来校验数据在传输过程中是否被修改。其中HMAC算法有一个密钥，增强了数据传输过程中的安全性，强化了算法外的不可控因素。[img]http://www.iteye.com/images/smiles/icon_biggrin.gif&quot;
	// alt=&quot;[/img]
	// 单向加密的用途主要是为了校验数据在传输过程中是否被修改。


	public static final String KEY_SHA = "SHA";
	public static final String KEY_MD5 = "MD5";
	public static final String KEY_MAC = "HmacMD5";
	 
	 
	// sun不推荐使用它们自己的base64,用apache的挺好     
	/**
	 * BASE64解密
	 */
	public static byte[] decryptBASE64(byte[] dest) {
		if (dest == null) {
			return null;
		}
		return Base64.decodeBase64(dest);
	}

	/**
	 * BASE64加密
	 */
	public static byte[] encryptBASE64(byte[] origin) {
		if (origin == null) {
			return null;
		}
		return Base64.encodeBase64(origin);
	}

	/**
	 * MD5加密
	 *
	 * @throws NoSuchAlgorithmException
	 */
	public static byte[] encryptMD5(byte[] data) throws NoSuchAlgorithmException {
		if (data == null) {
			return null;
		}
		MessageDigest md5 = MessageDigest.getInstance(KEY_MD5);
		md5.update(data);
		return md5.digest();
	}

	/**
	 * SHA加密
	 *
	 * @throws NoSuchAlgorithmException
	 */
	public static byte[] encryptSHA(byte[] data) throws NoSuchAlgorithmException {
		if (data == null) {
			return null;
	}
		MessageDigest sha = MessageDigest.getInstance(KEY_SHA);
		sha.update(data);
		return sha.digest();
	}

	/**
	 * 初始化HMAC密钥
	 *
	 * @throws NoSuchAlgorithmException
	 */
	public static String initMacKey() throws NoSuchAlgorithmException {
		KeyGenerator keyGenerator = KeyGenerator.getInstance(KEY_MAC);
		SecretKey secretKey = keyGenerator.generateKey();
		return new String(encryptBASE64(secretKey.getEncoded()));
	}

	/**
	 * HMAC加密
	 *
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 */
	public static byte[] encryptHMAC(byte[] data, String key) throws NoSuchAlgorithmException, InvalidKeyException {
		SecretKey secretKey = new SecretKeySpec(decryptBASE64(key.getBytes()), KEY_MAC);
		Mac mac = Mac.getInstance(secretKey.getAlgorithm());
		mac.init(secretKey);
		return mac.doFinal(data);

	}

	public static void main(String[] args) throws Exception {
	// TODO      Auto-generated method stub
		String data = "简单加密";
		System.out.println(new BigInteger(encryptBASE64(data.getBytes())).toString(16));
		System.out.println(new BigInteger(encryptBASE64(data.getBytes())).toString(32));
		System.out.println(new String(decryptBASE64(encryptBASE64(data.getBytes()))));
		System.out.println(new BigInteger(encryptMD5(data.getBytes())).toString());
		System.out.println(new BigInteger(encryptSHA(data.getBytes())).toString());
		System.out.println(new BigInteger(encryptHMAC(data.getBytes(), initMacKey())).toString());
	}

}
