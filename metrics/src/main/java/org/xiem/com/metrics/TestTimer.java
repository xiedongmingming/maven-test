package org.xiem.com.metrics;

import static com.codahale.metrics.MetricRegistry.name;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;

public class TestTimer {

	/**
	 * TIMERS主要是用来统计某一块代码段的执行时间以及其分布情况,具体是基于HISTOGRAMS和METERS来实现的.样例代码如下:
	 */

	private static final MetricRegistry metrics = new MetricRegistry();

	private static ConsoleReporter reporter = ConsoleReporter.forRegistry(metrics).build();

	// private static final Timer requests =
	// metrics.timer(name(TestTimers.class, "request"));//实例化一个METER
	private static final Timer requests = metrics.timer(name(TestTimer.class, "request"));

	public static void handleRequest(int sleep) {
		Timer.Context context = requests.time();
		try {
			Thread.sleep(sleep);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			context.stop();
		}
	}

	public static void main(String[] args) throws InterruptedException {
		reporter.start(3, TimeUnit.SECONDS);
		Random random = new Random();
		while (true) {
			handleRequest(random.nextInt(1000));
		}
	}
}
