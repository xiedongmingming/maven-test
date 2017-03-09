package org.xiem.com.apache.lang3;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Joiner;

public class GoogleCommonTest {

	public static void main(String[] args) {

		List<Integer> statusList = new ArrayList<Integer>();
		statusList.add(12);
		statusList.add(424);
		statusList.add(7565);
		statusList.add(7565);
		statusList.add(7565);
		statusList.add(7565);
		statusList.add(7565);

		Joiner dot = Joiner.on(",");
		System.out.println(dot.join(statusList));

		System.out.println(StringUtils.join(statusList, ","));
	}
}
