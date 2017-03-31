package org.xiem.com.apache.validator;

import org.apache.commons.validator.routines.InetAddressValidator;

public class InetAddressValidatorTest {

	static InetAddressValidator IP_VALIDATOR = new InetAddressValidator(); // IP校验器

	public static boolean isValidIp(final String ip) {

		// TODO: distinguish v4 and v6

		return IP_VALIDATOR.isValid(ip);
	}

	public static void main(String[] args) {

	}

}
