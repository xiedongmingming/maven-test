package org.xiem.com.curator.leader;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;

import com.google.common.collect.Lists;

public class LeaderSelectorTest {// LEADER选举--http://ifeve.com/zookeeper-leader/

	// LeaderSelector
	// LeaderSelectorListener
	// LeaderSelectorListenerAdapter
	// CancelLeadershipException
	
	// public LeaderSelector(CuratorFramework client, String mutexPath,LeaderSelectorListener listener)
	// public LeaderSelector(CuratorFramework client, String mutexPath, ThreadFactory threadFactory, Executor executor, LeaderSelectorListener listener)

	// 通过LEADERSELECTORLISTENER可以对领导权进行控制在适当的时候释放领导权这样每个节点都有可能获得领导权
	// 而LEADERLATCH一根筋到死除非调用CLOSE方法(否则它不会释放领导权)

	private static final String ZK_ADDRESS = "192.168.186.135:2180";
	
	private static final int CLIENT_QTY = 10;
	private static final String PATH = "/examples/leader";

	public static void main(String[] args) throws Exception {
		
		List<CuratorFramework> clients = Lists.newArrayList();
		
		List<ExampleClient> examples = Lists.newArrayList();
		
		// TestingServer server = new TestingServer();
		
		try {
			
			for (int i = 0; i < CLIENT_QTY; ++i) {
				
				CuratorFramework client = CuratorFrameworkFactory.newClient(ZK_ADDRESS, new ExponentialBackoffRetry(1000, 3));
				
				clients.add(client);
				
				ExampleClient example = new ExampleClient(client, PATH, "client #" + i);
				
				examples.add(example);
				
				client.start();

				example.start();// 必须启动:一旦启动当实例取得领导权时你的LISTENER的TAKELEADERSHIP方法被调用而TAKELEADERSHIP方法只有领导权被释放时才返回
			}

			System.out.println("press enter return to quit\n");

			new BufferedReader(new InputStreamReader(System.in)).readLine();

		} finally {

			System.out.println("shutting down...");

			for (ExampleClient exampleClient : examples) {
				CloseableUtils.closeQuietly(exampleClient);
			}

			for (CuratorFramework client : clients) {
				CloseableUtils.closeQuietly(client);
			}
		}
	}
}