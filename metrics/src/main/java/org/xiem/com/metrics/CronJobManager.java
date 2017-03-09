package org.xiem.com.metrics;

import java.util.EnumMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.joda.time.DateTime;
import org.joda.time.DateTimeFieldType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CronJobManager {

    public static final long MINUTES_IN_AN_HOUR = 60;

	public enum CronJob {// 表示任务的类型
        RELOAD_CONFIG,
        RELOAD_DB_CACHE,
		CHECK_DB_AUDIT,
        BUDGET_CLIENT_UPDATE,
        BUDGET_DEBITOR_UPDATE,
        BUDGET_MANAGER_UPDATE
    }

    private static final Logger LOG = LoggerFactory.getLogger(CronJobManager.class);

	private static EnumMap<CronJob, CronJobExecutor> jobToExecutors = new EnumMap<CronJob, CronJobExecutor>(
			CronJob.class);

	private static class CronJobExecutor {// 每个任务的执行器
		private final Runnable runnable;// 对应的具体任务
		private final ScheduledExecutorService executor;// 执行器
        private CronJobExecutor(final CronJob job, final Runnable r, final long delay, final long interval, final TimeUnit timeUnit) {
            this.runnable = new Runnable() {
				public void run() {
                    long start = System.currentTimeMillis();
                    try {
                        r.run();
                    } catch (Throwable t) {
						LOG.error("caught throwable: ", t);
                    }
                    long time = System.currentTimeMillis() - start;
					if (time > timeUnit.toMillis(interval)) {// 时间单位
                        StringBuilder sb = new StringBuilder();
                        sb.append(job.toString());
                        sb.append(" took ").append(time);
                        sb.append(" ms to complete, more than an update interval: ");
                        sb.append(interval);
                        LOG.warn(sb.toString());
                    }
                }
            };
			this.executor = Executors.newSingleThreadScheduledExecutor();// 表示会开启一个单独的线程
            executor.scheduleAtFixedRate(runnable, delay, interval, timeUnit);
        }
    }

    public static synchronized void runPeriodicallyAtSetTimeInMillis(
            final CronJob job,
            final Runnable runnable,
            final long intervalMillis,
            final long offsetMillis,
            final DateTimeFieldType dateFieldType) {
        final long numMillisInDateType = dateFieldType.getDurationType().getField(null).getMillis(1);
        if (numMillisInDateType % intervalMillis != 0) {
            throw new IllegalArgumentException("interval in millis must be a factor of " + numMillisInDateType + ": " + intervalMillis);
        }

        DateTime current = DateTime.now();
        DateTime flooredDate = current.property(dateFieldType).roundFloorCopy();
        long currOffset = current.getMillis() - flooredDate.getMillis();
        long delay = intervalMillis - (currOffset - offsetMillis) % intervalMillis;
        runPeriodically(job, runnable, delay, intervalMillis, TimeUnit.MILLISECONDS);
    }

    public static synchronized void runPeriodicallyAtSetTimesInAnHour(
            final CronJob job,
            final Runnable runnable,
            final long intervalMinutes,
            final long offsetMinutes) {
        if (MINUTES_IN_AN_HOUR % intervalMinutes != 0) {
            throw new IllegalArgumentException(
                    "interval in minutes must be a factor of "
                            + MINUTES_IN_AN_HOUR);
        }
        runPeriodicallyAtSetTimeInMillis(job,
                runnable,
                TimeUnit.MINUTES.toMillis(intervalMinutes),
                TimeUnit.MINUTES.toMillis(offsetMinutes),
                DateTimeFieldType.hourOfDay());
    }

    /**
     * Run the runnable periodically with a frequency of 'interval' timeunits after an initial 'delay' timeUnits
     * 
     * @param key
     * @param runnable
     * @param delay
     * @param interval
     * @param timeunit
     */
    public static synchronized void runPeriodically(final CronJob job, final Runnable runnable, final long delay, final long interval, TimeUnit timeUnit) {
        LOG.info("Adding cron job " + job + " with interval " + interval + " and delay " + delay + " in " + timeUnit);
        if (jobToExecutors.containsKey(job)) {
            throw new IllegalStateException(job + " scheduled more than once");
        }
        jobToExecutors.put(job, new CronJobExecutor(job, runnable, delay, interval, timeUnit));
    }

    public static void requestExecution(final CronJob job) {
        LOG.info("Execution requested for runnable: " + job);
        if (!jobToExecutors.containsKey(job)) {
            throw new IllegalArgumentException("requested runnable not set: "
                    + job);
        }
        CronJobExecutor r = jobToExecutors.get(job);
        r.executor.execute(r.runnable);
    }

    public static synchronized void shutdown(CronJob job) throws InterruptedException {
        CronJobExecutor excutor = jobToExecutors.get(job);
        if (excutor != null) {
            excutor.executor.shutdown();
            boolean terminated = false;
            try {
                terminated = excutor.executor.awaitTermination(60, TimeUnit.SECONDS);
            } finally {
                if (!terminated) {
                    LOG.error("Termination failed: " + job);
                }
                jobToExecutors.remove(job);
            }
        }
    }
    public static synchronized void shutdownAllRunnables() throws InterruptedException {
        for (CronJob k : jobToExecutors.keySet()) {
            shutdown(k);
        }
    }
    static {
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			public void run() {
                try {
                    CronJobManager.shutdownAllRunnables();
                } catch (InterruptedException e) {
                    LOG.warn(e.getMessage(), e);
                }
            }
        }));
    }
}
