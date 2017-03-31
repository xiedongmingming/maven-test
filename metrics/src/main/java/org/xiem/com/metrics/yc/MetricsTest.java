package org.xiem.com.metrics.yc;

import java.net.InetSocketAddress;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import org.xiem.com.metrics.Metrics;

import com.codahale.metrics.Metric;
import com.codahale.metrics.MetricFilter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.MetricSet;
import com.codahale.metrics.Slf4jReporter;
import com.codahale.metrics.graphite.Graphite;
import com.codahale.metrics.graphite.GraphiteReporter;
import com.codahale.metrics.jvm.GarbageCollectorMetricSet;
import com.codahale.metrics.jvm.MemoryUsageGaugeSet;
import com.codahale.metrics.jvm.ThreadStatesGaugeSet;
import com.google.common.base.Joiner;

public class MetricsTest {

	private static final MetricRegistry instance;// 容器

	static {

		instance = new MetricRegistry();

		registerAll("jvm.gc", new GarbageCollectorMetricSet());
		registerAll("jvm.memory", new MemoryUsageGaugeSet());
		registerAll("jvm.threads", new ThreadStatesGaugeSet());
	}

	public static MetricRegistry getInstance() {
		return instance;
	}

	public static void registerAll(String prefix, MetricSet set) {

		Joiner dot = Joiner.on(".");

		for (Entry<String, Metric> e : set.getMetrics().entrySet()) {
			instance.register(dot.join(prefix, e.getKey()), e.getValue());
		}
	}

	// ***********************************************************************************
	public static void addGraphiteReporter(String host, int port, String prefix) {// 添加一个到GRAPHITE的REPORT

		final Graphite graphite = new Graphite(new InetSocketAddress(host, port));

		final GraphiteReporter reporter = GraphiteReporter.forRegistry(Metrics.getInstance()).prefixedWith(prefix)
				.convertRatesTo(TimeUnit.SECONDS).convertDurationsTo(TimeUnit.MILLISECONDS).filter(MetricFilter.ALL)
				.build(graphite);

		reporter.start(5, TimeUnit.SECONDS);// 报告周期
	}

	public static void addLogReporter(String prefix) {

		final Slf4jReporter reporter = Slf4jReporter.forRegistry(Metrics.getInstance()).convertRatesTo(TimeUnit.SECONDS)
				.convertDurationsTo(TimeUnit.MILLISECONDS).filter(MetricFilter.ALL).build();

		reporter.start(5, TimeUnit.SECONDS);
	}

	public static void main(String[] args) {

	}

}
