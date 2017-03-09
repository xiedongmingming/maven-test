package org.xiem.com.metrics;

import java.util.Map;
import java.util.Random;

import com.codahale.metrics.health.HealthCheck;
import com.codahale.metrics.health.HealthCheckRegistry;

public class TestHealthChec extends HealthCheck {

	private final Database database;

	public TestHealthChec(Database database) {
		this.database = database;
	}

	@Override
	protected Result check() throws Exception {//
		if (database.ping()) {
			return Result.healthy();// 表示健康
		}
		return Result.unhealthy("can't ping database.");// 表示不健康
	}

	static class Database {// 模拟DATABASE对象
		public boolean ping() {// 模拟DATABASE的PING方法
			Random random = new Random();
			return random.nextBoolean();
		}
	}

	public static void main(String[] args) {

		// MetricRegistry metrics = new MetricRegistry();
		// ConsoleReporter reporter =
		// ConsoleReporter.forRegistry(metrics).build();

		HealthCheckRegistry registry = new HealthCheckRegistry();

		registry.register("database1", new TestHealthChec(new Database()));
		registry.register("database2", new TestHealthChec(new Database()));

		while (true) {
			for (Map.Entry<String, Result> entry : registry.runHealthChecks().entrySet()) {
				if (entry.getValue().isHealthy()) {// 会调用CHECK方法
					System.out.println(entry.getKey() + ": ok");
				} else {
					System.err.println(entry.getKey() + ": fail, error message: " + entry.getValue().getMessage());
					final Throwable e = entry.getValue().getError();// 获取不健康时的错误信息
					if (e != null) {
						e.printStackTrace();
					}
				}
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {

			}
		}
	}
}