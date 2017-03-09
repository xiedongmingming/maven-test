package org.xiem.com.crypt.cipher;

public class MainTest {

	// 算法+模式+填充

	// javax.crypto.Cipher
	// 该类提供加密和解密功能(是JCE框架的核心--JAVA密码扩展)

	// 一、与所有的引擎类一样可以通过调用CIPHER类中的GETINSTANCE静态工厂方法得到CIPHER对象:
	// 		public static Cipher getInstance(String transformation);
	// 		public static Cipher getInstance(String transformation, String provider);
	// 第一个参数描述了由指定输入产生输出所进行的操作或操作集合(该参数总是包含密码学算法名称比如DES--也可以在后面包含模式和填充方式)可以是下列两种形式之一:
	// 		"algorithm/mode/padding"
	// 		"algorithm"
	// 例如下面的例子就是有效的形式:
	//		"DES/CBC/PKCS5Padding"
	//		"DES"
	// 如果没有指定模式或填充方式就使用特定提供者指定的默认模式或默认填充方式.例如SUNJCE提供者使用ECB作为DES、DES-EDE和BLOWFISH等CIPHER的默认模式
	// 并使用PKCS5PADDING作为它们默认的填充方案.这意味着在SUNJCE提供者中下列形式的声明是等价的:
	//		Cipher c1 = Cipher.getInstance("DES/ECB/PKCS5Padding");
	// 		Cipher c1 = Cipher.getInstance("DES");
	// 当以流加密方式请求以块划分CIPHER时可以在模式名后面跟上一次运算需要操作的BIT数目例如采用"DES/CFB8/NOPADDING"和"DES/OFB32/PKCS5PADDING"形式的参数.
	// 如果没有指定数目则使用提供者指定的默认值(例如SUNJCE提供者使用的默认值是64BIT).
	//
	// 工厂方法返回的对象没有进行初始化,因此在使用前必须进行初始化.通过GETINSTANCE得到的CIPHER对象必须使用下列四个模式之一进行初始化,这四个模式在CIPHER类中被定义
	// 为FINAL INTERGER常数,我们可以使用符号名来引用这些模式:
	// 		ENCRYPT_MODE:加密数据
	// 		DECRYPT_MODE:解密数据
	// 		WRAP_MODE:将一个KEY封装成字节(可以用来进行安全传输)
	// 		UNWRAP_MODE:将前述已封装的密钥解开成对象:java.security.Key
	//
	// 每个CIPHER初始化方法使用一个模式参数OPMOD,并用此模式初始化CIPHER对象.此外还有其他参数(包括密钥KEY、包含密钥的证书CERTIFICATE、算法参数和随机源)
	// 我们可以调用以下的INIT方法之一来初始化CIPHER对象:
	//		public void init(int opmod, Key key);
	// 		public void init(int opmod, Certificate certificate);
	// 		public void init(int opmod, Key key, SecureRandom random);
	// 		public void init(int opmod, Certificate certificate, SecureRandom random);
	// 		public void init(int opmod, Key key, AlgorithmParameterSpec params);
	// 		public void init(int opmod, Key key, AlgorithmParameterSpec params, SecureRandom random);
	// 		public void init(int opmod, Key key, AlgorithmParameters params);
	// 		public void init(int opmod, Key key, AlgorithmParameters params, SecureRandom random);
	// 必须指出的是加密和解密必须使用相同的参数.当CIPHER对象被初始化时它将失去以前得到的所有状态.即初始化CIPHER对象与新建一个CIPHER实例然后将它初始化是等价的
	//
	// 二、可以调用以下的DOFINAL方法之一完成单步的加密或解密数据:
	// 		public byte[] doFinal(byte[] input);
	// 		public byte[] doFinal(byte[] input, int inputOffset, int inputLen);
	// 		public int doFinal(byte[] input, int inputOffset, int inputLen, byte[] output);
	// 		public int doFinal(byte[] input, int inputOffset, int inputLen, byte[] output, int outputOffset);
	// 在多步加密或解密数据时首先需要一次或多次调用UPDATE方法用以提供加密或解密的所有数据:
	// 		public byte[] update(byte[] input);
	//		public byte[] update(byte[] input, int inputOffset, int inputLen);
	// 		public int update(byte[] input, int inputOffset, int inputLen, byte[] output);
	// 		public int update(byte[] input, int inputOffset, int inputLen, byte[] output, int outputOffset);
	// 如果还有输入数据,多步操作可以使用前面提到的DOFINAL方法之一结束.如果没有数据,多步操作可以使用下面的DOFINAL方法之一结束:
	// 		public byte[] doFinal();
	// 		public int doFinal(byte[] output, int outputOffset);
	// 如果在TRANSFORMATION参数部分指定了PADDING或UNPADDING方式则所有的DOFINAL方法都要注意所用的PADDING或UNPADDING方式
	// 调用DOFINAL方法将会重置CIPHER对象到使用INIT进行初始化时的状态,就是说CIPHER对象被重置,使得可以进行更多数据的加密或解密,至于这两种模式可以在调用INIT时进行指定
	//
	// 三、包裹WRAP密钥必须先使用WRAP_MODE初始化CIPHER对象,然后调用以下方法:
	// 		public final byte[] wrap(Key key);
	// 如果将调用WRAP方法的结果(WRAP后的密钥字节)提供给解包裹UNWRAP的人使用,必须给接收者发送以下额外信息:
	// (1)、密钥算法名称:密钥算法名称可以调用KEY接口提供的GETALGORITHM方法得到
	// 		public String getAlgorithm();
	// (2)、被包裹密钥的类型(Cipher.SECRET_KEY、Cipher.PRIVATE_KEY、Cipher.PUBLIC_KEY)
	// sourcelink: http://bbs.sdu.edu.cn/pc/pccon.php?id=1292&nid=41716&order=&tid=
	// 为了对调用WRAP方法返回的字节进行解包必须先使用UNWRAP_MODE模式初始化CIPHER对象,然后调用以下方法:
	// 		public final Key unwrap(byte[] wrappedKey, String wrappedKeyAlgorithm, int wrappedKeyType));
	// 其中参数WRAPPEDKEY是调用WRAP方法返回的字节,参数WRAPPEDKEYALGORITHM是用来包裹密钥的算法,参数WRAPPEDKEYTYPE是被包裹密钥的类型,该类型必须是三者之一:
	// Cipher.SECRET_KEY、Cipher.PRIVATE_KEY、Cipher.PUBLIC_KEY
	//
	// 四、SUNJCE提供者实现的CIPHER算法使用如下参数
	// (1)、采用CBC、CFB、OFB、PCBC模式的DES、DES-EDE和BLOWFISH算法.它们使用初始化向量IV作为参数.可以使用javax.crypto.spec.IvParameterSpec类并使用给定的IV参数来初始化Cipher对象.
	// （2）PBEWithMD5AndDES使用的参数是一个由盐值和迭代次数组成的参数集合。可以使用javax.crypto.spec.PBEParameterSpec类并利用给定盐值和迭代次数来初始化Cipher对象。
	//
	// 注意:如果使用SealedObject类，就不必为解密运算参数的传递和保存担心。这个类在加密对象内容中附带了密封和加密的参数，可以使用相同的参数对其进行解封和解密。
	//
	// Cipher中的某些update和doFinal方法允许调用者指定加密或解密数据的输出缓存。此时，保证指定的缓存足够大以容纳加密或解密运算的结果是非常重要
	// 的，可以使用Cipher的以下方法来决定输出缓存应该有多大：public int getOutputSize(int inputLen)
	//
	//
	//
	// 实例
	//
	// import java.security.*;
	//
	// import javax.crypto.*;
	//
	// import java.io.*;
	//
	// //对称加密器
	// public class CipherMessage {
	// private String algorithm; // 算法，如DES
	// private Key key; // 根据算法对应的密钥
	// private String plainText; // 明文
	//
	// KeyGenerator keyGenerator;
	// Cipher cipher;
	//
	// // 函数进行初始化
	// CipherMessage(String alg, String msg) {
	// algorithm = alg;
	// plainText = msg;
	// }
	//
	// // 加密函数，将原文加密成密文
	// public byte[] CipherMsg() {
	// byte[] cipherText = null;
	//
	// try {
	// // 生成Cipher对象
	// cipher = Cipher.getInstance(algorithm);
	// // 用密钥加密明文(plainText),生成密文(cipherText)
	// cipher.init(Cipher.ENCRYPT_MODE, key); //
	// 操作模式为加密(Cipher.ENCRYPT_MODE),key为密钥
	// cipherText = cipher.doFinal(plainText.getBytes()); // 得到加密后的字节数组
	// // String str = new String(cipherText);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return cipherText;
	// }
	//
	// // 解密函数，将密文解密回原文
	// public String EncipherMsg(byte[] cipherText, Key k) {
	// byte[] sourceText = null;
	//
	// try {
	// cipher.init(Cipher.DECRYPT_MODE, k); // 操作模式为解密,key为密钥
	// sourceText = cipher.doFinal(cipherText);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return new String(sourceText);
	//
	// }
	//
	// // 生成密钥
	// public Key initKey() {
	// try {
	// // 初始化密钥key
	// keyGenerator = KeyGenerator.getInstance(algorithm);
	// keyGenerator.init(56); // 选择DES算法,密钥长度必须为56位
	// key = keyGenerator.generateKey(); // 生成密钥
	// } catch (Exception ex) {
	// ex.printStackTrace();
	// }
	// return key;
	// }
	//
	//
	//
	// // 获取Key类型的密钥
	// public Key getKey() {
	// return key;
	// }
	//
	// // 获取Key类型的密钥
	// public Key getKey(byte[] k) {
	// try {
	// key = cipher.unwrap(k, algorithm, Cipher.DECRYPT_MODE);
	// } catch (Exception ex) {
	// ex.printStackTrace();
	// }
	// return key;
	// }
	//
	// // 获取密钥包装成byte[]类型的
	// public byte[] getBinaryKey(Key k) {
	// byte[] bk = null;
	// try {
	// bk = cipher.wrap(k);
	// } catch (Exception ex) {
	// ex.printStackTrace();
	// }
	// return bk;
	// }
	// }
	//
	//
	//
	//
	// import java.security.*;
	//
	// import javax.crypto.*;
	//
	// import java.io.*;
	//
	// public class TestMain {
	//
	// public static void main(String[] args) {
	// String algorithm = "DES"; // 定义加密算法,可用 DES,DESede,Blowfish
	// String message = "Hello World. 这是待加密的信息"; // 生成个DES密钥
	// Key key;
	//
	// CipherMessage cm = new CipherMessage(algorithm, message);
	// key = cm.initKey();
	// byte[] msg = cm.CipherMsg();
	// System.out.println("加密后的密文为：" + new String(msg));
	// // System.out.println("密钥为："+new String(cm.getBinaryKey(key)));
	//
	//
	// System.out.println(cm.getBinaryKey(key));
	// System.out.println("解密密文为：" + cm.EncipherMsg(msg, key));
	//
	// }
	//
	// }

	public static void main(String[] args) {

	}

}
