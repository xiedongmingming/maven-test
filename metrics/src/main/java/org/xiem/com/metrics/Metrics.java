package org.xiem.com.metrics;

import java.net.InetSocketAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map.Entry;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.codahale.metrics.Gauge;
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

/***
 * 
 * METRICS-JAVA版的指标度量工具之一
 * 
 * METRICS是一个给JAVA服务的各项指标提供度量工具的包,在JAVA代码中嵌入METRICS代码,可以方便的对业务代码的各个指标进行监控,同时,
 * METRICS能够很好的跟GANLIA、GRAPHITE结合,方便的提供图形化接口.基本使用方式直接将CORE包(目前稳定版本3.0.1)
 * 导入POM文件即可,配置如下:
 * 
 * <dependency><groupId>com.codahale.metrics</groupId>
 * <artifactId>metrics-core</artifactId><version>3.0.1</version></dependency>
 * 
 * CORE包主要提供如下核心功能:
 * 
 * •METRICS REGISTRIES类似一个METRICS容器,维护一个MAP,可以是一个服务一个实例.
 * •支持五种METRICS类型:GAUGES、COUNTERS、METERS、HISTOGRAMS和TIMERS.
 * •可以将METRICS值通过JMX、CONSOLE,CSV文件和SLF4J LOGGERS发布出来.
 * 
 * 五种METRICS类型:
 * 
 * 1.GAUGES
 * 
 * GAUGES是一个最简单的计量,一般用来统计瞬时状态的数据信息,比如系统中处于PENDING状态的JOB.测试代码:
 * 
 * 通过以上步骤将会向METRICSREGISTRY容器中注册一个名字为COM.NETEASE.TEST.METRICS.TESTGAUGES.PENDING
 * -JOB.SIZE的METRICS,实时获取队列长度的指标.另外,CORE包中还扩展了几种特定的GAUGE:
 * 
 * JMX GAUGES--提供给第三方库只通过JMX将指标暴露出来.
 * 
 * RATIO GAUGES—简单地通过创建一个GAUGE计算两个数的比值.
 * 
 * CACHED GAUGES—对某些计量指标提供缓存
 * 
 * DERIVATIVE GAUGES—提供GAUGE的值是基于其他GAUGE值的接口.
 * 
 * 2. COUNTER
 * COUNTER是GAUGE的一个特例,维护一个计数器,可以通过INC()和DEC()方法对计数器做修改.使用步骤与GAUGE基本类似,
 * 在METRICSREGISTRY中提供了静态方法可以直接实例化一个COUNTER.
 * 
 * 3. METERS
 * 
 * METERS用来度量某个时间段的平均处理次数(REQUEST PER
 * SECOND),每1、5、15分钟的TPS.比如一个SERVICE的请求数,通过METRICS.METER()实例化一个METER之后,然后通过METER
 * .MARK()方法就能将本次请求记录下来.统计结果有总的请求数,平均每秒的请求数,以及最近的1、5、15分钟的平均TPS.系统吞吐量(TPS)
 * 
 * 4. HISTOGRAMS
 * 
 * HISTOGRAMS主要使用来统计数据的分布情况,最大值、最小值、平均值、中位数，百分比(75%、90%、95%、98%、99%和99.9%).例如,
 * 需要统计某个页面的请求响应时间分布情况,可以使用该种类型的METRICS进行统计.具体的样例代码如下:
 * 
 * 5. TIMERS
 * 
 * TIMERS主要是用来统计某一块代码段的执行时间以及其分布情况,具体是基于HISTOGRAMS和METERS来实现的.样例代码如下:
 * 
 * Health Checks
 * 
 * Metrics提供了一个独立的模块：Health
 * Checks，用于对Application、其子模块或者关联模块的运行是否正常做检测。该模块是独立metrics-core模块的，
 * 使用时则导入metrics-healthchecks包。
 * 
 * <dependency> <groupId>com.codahale.metrics</groupId>
 * <artifactId>metrics-healthchecks</artifactId>
 * <version>3.0.1</version> </dependency>
 * 
 * 使用起来和与上述几种类型的Metrics有点类似，但是需要重新实例化一个Metrics容器HealthCheckRegistry，
 * 待检测模块继承抽象类HealthCheck并实现check()方法即可，然后将该模块注册到HealthCheckRegistry中，
 * 判断的时候通过isHealthy()接口即可。如下示例代码：
 * 
 **/

public class Metrics {

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
        final GraphiteReporter reporter = GraphiteReporter.forRegistry(Metrics.getInstance())
                                                          .prefixedWith(prefix)
                                                          .convertRatesTo(TimeUnit.SECONDS)
                                                          .convertDurationsTo(TimeUnit.MILLISECONDS)
                                                          .filter(MetricFilter.ALL)
                                                          .build(graphite);
        reporter.start(5, TimeUnit.SECONDS);
    }

    public static void addLogReporter(String prefix) {
        final Slf4jReporter reporter = Slf4jReporter.forRegistry(Metrics.getInstance())
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .filter(MetricFilter.ALL)
                .build();
        reporter.start(5, TimeUnit.SECONDS);
    }
	
	public static void main(String[] args) {
		
		System.out.println("\u5b8c\u5168\u6b63\u786e");
		System.out.println("\u5b8c\u5168\u6b63\u786e");

		Metrics.addGraphiteReporter(METRICS_GRAPHITE_HOST, METRICS_GRAPHITE_PORT, METRICS_GRAPHITE_PREFIX);

		MetricRegistry metrics = Metrics.getInstance();

		Timer stats = metrics.timer("mingxietesting");

		stats.time();

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式

		final Random randomnum = new Random();

		Gauge<Integer> gauge = new Gauge<Integer>() {// 实例化一个GAUGE
			public Integer getValue() {
				int testnum = randomnum.nextInt(10000);
				System.out.println("GAUGE生成的数值为: " + testnum);
				return testnum;
			}
		};
		metrics.register("xieming gauga test", gauge);// 注册到容器中

		int count = 0;
		int kind = 0;
		int num = 0;
		for (;;) {
			try {
				Thread.sleep(10 * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (count++ % 30 == 0) {
				kind = (kind + 1) % 5;
			}

			num = kind * 2000;
			System.out.println("time: " + df.format(new Date()) + ": " + num);
			stats.update(num, TimeUnit.MILLISECONDS);

		}
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
