package org.xiem.com.metrics;

import static com.codahale.metrics.MetricRegistry.name;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Counter;
import com.codahale.metrics.MetricRegistry;

public class TestCounter {

	/*
	 * COUNTER是GAUGE的一个特例,维护一个计数器,可以通过INC()和DEC()方法对计数器做修改.使用步骤与GAUGE基本类似,
	 * 在METRICSREGISTRY中提供了静态方法可以直接实例化一个COUNTER.
	 */

	private static final MetricRegistry metrics = new MetricRegistry();

	private static ConsoleReporter reporter = ConsoleReporter.forRegistry(metrics).build();

	/**
	 * 实例化一个COUNTER,同样可以通过如下方式进行实例化再注册进去
	 * 
	 * pendingJobs = new Counter();
	 * metrics.register(MetricRegistry.name(TestCounter.class, "pending-jobs"), pendingJobs);
	 */
	private static Counter pendingJobs = metrics.counter(name(TestCounter.class, "pedding-jobs"));

	// private static Counter pendingJobs = metrics.counter(MetricRegistry.name(TestCounter.class, "pedding-jobs"));

	private static Queue<String> queue = new LinkedList<String>();

	public static void add(String str) {
		pendingJobs.inc();
		queue.offer(str);// 添加
	}

	public String take() {
		pendingJobs.dec();
		return queue.poll();
	}

	public static void main(String[] args) throws InterruptedException {

		reporter.start(3, TimeUnit.SECONDS);

		while (true) {
			add("1");
			Thread.sleep(1000);
		}
	}
}
