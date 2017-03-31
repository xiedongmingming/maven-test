package org.xiem.com.curator.count;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.shared.SharedCount;
import org.apache.curator.framework.recipes.shared.SharedCountListener;
import org.apache.curator.framework.recipes.shared.SharedCountReader;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.retry.ExponentialBackoffRetry;

import com.google.common.collect.Lists;

public class SharedCounterTest implements SharedCountListener {// http://ifeve.com/zookeeper-sharedcount/

	// 利用ZK可以实现一个集群共享的计数器(只要使用相同的PATH就可以得到最新的计数器值(这是由ZK的一致性保证的))
	// CURATOR有两个计数器:
	// 一个是用INT来计数
	// 一个是用LONG来计数

	// SharedCount -- INT
	// SharedCountReader
	// SharedCountListener

	private static final int QTY = 5;

	private static final String ZK_ADDRESS = "192.168.186.135:2180";
	private static final String PATH = "/example/counter";

	public static void main(String[] args) throws IOException, Exception {
		
		final Random rand = new Random();
		
		SharedCounterTest example = new SharedCounterTest();
		
		// TestingServer server = new TestingServer()
		
		try {	
			
			CuratorFramework client = CuratorFrameworkFactory.newClient(ZK_ADDRESS, new ExponentialBackoffRetry(1000, 3));
			client.start();

			SharedCount baseCount = new SharedCount(client, PATH, 0);
			baseCount.addListener(example);
			baseCount.start();

			List<SharedCount> sharedCounts = Lists.newArrayList();

			ExecutorService service = Executors.newFixedThreadPool(QTY);

			for (int i = 0; i < QTY; ++i) {

				final SharedCount sharedCount = new SharedCount(client, PATH, 0);

				sharedCounts.add(sharedCount);

				Callable<Void> task = new Callable<Void>() {

					@Override
					public Void call() throws Exception {

						sharedCount.start();// 注意计数器必须START(使用完之后必须调用CLOSE关闭它)

						Thread.sleep(rand.nextInt(10000));

						System.out.println("increment: " + sharedCount.trySetCount(sharedCount.getVersionedValue(), sharedCount.getCount() + rand.nextInt(10)));

						// 第一个参数提供当前的VERSIONEDVALUE(如果期间其它CLIENT更新了此计数值你的更新可能不成功)但是这时你的CLIENT更新了最新的值(所以失败了你可以尝试再更新一次)
						// 而SETCOUNT是强制更新计数器的值

						return null;
					}
				};

				service.submit(task);
			}

			service.shutdown();
			service.awaitTermination(10, TimeUnit.MINUTES);

			for (int i = 0; i < QTY; ++i) {
				sharedCounts.get(i).close();
			}

			baseCount.close();

		} catch (Exception e) {

		}

	}

	@Override
	public void stateChanged(CuratorFramework arg0, ConnectionState arg1) {

		// 在这里再重复一遍前面讲到的强烈推荐你监控ConnectionStateListener， 尽管我们的有些例子没有监控它。
		// 在本例中SharedCountListener扩展了ConnectionStateListener。 这一条针对所有的Curator
		// recipes都适用，后面的文章中就不专门提示了。

		System.out.println("state changed: " + arg1.toString());
	}
	@Override
	public void countHasChanged(SharedCountReader sharedCount, int newCount) throws Exception {
		System.out.println("counter's value is changed to " + newCount);
	}
}
