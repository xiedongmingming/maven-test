package org.xiem.com.hbase;

import java.util.Arrays;

import org.apache.hadoop.hbase.util.Bytes;

public class MainTest {

	private static final int SHORT_BYTES = Short.SIZE / 8;// 16

	public static void main(String[] args) {

		String key = "0997D66801EDB60A";
		
        byte[] keyBytes = key.getBytes();

        byte[] saltedKey = new byte[keyBytes.length + SHORT_BYTES];

        Arrays.fill(saltedKey, (byte) 0);

        Bytes.putBytes(saltedKey, SHORT_BYTES, keyBytes, 0, keyBytes.length);

        Bytes.putShort(saltedKey, 0, (short) Arrays.hashCode(saltedKey));

	}

}
