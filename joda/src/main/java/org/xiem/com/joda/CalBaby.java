package org.xiem.com.joda;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class CalBaby {

	private final static String birthday = "2012-3-10 08:20:55";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		while (true) {
			String format1 = "yyyy-MM-dd";
			String format2 = "yyyy-MM-dd HH:mm:ss";
			@SuppressWarnings("resource")
			Scanner s = new Scanner(System.in);
			System.out.println("########################################");
			cutTwoDateToDay(convertToDate1(birthday, format2), new Date(), false);
			System.out.println("请选择操作");
			System.out.println("请输入日期(格式例如:2012-11-08)");
			System.out.println("########################################");
			String endDateStr = s.nextLine();
			Date endDate = convertToDate1(endDateStr, format1);
			if (endDate == null) {
				System.out.println("输入格式错误!请重新输入.");
				continue;
			}
			boolean inputFlag = true;
			cutTwoDateToDay(convertToDate1(birthday, format2), endDate, inputFlag);
		}

	}

	/**
	 * 计算两个日期之间的差距天数
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static void cutTwoDateToDay(Date beginDate, Date endDate, boolean inputFlag) {
		Calendar calendar = Calendar.getInstance();
		long intervalDays = 0;
		calendar.setTime(beginDate);
		long begin = calendar.getTimeInMillis();
		calendar.setTime(endDate);
		long end = calendar.getTimeInMillis();
		long totalM = end - begin;
		System.out.println((end - begin));
		System.out.println(24 * 60 * 60 * 1000);
		intervalDays = totalM / (24 * 60 * 60 * 1000);
		long intervalHours = (totalM - (intervalDays * 24 * 60 * 60 * 1000)) / (60 * 60 * 1000);
		long intervalMin = (totalM - intervalDays * (24 * 60 * 60 * 1000) - intervalHours * 60 * 60 * 1000)
				/ (60 * 1000);
		if (inputFlag) {
			if (totalM > 0L && totalM % (24 * 60 * 60 * 1000) > 0L) {
				intervalDays = intervalDays + 1;
			}
			System.out.println("宝宝从出生到" + formatDate(endDate, "yyyy-MM-dd") + "已经" + intervalDays + "天了");
		} else {
			System.out.println("宝宝来到这个世界已经" + intervalDays + "天" + intervalHours + "小时" + intervalMin + "分钟了");
		}

	}

	/**
	 * 将字符串日期转换为Date yyyy-MM-dd HH:mm:ss yyyy-MM-dd
	 * 
	 * @param s
	 * @return
	 */
	public static Date convertToDate1(String s, String format) {

		if (s == null) {
			return null;
		}
		try {
			SimpleDateFormat df = new SimpleDateFormat(format);
			return df.parse(s);
		} catch (Exception e) {
			return null;
		}
	}

	public static String formatDate(Date date, String strType) {
		if (date == null) {
			return null;
		}

		SimpleDateFormat sdf = new SimpleDateFormat(strType);
		try {
			return sdf.format(date);
		} catch (Exception e) {
			return null;
		}
	}
}