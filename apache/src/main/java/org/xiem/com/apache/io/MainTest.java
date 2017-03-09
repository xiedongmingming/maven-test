package org.xiem.com.apache.io;

import java.io.InputStream;

import org.apache.commons.io.input.TeeInputStream;
import org.apache.commons.io.output.ByteArrayOutputStream;

public class MainTest {

	@SuppressWarnings({ "resource", "unused" })
	public static void main(String[] args) {
		InputStream is;
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		TeeInputStream tee = new TeeInputStream(System.in, output);
		is = tee;
	}

}
