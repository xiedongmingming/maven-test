package org.xiem.com.apache.codec;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.EncoderException;
// import java.io.UnsupportedEncodingException;
import org.apache.commons.codec.net.URLCodec;
// import java.net.URLDecoder;

public class MainTest {

	public static void main(String[] args) {

		URLCodec URL_CODEC = new URLCodec();

		try {
			URL_CODEC.encode("xxx");
			URL_CODEC.decode("xxx");
		} catch (EncoderException | DecoderException e) {
			e.printStackTrace();
		}

		// URL编解码
		// URLDecoder.decode(pair.substring(0, idx),
		// "UTF-8")//表示用指定编码来解码字符串--UnsupportedEncodingException
	}

}
