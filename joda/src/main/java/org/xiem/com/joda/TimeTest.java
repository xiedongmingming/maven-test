package org.xiem.com.joda;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class TimeTest {// 时间和时区等的测试

	public static void main(String[] args) {
		Test0002();
	}

	public static void Test0001() {

		System.out.println("***********************************************************************");
		System.out.println("设置时区信息:");

		TimeZone localZone = TimeZone.getTimeZone("Asia/Shanghai");

		TimeZone.setDefault(localZone);

		System.out.println("本地时区的名称: " + localZone.getDisplayName());

		System.out.println("***********************************************************************");
		System.out.println("获取所有可用时区信息:");
		for (String ids : TimeZone.getAvailableIDs()) {
			System.out.println(ids);
		}
	}

	@SuppressWarnings("deprecation")
	public static void Test0002() {

		System.out.println("***********************************************************************");
		System.out.println("时间格式: SimpleDateFormat");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 简化版的的时间格式器

		System.out.println(df.format(new Date()));

		System.out.println("***********************************************************************");
		System.out.println("时间格式: Calendar");
		Calendar calendar = Calendar.getInstance();

		System.out.println(calendar.getTime().getTime());
		System.out.println(calendar.getTime().getDate());
		System.out.println(calendar.getTime().getDay());
		System.out.println(calendar.getTime().toLocaleString());

		System.out.println("***********************************************************************");
		System.out.println("时间格式: DateFormat");
		// 1、构建一个格式化器
		// 第一个参数: 日期的格式
		// 第二个参数: 时间的格式
		// 第三个参数: 本地化参数
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM,
				Locale.getDefault());

		dateFormat.setTimeZone(TimeZone.getDefault());// 设置时间的时区

		final String dateTime = dateFormat.format(new Date(System.currentTimeMillis()));

		System.out.println(dateTime);
	}

	public static void Test0003() {

	}
}
