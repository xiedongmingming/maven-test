package org.xiem.com.metrics;

import java.net.InetSocketAddress;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.codahale.metrics.Metric;
import com.codahale.metrics.MetricFilter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.MetricSet;
import com.codahale.metrics.Slf4jReporter;
import com.codahale.metrics.Timer;
import com.codahale.metrics.graphite.Graphite;
import com.codahale.metrics.graphite.GraphiteReporter;
import com.codahale.metrics.jvm.GarbageCollectorMetricSet;
import com.codahale.metrics.jvm.MemoryUsageGaugeSet;
import com.codahale.metrics.jvm.ThreadStatesGaugeSet;
import com.google.common.base.Joiner;

public class AuditMetricsTest {

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

		// Map<String, Integer> counters = new HashMap<String, Integer>();
		//
		// counters.put("counter1", 0);
		// counters.put("counter2", 0);
		// counters.put("counter3", 0);
		// counters.put("counter4", 0);
		// counters.put("counter5", 0);
		//
		// Gauge<Integer> gauge1 = new Gauge<Integer>() {// 实例化一个GAUGE
		// @Override
		// public Integer getValue() {
		// return GetAndClean(counters, "counter1");
		// }
		// };
		// Gauge<Integer> gauge2 = new Gauge<Integer>() {// 实例化一个GAUGE
		// @Override
		// public Integer getValue() {
		// return GetAndClean(counters, "counter2");
		// }
		// };
		// Gauge<Integer> gauge3 = new Gauge<Integer>() {// 实例化一个GAUGE
		// @Override
		// public Integer getValue() {
		// return GetAndClean(counters, "counter3");
		// }
		// };
		// Gauge<Integer> gauge4 = new Gauge<Integer>() {// 实例化一个GAUGE
		// @Override
		// public Integer getValue() {
		// return GetAndClean(counters, "counter4");
		// }
		// };
		// Gauge<Integer> gauge5 = new Gauge<Integer>() {// 实例化一个GAUGE
		// @Override
		// public Integer getValue() {
		// return GetAndClean(counters, "counter5");
		// }
		// };

		// metrics.register(MetricRegistry.name(AuditMetricsTest.class,
		// "pending-job", "counter1"), gauge1);
		// metrics.register(MetricRegistry.name(AuditMetricsTest.class,
		// "pending-job", "counter2"), gauge2);
		// metrics.register(MetricRegistry.name(AuditMetricsTest.class,
		// "pending-job", "counter3"), gauge3);
		// metrics.register(MetricRegistry.name(AuditMetricsTest.class,
		// "pending-job", "counter4"), gauge4);
		// metrics.register(MetricRegistry.name(AuditMetricsTest.class,
		// "pending-job", "counter5"), gauge5);

		// for (Map.Entry<String, Gauge> entry : metrics.getGauges().entrySet())
		// {
		// System.out.println(entry.getKey());
		// }

		// ConsoleReporter reporter =
		// ConsoleReporter.forRegistry(metrics).convertRatesTo(TimeUnit.SECONDS)
		// .convertDurationsTo(TimeUnit.MILLISECONDS).build();
		//
		// reporter.start(2, TimeUnit.SECONDS);

		// Counter bidunotstart = metrics.counter("advertiser.bidu.notstart");
		// Counter bidurestart = metrics.counter("advertiser.bidu.restart");
		// Counter biduinprogress =
		// metrics.counter("advertiser.bidu.inprogress");
		// Counter bidusuccess = metrics.counter("advertiser.bidu.success");
		//
		// Counter tencentnotstart =
		// metrics.counter("advertiser.tencent.notstart");
		// Counter tencentrestart =
		// metrics.counter("advertiser.tencent.restart");
		// Counter tencentinprogress =
		// metrics.counter("advertiser.tencent.inprogress");
		// Counter tencentsuccess =
		// metrics.counter("advertiser.tencent.success");

		final Random rand = new Random();

