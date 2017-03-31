package org.xiem.com.apache.math;

import java.math.BigDecimal;

import org.apache.commons.math.util.MathUtils;

public class MainTest {

	public static void main(String[] args) {
		MathUtils.round((double) 1000000000, 3, BigDecimal.ROUND_HALF_UP);
	}

}
