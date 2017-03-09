package org.xiem.com.metrics;

import static com.codahale.metrics.MetricRegistry.name;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;

public class TestMeter {

	/**
	 * METERS用来度量某个时间段的平均处理次数(REQUEST PER
	 * SECOND),每1、5、15分钟的TPS.比如一个SERVICE的请求数,通过METRICS.METER()实例化一个METER之后,
	 * 然后通过METER.MARK()方法就能将本次请求记录下来.统计结果有总的请求数,平均每秒的请求数,以及最近的1、5、15分钟的平均TPS.
	 * 系统吞吐量(TPS)
	 * 
	 */

	private static final MetricRegistry metrics = new MetricRegistry();//

	private static ConsoleReporter reporter = ConsoleReporter.forRegistry(metrics).build();

	private static final Meter requests = metrics.meter(name(TestMeter.class, "request"));// 实例化一个METER

	public static void handleRequest() {
		requests.mark();// 记录一次请求
	}

	public static void main(String[] args) throws InterruptedException {

		reporter.start(3, TimeUnit.SECONDS);

		Random rand = new Random();

		while (true) {

			handleRequest();

			long randomnum = (long) rand.nextDouble() * 1000;

			Thread.sleep(randomnum);
		}
	}

}
