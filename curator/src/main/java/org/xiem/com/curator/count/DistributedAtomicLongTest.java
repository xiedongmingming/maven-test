package org.xiem.com.curator.count;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.atomic.AtomicValue;
import org.apache.curator.framework.recipes.atomic.DistributedAtomicLong;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.retry.RetryNTimes;

import com.google.common.collect.Lists;

public class DistributedAtomicLongTest {

	private static final String ZK_ADDRESS = "192.168.186.135:2180";

	private static final int QTY = 5;

	private static final String PATH = "/example/counter";

	public static void main(String[] args) throws IOException, Exception {

		// 首先尝试使用乐观锁的方式设置计数器如果不成功(比如期间计数器已经被其它CLIENT更新了)
		// 它使用InterProcessMutex方式来更新计数值
		// 还记得InterProcessMutex是什么吗？
		// 它是我们前面跟着实例学习ZooKeeper的用法： 分布式锁 讲的分布式可重入锁。 这和上面的计数器的实现有显著的不同。
		// 此计数器有一系列的操作：
		//
		// get(): 获取当前值
		// increment()： 加一
		// decrement(): 减一
		// add()： 增加特定的值
		// subtract(): 减去特定的值
		// trySet(): 尝试设置计数值
		// forceSet(): 强制设置计数值
		try {

			CuratorFramework client = CuratorFrameworkFactory.newClient(ZK_ADDRESS, new ExponentialBackoffRetry(1000, 3));
			client.start();

			List<DistributedAtomicLong> examples = Lists.newArrayList();

			ExecutorService service = Executors.newFixedThreadPool(QTY);

			for (int i = 0; i < QTY; ++i) {

				final DistributedAtomicLong count = new DistributedAtomicLong(client, PATH, new RetryNTimes(10, 10));

				examples.add(count);

				Callable<Void> task = new Callable<Void>() {

					@Override
					public Void call() throws Exception {

						try {
							
							// Thread.sleep(rand.nextInt(1000));
							
							AtomicValue<Long> value = count.increment();
							
							// AtomicValue<Long> value = count.decrement();
							// AtomicValue<Long> value = count.add((long) rand.nextInt(20));
							
							System.out.println("succeed: " + value.succeeded());

							if (value.succeeded()) {
								System.out.println("increment: from " + value.preValue() + " to " + value.postValue());
							}

						} catch (Exception e) {
							e.printStackTrace();
						}

						return null;
					}
				};
				service.submit(task);
			}

			service.shutdown();
			service.awaitTermination(10, TimeUnit.MINUTES);

		} catch (Exception e) {

		}

	}

}
