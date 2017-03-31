package org.xiem.com.curator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListener;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.retry.RetryNTimes;
import org.apache.curator.test.TestingServer;
import org.apache.curator.utils.CloseableUtils;
import org.apache.curator.utils.EnsurePath;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

import com.google.common.collect.Lists;

@SuppressWarnings("deprecation")
public class MainTest {// 利用CURATOR提供的客户端API可以完全实现原生客户端的功能(值得注意的是CURATOR采用流式风格API)

	private static final String ZK_ADDRESS = "192.168.186.135:2180";
	private static final String ZK_LOCK_PATH = "/zktest";
	private static final String ZK_PATH = "/zktest2";

	public static void main(String[] args) throws Exception {
		Test003();
	}

	@SuppressWarnings("unused")
	private static void Test000() throws Exception {

		// 1. 连接到ZK
		CuratorFramework client = CuratorFrameworkFactory.newClient(ZK_ADDRESS, new RetryNTimes(10, 5000));
		client.start();
		System.out.println("zk client start successfully !");

		// 2. 客户端API测试

		// 2.1 创建节点
		String data1 = "hello";
		client.create().creatingParentsIfNeeded().forPath(ZK_PATH, data1.getBytes());

		// 2.2 获取节点和数据
		print(client.getChildren().forPath("/"));
		print(client.getData().forPath(ZK_PATH));

		// 2.3 修改数据
		String data2 = "world";
		client.setData().forPath(ZK_PATH, data2.getBytes());
		print(client.getData().forPath(ZK_PATH));

		// 2.4 删除节点
		client.delete().forPath(ZK_PATH);
		print(client.getChildren().forPath("/"));
	}

	private static void print(Object result) {

		System.out.println(result instanceof byte[] ? new String((byte[]) result) : result);

	}