		final ExecutorService executor = Executors.newCachedThreadPool();//
		
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {//
			public void run() {
				executor.shutdownNow();
				System.out.println("shutdown");
			}
		}));
		
		CronJobManager.runPeriodically(CronJobManager.CronJob.RELOAD_DB_CACHE, new Runnable() {
			@SuppressWarnings("deprecation")
			public void run() {

				// bidunotstart.dec(bidunotstart.getCount());
				// bidurestart.dec(bidurestart.getCount());
				// biduinprogress.dec(biduinprogress.getCount());
				// bidusuccess.dec(bidusuccess.getCount());
				//
				// tencentnotstart.dec(tencentnotstart.getCount());
				// tencentrestart.dec(tencentrestart.getCount());
				// tencentinprogress.dec(tencentinprogress.getCount());
				// tencentsuccess.dec(tencentsuccess.getCount());

				System.out.println("任务执行时间为: " + new Date().toLocaleString());
				
				Timer.Context timer_all = metrics.timer("audittime_all_new").time();
				// Timer.Context timer_bidu =
				// metrics.timer("audittime_bidu").time();

				long randomnum = (long) ((rand.nextDouble() * 50) + 50);

				System.out.println("随机数值为: " + randomnum);

				try {
					Thread.sleep(randomnum);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				// timer_bidu.stop();
				//
				// Timer.Context timer_tencent =
				// metrics.timer("audittime_tencent").time();
				// randomnum = (long) ((rand.nextDouble() * 50) + 50);
				// try {
				// Thread.sleep(randomnum);
				// } catch (InterruptedException e) {
				// e.printStackTrace();
				// }
				// timer_tencent.stop();
				//
				// counters.put("counter1", counter);
				// counters.put("counter2", counter);
				// counters.put("counter3", counter);
				// counters.put("counter4", counter);
				// counters.put("counter5", counter);
				//
				// System.out.println("当前值为:" + counters.get("counter1") + "," +
				// counters.get("counter2") + ","
				// + counters.get("counter3") + "," + counters.get("counter4") +
				// "," + counters.get("counter5"));
				//
				// counter = counter + 3;
				//
				// bidunotstart.inc(3);
				// bidurestart.inc(3);
				// biduinprogress.inc(3);
				// bidusuccess.inc(3);
				//
				// tencentnotstart.inc(3);
				// tencentrestart.inc(3);
				// tencentinprogress.inc(3);
				// tencentsuccess.inc(3);
				//
				// System.out.println("计数器当前值为:" + bidunotstart.getCount() + ","
				// + bidurestart.getCount() + ","
				// + biduinprogress.getCount() + "," + bidusuccess.getCount());
				// System.out.println("计数器当前值为:" + tencentnotstart.getCount() +
				// "," + tencentrestart.getCount() + ","
				// + tencentinprogress.getCount() + "," +
				// tencentsuccess.getCount());

				timer_all.stop();
			}
		}, 0, 10, TimeUnit.SECONDS);

		// for (;;) {
		//
		// // bidunotstart.dec(bidunotstart.getCount());
		// // bidurestart.dec(bidurestart.getCount());
		// // biduinprogress.dec(biduinprogress.getCount());
		// // bidusuccess.dec(bidusuccess.getCount());
		// //
		// // tencentnotstart.dec(tencentnotstart.getCount());
		// // tencentrestart.dec(tencentrestart.getCount());
		// // tencentinprogress.dec(tencentinprogress.getCount());
		// // tencentsuccess.dec(tencentsuccess.getCount());
		//
		// try {
		// Thread.sleep(10 * 1000);
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// }
		//
		// Timer.Context timer = metrics.timer("audittime").time();
		//
		// // bidunotstart.inc(16);
		// // bidurestart.inc(3);
		// // biduinprogress.inc(11);
		// // bidusuccess.inc(1);
		// //
		// // tencentnotstart.inc(34);
		// // tencentrestart.inc(9);
		// // tencentinprogress.inc(29);
		// // tencentsuccess.inc(30);
		//
		// long randomnum = (long) ((rand.nextDouble() * 50) + 50);
		//
		// System.out.println("随机数值为: " + randomnum);
		//
		// try {
		// Thread.sleep(randomnum);
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// }
		//
		// counters.put("counter1", (int) randomnum % 20);
		// counters.put("counter2", (int) randomnum % 34);
		// counters.put("counter3", (int) randomnum % 45);
		// counters.put("counter4", (int) randomnum % 23);
		// counters.put("counter5", (int) randomnum % 13);
		//
		// // System.out.println("测试的时间为: " + (bidunotstart.getCount()));
		// // System.out.println("测试的时间为: " + (bidurestart.getCount()));
		// // System.out.println("测试的时间为: " + (biduinprogress.getCount()));
		// // System.out.println("测试的时间为: " + (bidusuccess.getCount()));
		// // System.out.println("测试的时间为: " + (tencentnotstart.getCount()));
		// // System.out.println("测试的时间为: " + (tencentrestart.getCount()));
		// // System.out.println("测试的时间为: " + (tencentinprogress.getCount()));
		// // System.out.println("测试的时间为: " + (tencentsuccess.getCount()));
		//
		// timer.stop();
		// }
	}

	public void test() {

		// System.out.println("时间长度: " + TimeUnit.MILLISECONDS.toNanos(1));
		// System.out.println("时间长度: " +
		// TimeUnit.MILLISECONDS.toString().toLowerCase(Locale.US));
		//
		// String s = TimeUnit.SECONDS.toString().toLowerCase(Locale.US);
		// System.out.println("时间长度: " + s);
		// System.out.println("时间长度: " + s.substring(0, s.length() - 1));
		//
		// System.out.println("测试: " + System.getSecurityManager());
		// // System.setSecurityManager(s);
		//
		// Set<Class<? extends PlatformManagedObject>> objects =
		// ManagementFactory.getPlatformManagementInterfaces();
		// for (Class<?> clazz : objects) {
		// System.out.println("测试: " + clazz.getCanonicalName());
		// }
		// System.out.println("测试: " +
		// ManagementFactory.CLASS_LOADING_MXBEAN_NAME);

	}

}
