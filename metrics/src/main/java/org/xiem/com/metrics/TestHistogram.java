package org.xiem.com.metrics;

import static com.codahale.metrics.MetricRegistry.name;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Histogram;
import com.codahale.metrics.MetricRegistry;

public class TestHistogram {

	/**
	 * 
	 * HISTOGRAMS主要使用来统计数据的分布情况,最大值、最小值、平均值、中位数，百分比(75%、90%、95%、98%、99%和99.9%).
	 * 例如,需要统计某个页面的请求响应时间分布情况,可以使用该种类型的METRICS进行统计.具体的样例代码如下:
	 * 
	 */

	private static final MetricRegistry metrics = new MetricRegistry();

	private static ConsoleReporter reporter = ConsoleReporter.forRegistry(metrics).build();

	// ExponentiallyDecayingReservoir
	private static final Histogram randomNums = metrics.histogram(name(TestHistogram.class, "random"));// 实例化一个HISTOGRAMS

	public static void handleRequest(double random) {

		randomNums.update((int) (random * 100));// 用于对数据的分布情况进行统计(跟时间没有关系)

	}

	public static void main(String[] args) throws InterruptedException {

		reporter.start(3, TimeUnit.SECONDS);// 表示3秒报告一次

		Random rand = new Random();

		while (true) {

			handleRequest(rand.nextDouble());

			Thread.sleep(100);

		}
	}
}