package org.xiem.com.apache.validator;

import org.apache.commons.validator.routines.UrlValidator;

public class UrlValidatorTest {

	static String[] SCHEMES = { "http", "https" };// 协议

	static UrlValidator URL_VALIDATOR = new UrlValidator(SCHEMES);

	public static boolean isValidUrl(final String url) {
		return URL_VALIDATOR.isValid(url);
	}

	public static void main(String[] args) {

	}
}
