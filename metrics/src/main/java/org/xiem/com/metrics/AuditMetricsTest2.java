package org.xiem.com.metrics;

import java.net.InetSocketAddress;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.codahale.metrics.Counter;
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

public class AuditMetricsTest2 {

	public static final String METRICS_GRAPHITE_HOST = "graphite1.t1.shadc.yosemitecloud.com";
	public static final String METRICS_GRAPHITE_PREFIX = "rts.shadc-t1.rts4-t1-shadc-yosemitecloud-com";

	public static final int METRICS_GRAPHITE_PORT = 2003;

	private static final MetricRegistry instance;

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

	public static void addGraphiteReporter(String host, int port, String prefix) {
		final Graphite graphite = new Graphite(new InetSocketAddress(host, port));
		final GraphiteReporter reporter = GraphiteReporter.forRegistry(Metrics.getInstance()).prefixedWith(prefix)
				.convertRatesTo(TimeUnit.SECONDS).convertDurationsTo(TimeUnit.MILLISECONDS).filter(MetricFilter.ALL)
				.build(graphite);
		reporter.start(5, TimeUnit.SECONDS);
	}

	public static void addLogReporter(String prefix) {
		final Slf4jReporter reporter = Slf4jReporter.forRegistry(Metrics.getInstance()).convertRatesTo(TimeUnit.SECONDS)
				.convertDurationsTo(TimeUnit.MILLISECONDS).filter(MetricFilter.ALL).build();
		reporter.start(5, TimeUnit.SECONDS);
	}

	public static Integer GetAndClean(Map<String, Integer> counters, String key) {
		Integer result = counters.containsKey(key) ? counters.get(key) : 0;
		counters.put(key, 0);
		return result;
	}

	public static int counter = 0;

	public static void main(String[] args) {

		Metrics.addGraphiteReporter(METRICS_GRAPHITE_HOST, METRICS_GRAPHITE_PORT, METRICS_GRAPHITE_PREFIX);

		final MetricRegistry metrics = Metrics.getInstance();

		final Random rand = new Random();

		final ExecutorService executor = Executors.newCachedThreadPool();//
		
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {//
			public void run() {
				executor.shutdownNow();
				System.out.println("shutdown");
			}
		}));
		
		CronJobManager.runPeriodically(CronJobManager.CronJob.RELOAD_DB_CACHE, new Runnable() {
			public void run() {
				String timestring = new Date().toString();
				Counter counterAll = metrics.counter("audit couter all " + timestring);
				Counter counterNotStart = metrics.counter("audit couter notstart " + timestring);
				Counter counterRestart = metrics.counter("audit couter restart " + timestring);
				Counter counterInprogress = metrics.counter("audit couter inprogress " + timestring);
				Counter counterSuccess = metrics.counter("audit couter success " + timestring);
				long count = (long) (rand.nextDouble() * 100);
				System.out.println("计数值为: " + count);
				counterAll.inc(count + 1);
				counterNotStart.inc(count + 2);
				counterRestart.inc(count + 3);
				counterInprogress.inc(count + 4);
				counterSuccess.inc(count + 5);
			}
		}, 0, 30, TimeUnit.SECONDS);
	}
}
