package org.xiem.com.joda;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.joda.time.DateTime;
import org.joda.time.DateTime.Property;
import org.joda.time.DateTimeFieldType;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.Seconds;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class MainTest {// 对应依赖包:joda-time

	// JODATIME提供了一组JAVA类包用于处理包括ISO8601标准在内的DATE和TIME.
	// 可以利用它把JDK中DATE和CALENDAR类完全替换掉而且仍然能够提供很好的集成.
	//
	// JODATIME主要的特点包括:
	//
	// 1.易于使用:CALENDAR让获取"正常的"的日期变得很困难,使它没办法提供简单的方法,而JODATIME能够直接进行域访问并且索引值1就是代表JANUARY.
	// 2.易于扩展:JDK支持多日历系统是通过CALENDAR的子类来实现,这样就显示的非常笨重而且事实上要实现其它日历系统是很困难的.JODATIME支持多日历系统是通过基于Chronology类的插件体系来实现。
	// 3.提供一组完整的功能:它打算提供所有关系到DATE-TIME计算的功能.JODATIME当前支持8种日历系统,而且在将来还会继续添加,有着比JDK的CALENDAR更好的整体性能等等。
	//
	// 附上几个例子:

	public static void main(String[] args) {
		// Test0000();
		// Test0001();
		Test0002();
		Test0003();
		Test0004();
		Test0005();
		Test0006();
	}

	public static void Test0000() {

		// ****************************************************************************************************

		LocalDateTime startDate = new LocalDateTime("2015-04-01");// 获取指定的日期点

		long timeOffset = Seconds.secondsBetween(startDate, new LocalDateTime()).getSeconds();// 获取当前日期和指定时间点之间的差值

		System.out.println("计算得到的差值是: " + timeOffset);

		// ****************************************************************************************************

		DateTime current = DateTime.now();// 获取到的是一个时间点

		System.out.println(current.getMillis());// 毫秒数

		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println(current.getMillis());// 与上面相同
	}
	@SuppressWarnings("unused")
	public static void Test0001() {

		// ****************************************************************************************************

		DateTimeFieldType dateFieldType001 = DateTimeFieldType.centuryOfEra();// 表示一个时间域
		DateTimeFieldType dateFieldType002 = DateTimeFieldType.clockhourOfDay();// 表示一个时间域
		DateTimeFieldType dateFieldType003 = DateTimeFieldType.clockhourOfHalfday();// 表示一个时间域
		DateTimeFieldType dateFieldType004 = DateTimeFieldType.dayOfMonth();// 表示一个时间域
		DateTimeFieldType dateFieldType005 = DateTimeFieldType.dayOfWeek();// 表示一个时间域
		DateTimeFieldType dateFieldType006 = DateTimeFieldType.dayOfYear();// 表示一个时间域
		DateTimeFieldType dateFieldType007 = DateTimeFieldType.era();// 表示一个时间域
		DateTimeFieldType dateFieldType008 = DateTimeFieldType.halfdayOfDay();// 表示一个时间域
		DateTimeFieldType dateFieldType009 = DateTimeFieldType.hourOfDay();// 表示一个时间域
		DateTimeFieldType dateFieldType010 = DateTimeFieldType.hourOfHalfday();// 表示一个时间域
		DateTimeFieldType dateFieldType011 = DateTimeFieldType.millisOfDay();// 表示一个时间域
		DateTimeFieldType dateFieldType012 = DateTimeFieldType.millisOfSecond();// 表示一个时间域
		DateTimeFieldType dateFieldType013 = DateTimeFieldType.minuteOfDay();// 表示一个时间域
		DateTimeFieldType dateFieldType014 = DateTimeFieldType.minuteOfHour();// 表示一个时间域
		DateTimeFieldType dateFieldType015 = DateTimeFieldType.monthOfYear();// 表示一个时间域
		DateTimeFieldType dateFieldType016 = DateTimeFieldType.secondOfDay();// 表示一个时间域
		DateTimeFieldType dateFieldType017 = DateTimeFieldType.secondOfMinute();// 表示一个时间域
		DateTimeFieldType dateFieldType018 = DateTimeFieldType.weekOfWeekyear();// 表示一个时间域
		DateTimeFieldType dateFieldType019 = DateTimeFieldType.weekyear();// 表示一个时间域
		DateTimeFieldType dateFieldType020 = DateTimeFieldType.weekyearOfCentury();// 表示一个时间域
		DateTimeFieldType dateFieldType021 = DateTimeFieldType.year();// 表示一个时间域
		DateTimeFieldType dateFieldType022 = DateTimeFieldType.yearOfCentury();// 表示一个时间域
		DateTimeFieldType dateFieldType023 = DateTimeFieldType.yearOfEra();// 表示一个时间域

		System.out.println(dateFieldType009.getDurationType());// 返回类型:DurationFieldType
		System.out.println(dateFieldType009.getDurationType().getField(null));// 返回类型(参数为NULL表示ISOCHRONOLOGY):DurationField
		System.out.println(dateFieldType009.getDurationType().getField(null).getMillis(1));// 返回该持续域的毫秒数

		long numMillisInDateType009 = dateFieldType009.getDurationType().getField(null).getMillis(1);
		long numMillisInDateType010 = dateFieldType010.getDurationType().getField(null).getMillis(1);

		System.out.println(numMillisInDateType009);// 表示一个小时的毫秒数
		System.out.println(numMillisInDateType010);// 表示一个小时的毫秒数

		DateTime current = DateTime.now();// 表示当前时间

		Property property = current.property(dateFieldType009);

		System.out.println("属性值为: " + current.toString());
		System.out.println("属性值为: " + property.toString());

		DateTime flooredDate = current.property(dateFieldType009).roundFloorCopy();// 向上取整(整点时间)

		System.out.println("属性值为: " + flooredDate.toString());

		long currOffset = current.getMillis() - flooredDate.getMillis();// 距离整点已经过去的时间

		// long delay = intervalMillis - (currOffset - offsetMillis) %
		// intervalMillis;
		// 要求:
		// 在时间域进行整点划分
		// 上面注释用于计算第一次延迟时间
	}
	public static void Test0002() {// 创建任意时间对象

		// ****************************************************************
		// JDK
		Calendar calendar = Calendar.getInstance();

		calendar.set(2012, Calendar.NOVEMBER, 15, 18, 23, 55);

		System.out.println(calendar.getTime());

		// ****************************************************************
		// JODA-TIME
		DateTime dateTime = new DateTime(2012, 12, 15, 18, 23, 55);

		System.out.println(dateTime.toString());
	}
	public static void Test0003() {// 计算两日期相差的天数

		// ****************************************************************
		// JDK
		Calendar sta001 = Calendar.getInstance();
		sta001.set(2012, Calendar.NOVEMBER, 14);

		Calendar end001 = Calendar.getInstance();
		end001.set(2013, Calendar.NOVEMBER, 15);

		long staTime = sta001.getTimeInMillis();
		long endTime = end001.getTimeInMillis();

		long diff = endTime - staTime;

		int days001 = (int) (diff / 1000 / 3600 / 24);

		System.out.println("JDK计算得到的天数: " + days001);

		// ****************************************************************
		// JODA
		LocalDate sta002 = new LocalDate(2012, 12, 14);
		LocalDate end002 = new LocalDate(2013, 12, 15);

		int days002 = Days.daysBetween(sta002, end002).getDays();

		System.out.println("JODA计算得到的天数: " + days002);
	}
	public static void Test0004() {// 获取18天之后的某天在下个月的当前周的第一天日期
		// JDK
		Calendar current = Calendar.getInstance();
		current.add(Calendar.DAY_OF_MONTH, 18);
		current.add(Calendar.MONTH, 1);

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Date date = current.getTime();

		String dateStr001 = dateFormat.format(date);

		System.out.println(dateStr001);

		// JODA
		String dateStr002 = new DateTime().plusDays(18).plusMonths(1).dayOfWeek().withMinimumValue()
				.toString("yyyy-MM-dd HH:mm:ss");
		System.out.println(dateStr002);
	}
	public static void Test0005() {// 时间格式化
		
		DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
		
		DateTime dateTime = DateTime.parse("2012-12-21 23:22:45", format);// 时间解析
		
		String string_u = dateTime.toString("yyyy/MM/dd HH:mm:ss EE");
		System.out.println(string_u);
		
		String string_c = dateTime.toString("yyyy年MM月dd日 HH:mm:ss EE", Locale.CHINESE);
		System.out.println(string_c);
	}
	public static void Test0006() {// 与JDK互操作(两种时间之间互相转换)

		// 通过JDK时间对象构造

		Date date = new Date();

		DateTime dateTime = new DateTime(date);

		Calendar calendar = Calendar.getInstance();
		dateTime = new DateTime(calendar);

		// JODA各种操作
		dateTime = dateTime.plusDays(1) // 增加天
				.plusYears(1)// 增加年
				.plusMonths(1)// 增加月
				.plusWeeks(1)// 增加星期
				.minusMillis(1)// 减分钟
				.minusHours(1)// 减小时
				.minusSeconds(1);// 减秒数

		// 计算完转换成JDK对象

		@SuppressWarnings("unused")
		Date date2 = dateTime.toDate();

		@SuppressWarnings("unused")
		Calendar calendar2 = dateTime.toCalendar(Locale.CHINA);
	}

	@SuppressWarnings("unused")
	public static void Test0007() {

		DateTime dateTime001 = new DateTime(2012, 12, 13, 18, 23, 55);
		DateTime dateTime002 = new DateTime(2011, 12, 13, 18, 23, 55, 333);

		// 下面就是按照一点的格式输出时间
		String str2 = dateTime001.toString("MM/dd/yyyy hh:mm:ss.SSSa");
		String str3 = dateTime001.toString("dd-MM-yyyy HH:mm:ss");
		String str4 = dateTime001.toString("EEEE dd MMMM, yyyy HH:mm:ssa");
		String str5 = dateTime001.toString("MM/dd/yyyy HH:mm ZZZZ");
		String str6 = dateTime001.toString("MM/dd/yyyy HH:mm Z");

		DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");

		DateTime dateTime003 = DateTime.parse("2012-12-21 23:22:45", format);

		String string_u = dateTime003.toString("yyyy/MM/dd HH:mm:ss EE");
		String string_c = dateTime003.toString("yyyy年MM月dd日 HH:mm:ss EE", Locale.CHINESE);
		System.out.println(string_u);
		System.out.println(string_c);

		DateTime dt1 = new DateTime();// 取得当前时间
		DateTime dt2 = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").parseDateTime("2012-12-26 03:27:39");

		// 计算两个日期间隔的天数
		LocalDate start = new LocalDate(2012, 12, 14);
		LocalDate end = new LocalDate(2013, 01, 15);
		int days = Days.daysBetween(start, end).getDays();

		// 计算两个日期间隔的小时数,分钟数,秒数

		// 增加日期
		DateTime dateTime004 = DateTime.parse("2012-12-03");
		dateTime004 = dateTime004.plusDays(30);
		dateTime004 = dateTime004.plusHours(3);
		dateTime004 = dateTime004.plusMinutes(3);
		dateTime004 = dateTime004.plusMonths(2);
		dateTime004 = dateTime004.plusSeconds(4);
		dateTime004 = dateTime004.plusWeeks(5);
		dateTime004 = dateTime004.plusYears(3);

		dateTime001 = dateTime001.plusDays(1) // 增加天
				.plusYears(1)// 增加年
				.plusMonths(1)// 增加月
				.plusWeeks(1)// 增加星期
				.minusMillis(1)// 减分钟
				.minusHours(1)// 减小时
				.minusSeconds(1);// 减秒数

		// 判断是否闰月
		DateTime dt4 = new DateTime();
		org.joda.time.DateTime.Property month = dt4.monthOfYear();
		System.out.println("是否闰月:" + month.isLeap());

		// 取得3秒前的时间
		DateTime dt5 = dateTime004.secondOfMinute().addToCopy(-3);
		dateTime004.getSecondOfMinute();// 得到整分钟后过的秒钟数
		dateTime004.getSecondOfDay();// 得到整天后过的秒钟数
		dateTime004.secondOfMinute();// 得到分钟对象例如做闰年判断等使用

		// DateTime与java.util.Date对象,当前系统TimeMillis转换
		DateTime dt6 = new DateTime(new Date());
		Date date = dateTime004.toDate();
		DateTime dt7 = new DateTime(System.currentTimeMillis());
		dateTime004.getMillis();

		Calendar calendar = Calendar.getInstance();
		dateTime001 = new DateTime(calendar);
	}

}
