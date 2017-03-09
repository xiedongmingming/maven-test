package org.xiem.com.metrics;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Gauge;
import com.codahale.metrics.JmxReporter;
import com.codahale.metrics.MetricRegistry;

public class TestGauges {// 测试GAUGES--实时统计PENDING状态的JOB个数

	// 实例化一个REGISTRY,最核心的一个模块,相当于一个应用程序的METRICS系统的容器,维护一个MAP
	private static final MetricRegistry metrics = new MetricRegistry();

	private static Queue<String> queue = new LinkedBlockingDeque<String>();// ConcurrentLinkedQueue

	// 各种REPORT:
	// ConsoleReporter
	// Slf4jReporter
	// JmxReporter
	// CsvReporter

	// GraphiteReporter

	private static ConsoleReporter reporter = ConsoleReporter.forRegistry(metrics).build();// 在控制台上打印输出

	public static void main(String[] args) throws InterruptedException {

		reporter.start(3, TimeUnit.SECONDS);

		Gauge<Integer> gauge = new Gauge<Integer>() {// 实例化一个GAUGE
			public Integer getValue() {
				return queue.size();
			}
		};

		metrics.register(MetricRegistry.name(TestGauges.class, "pending-job", "size"), gauge);// 注册到容器中

		JmxReporter jmxReporter = JmxReporter.forRegistry(metrics).build();// 测试JMX
		jmxReporter.start();

		for (int i = 0; i < 20; i++) {// 模拟数据
			queue.add("a");
			Thread.sleep(1000);
		}
	}
}