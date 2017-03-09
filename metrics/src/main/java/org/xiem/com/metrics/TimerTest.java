package org.xiem.com.metrics;

import java.util.concurrent.TimeUnit;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;

public class TimerTest {

	public static void main(String[] args) throws InterruptedException {

		MetricRegistry metrics = new MetricRegistry();

		ConsoleReporter reporter = ConsoleReporter.forRegistry(metrics).convertRatesTo(TimeUnit.SECONDS)
				.convertDurationsTo(TimeUnit.MILLISECONDS).build();

		// 鏁版嵁缁熻宸ュ叿
		@SuppressWarnings("unused")
		int icounter = 0;

		reporter.start(2, TimeUnit.SECONDS);

		long it = System.currentTimeMillis();

		boolean start = false;

		while (true) {

			icounter++;

			long st = System.currentTimeMillis();

			if (st - it > 10000 && !start) {

				start = true;

				System.out.println("stop metrics");

				reporter.stop();
			}

			// System.out.println("counter = " + icounter);

			// 寮�濮嬬粺璁imer1
			Timer.Context mTimer1 = metrics.timer("timer1").time();// --------鏍囩timer1瀵瑰簲鐨刴Timer1寮�濮嬭鏃�

			Thread.sleep(100);

			// 寮�濮嬬粺璁imer2
			Timer.Context mTimer2 = metrics.timer("timer2").time();// --------鏍囩timer2瀵瑰簲鐨刴Timer2寮�濮嬭鏃�

			Thread.sleep(100);

			// 缁撴潫缁熻timer1
			mTimer1.stop();// --------鏍囩timer1瀵瑰簲鐨刴Timer1璁℃椂缁撴潫

			// 缁撴潫缁熻timer2
			mTimer2.stop();// --------鏍囩timer2瀵瑰簲鐨刴Timer1璁℃椂缁撴潫
		}
	}

}