	public static void Test001() throws Exception {// 通过JAVA代码使用ZOOKEEPER

		// ZOOKEEPER的使用主要是通过创建其JAR包下的ZOOKEEPER实例并且调用其接口方法进行的
		// 主要的操作就是对ZNODE的增删改操作以及监听ZNODE的变化

		// 以下为主要的API使用和解释:

		ZooKeeper zk = new ZooKeeper("127.0.0.1:2181", 500000, new Watcher() {// 创建一个ZOOKEEPER的实例

			// 第一个参数为目标服务器地址和端口
			// 第二个参数为SESSION超时时间
			// 第三个参数为节点变化时的回调方法

			@Override
			public void process(WatchedEvent event) {// 监控所有被触发的事件

			}
		});

		// 创建一个节点ROOT(数据是MYDATA)不进行ACL权限控制节点为永久性的(即客户端SHUTDOWN了也不会消失)
		zk.create("/root", "mydata".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		// 在ROOT下面创建一个CHILDONE(数据为CHILDONE)不进行ACL权限控制节点为永久性的
		zk.create("/root/childone", "childone".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		zk.getChildren("/root", true);
		zk.getData("/root/childone", true, null);
		zk.setData("/root/childone", "childonemodify".getBytes(), -1);
		zk.delete("/root/childone", -1);

		zk.close();// 关闭SESSION

		// 创建一个子目录节点
		zk.create("/testRootPath/testChildPath1", "1".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
		zk.create("/testRootPath/testChildPath2", "2".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
		zk.create("/testRootPath/testChildPath3", "3".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
		zk.create("/testRootPath/testChildPath4", "4".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);

		System.out.println(zk.getChildren("/testRootPath", false));

		// 创建一个子目录节点
		zk.create("/testRootPath", "testRootData".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

		zk.create("/testRootPath/testChildPath1", "1".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
		zk.create("/testRootPath/testChildPath2", "2".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
		zk.create("/testRootPath/testChildPath3", "3".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
		zk.create("/testRootPath/testChildPath4", "4".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);

		System.out.println(zk.getChildren("/testRootPath", false));

	}

	public static void Test002() throws Exception {// 运行正常

		ZooKeeper zk = new ZooKeeper("192.168.186.135:2180", 500000, new Watcher() {// 创建一个ZK实例

			// 第一个参数为目标服务器地址和端口
			// 第二个参数为SESSION超时时间
			// 第三个为节点变化时的回调方法

			@Override
			public void process(WatchedEvent event) {// 监控所有被触发的事件
				System.out.println("监测到事件: " + event.getState());
			}

		});

		zk.create("/root", "data".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);// 创建一个节点ROOT(不进行ACL权限控制)节点为永久性的(即客户端SHUTDOWN了也不会消失)

		zk.create("/root/childone", "childone".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);// 在ROOT下面创建一个子节点(不进行ACL权限控制)节点为永久性的

		List<String> list = zk.getChildren("/root", true);// 取得/ROOT节点下的子节点名称

		for (String str : list) {
			System.out.println(str);
		}

		byte[] bytes = zk.getData("/root/childone", true, null);// 取得节点下的数据

		System.out.println(new String(bytes));

		zk.setData("/root/childone", "childonemodify".getBytes(), -1);// 修改节点下的数据(第三个参数为版本如果是-1那会无视被修改的数据版本直接改掉)

		zk.delete("/root/childone", -1);// 删除节点(第二个参数为版本－1的话直接删除无视版本)

		zk.close();// 关闭SESSION
	}

	public static void Test003() throws IOException, KeeperException, InterruptedException {

		ZooKeeper zk = new ZooKeeper("192.168.186.135:2180", 500000, new Watcher() {// 创建一个ZK实例

			// 第一个参数为目标服务器地址和端口
			// 第二个参数为SESSION超时时间
			// 第三个为节点变化时的回调方法

			@Override
			public void process(WatchedEvent event) {// 监控所有被触发的事件
				System.out.println("监测到事件: " + event.getState());
			}

		});

		//		zk.create("/testRootPath", "test".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);// 必须先创建父目录
		//
		//		zk.create("/testRootPath/testChildPath1", "1".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);// 顺序
		//		zk.create("/testRootPath/testChildPath2", "2".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
		//		zk.create("/testRootPath/testChildPath3", "3".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
		//		zk.create("/testRootPath/testChildPath4", "4".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
		//
		//		System.out.println(zk.getChildren("/testRootPath", false));// 取得/ROOT节点下的子节点名称

		// zk.create("/testRootPath", "test".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);// 节点不能重复创建

		zk.create("/testRootPath/testChildPath1", "1".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
		zk.create("/testRootPath/testChildPath2", "2".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
		zk.create("/testRootPath/testChildPath3", "3".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
		zk.create("/testRootPath/testChildPath4", "4".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);

		System.out.println(zk.getChildren("/testRootPath", false));

		// 节点的消失需要一段时间(除非及时调用下面的函数)
		zk.close();// 关闭SESSION
	}

	public static void Test004() {

		// String serverList =
		// "192.168.186.135:2180;192.168.186.135:2181;192.168.186.135:2182";
		//
		// ZooKeeper zk = new ZooKeeper(serverList, 5000, watcher);
		// zk.create("/test", new byte[0], Ids.OPEN_ACL_UNSAFE,
		// CreateMode.PERSISTENT);
		//
	}

	public static void Test005() {

		CuratorFramework client = CuratorFrameworkFactory.newClient(ZK_ADDRESS, new RetryNTimes(10, 5000));

		client.start();

		System.out.println("zk client start successfully !");

		Thread t1 = new Thread(() -> {
			doWithLock(client);
		}, "t1");
		Thread t2 = new Thread(() -> {
			doWithLock(client);
		}, "t2");

		t1.start();
		t2.start();

	}

	public static void Test006() throws Exception {

		LeaderSelectorListener listener = new LeaderSelectorListener() {
			@Override
			public void takeLeadership(CuratorFramework client) throws Exception {

				// takeleadership() method should only return when leadership is being relinquished.

				System.out.println(Thread.currentThread().getName() + " take leadership!");

				Thread.sleep(5000L);

				System.out.println(Thread.currentThread().getName() + " relinquish leadership!");
			}

			@Override
			public void stateChanged(CuratorFramework client, ConnectionState state) {
			}
		};

		new Thread(() -> {
			registerListener(listener);
		}).start();

		new Thread(() -> {
			registerListener(listener);
		}).start();

		new Thread(() -> {
			registerListener(listener);
		}).start();

		Thread.sleep(Integer.MAX_VALUE);

	}

	@SuppressWarnings("resource")
	private static void registerListener(LeaderSelectorListener listener) {

		// 1.connect to zk
		CuratorFramework client = CuratorFrameworkFactory.newClient(ZK_ADDRESS, new RetryNTimes(10, 5000));
		client.start();

		// 2.ensure path
		try {
			new EnsurePath(ZK_PATH).ensure(client.getZookeeperClient());
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 3.register listener
		LeaderSelector selector = new LeaderSelector(client, ZK_PATH, listener);
		selector.autoRequeue();
		selector.start();
	}

	private static void doWithLock(CuratorFramework client) {

		InterProcessMutex lock = new InterProcessMutex(client, ZK_LOCK_PATH);

		try {

			if (lock.acquire(10 * 1000, TimeUnit.SECONDS)) {

				System.out.println(Thread.currentThread().getName() + " hold lock");

				Thread.sleep(5000L);

				System.out.println(Thread.currentThread().getName() + " release lock");

			}

		} catch (Exception e) {

			e.printStackTrace();

		} finally {

			try {
				lock.release();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	private static final String PATH1 = "/example/basic";

	public static void Test007(String[] args) throws Exception {

		TestingServer server = new TestingServer();

		CuratorFramework client = null;

		try {

			client = createSimple(server.getConnectString());
			client.start();
			client.create().creatingParentsIfNeeded().forPath(PATH1, "test".getBytes());
			CloseableUtils.closeQuietly(client);

			client = createWithOptions(server.getConnectString(), new ExponentialBackoffRetry(1000, 3), 1000, 1000);
			client.start();

			System.out.println(new String(client.getData().forPath(PATH1)));

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			CloseableUtils.closeQuietly(client);
			CloseableUtils.closeQuietly(server);
		}

	}

	public static CuratorFramework createSimple(String connectionString) {
		// these are reasonable arguments for the ExponentialBackoffRetry.
		// The first retry will wait 1 second - the second will wait up to 2
		// seconds - the
		// third will wait up to 4 seconds.
		ExponentialBackoffRetry retryPolicy = new ExponentialBackoffRetry(1000, 3);
		// The simplest way to get a CuratorFramework instance. This will use
		// default values.
		// The only required arguments are the connection string and the retry
		// policy
		return CuratorFrameworkFactory.newClient(connectionString, retryPolicy);
	}

	public static CuratorFramework createWithOptions(String connectionString, RetryPolicy retryPolicy,
			int connectionTimeoutMs, int sessionTimeoutMs) {
		// using the CuratorFrameworkFactory.builder() gives fine grained
		// control
		// over creation options. See the CuratorFrameworkFactory.Builder
		// javadoc details
		return CuratorFrameworkFactory.builder().connectString(connectionString).retryPolicy(retryPolicy)
				.connectionTimeoutMs(connectionTimeoutMs).sessionTimeoutMs(sessionTimeoutMs)
				// etc. etc.
				.build();
	}

	private static final int CLIENT_QTY = 10;
	@SuppressWarnings("unused")
	private static final String PATH = "/examples/leader";

	public static void LeaderSelectorExample(String[] args) throws Exception {

		List<CuratorFramework> clients = Lists.newArrayList();
		List<ExampleClient> examples = Lists.newArrayList();

		TestingServer server = new TestingServer();

		try {

			for (int i = 0; i < CLIENT_QTY; ++i) {

				CuratorFramework client = CuratorFrameworkFactory.newClient(server.getConnectString(),
						new ExponentialBackoffRetry(1000, 3));
				clients.add(client);

				ExampleClient example = new ExampleClient(client, PATH1, "Client #" + i);
				examples.add(example);

				client.start();
				example.start();
			}

			System.out.println("press enter/return to quit\n");

			new BufferedReader(new InputStreamReader(System.in)).readLine();

		} finally {

			System.out.println("shutting down...");

			for (ExampleClient exampleClient : examples) {
				CloseableUtils.closeQuietly(exampleClient);
			}

			for (CuratorFramework client : clients) {
				CloseableUtils.closeQuietly(client);
			}

			CloseableUtils.closeQuietly(server);
		}
	}
}
