package org.xiem.com.curator.leader;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderLatch;
import org.apache.curator.framework.recipes.leader.LeaderLatchListener;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;

import com.google.common.collect.Lists;

public class LeaderLatchTest {

	private static final String ZK_ADDRESS = "192.168.186.135:2180";

	private static final int CLIENT_QTY = 10;

	private static final String PATH = "/examples/leader";

	// public LeaderLatch(CuratorFramework client, String latchPath)
	// public LeaderLatch(CuratorFramework client, String latchPath, String id)
	// public boolean hasLeadership()
	// public void await() throws InterruptedException, EOFException
	// public boolean await(long timeout, TimeUnit unit) throws InterruptedException
	
	public static void main(String[] args) throws Exception {

		List<CuratorFramework> clients = Lists.newArrayList();// 新建多个客户端

		List<LeaderLatch> leaderLatchs = Lists.newArrayList();

		// TestingServer server = new TestingServer();

		try {

			for (int i = 0; i < CLIENT_QTY; ++i) {

				CuratorFramework client = CuratorFrameworkFactory.newClient(ZK_ADDRESS, new ExponentialBackoffRetry(1000, 3));
				client.getConnectionStateListenable().addListener(new ConnectionStateListener() {
					@Override
					public void stateChanged(CuratorFramework arg0, ConnectionState arg1) {
						// 异常处理:
						// LEADERLATCH实例可以增加CONNECTIONSTATELISTENER来监听网络连接问题
						// 当SUSPENDED或LOST时LEADER不再认为自己还是LEADER
						// 当LOST连接重连后(RECONNECTED)LEADERLATCH会删除先前的ZNODE然后重新创建一个
						// LEADERLATCH用户必须考虑导致LEADERSHIP丢失的连接问题
						// 强烈推荐你使用: ConnectionStateListener
					}
				});
				clients.add(client);

				LeaderLatch leaderLatch = new LeaderLatch(client, PATH, "client #" + i);

				leaderLatch.addListener(new LeaderLatchListener() {
					@Override
					public void isLeader() {

					}
					@Override
					public void notLeader() {

					}
				});
				leaderLatchs.add(leaderLatch);

				client.start();

				leaderLatch.start();// 一旦启动LEADERLATCH会和其它使用相同LATCHPATH的其它LEADERLATCH交涉(然后随机的选择其中一个作为LEADER)
			}

			Thread.sleep(10000);

			LeaderLatch currentLeader = null;

			for (int i = 0; i < CLIENT_QTY; ++i) {

				LeaderLatch leaderLatch = leaderLatchs.get(i);

				if (leaderLatch.hasLeadership()) {// 可以随时查看一个给定的实例是否是LEADER
					currentLeader = leaderLatch;
				}
			}
			System.out.println("当前的LEADER为: " + currentLeader.getId());
			System.out.println("当前的LEADER为: " + currentLeader.getLeader().getId());

			System.out.println("释放LEADER: " + currentLeader.getLeader().getId());

			currentLeader.close();// 一旦不使用LEADERLATCH了必须调用CLOSE方法(如果它是LEADER会释放LEADERSHIP其它的参与者将会选举一个LEADER)

			leaderLatchs.get(0).await(2, TimeUnit.SECONDS);// LEADERLATCH请求成为LEADERSHIP(会阻塞)尝试获取LEADER地位(但是未必能上位)

			System.out.println("the new leader is: " + leaderLatchs.get(0).getLeader().getId());

			System.out.println("press enter return to quit\n");

			new BufferedReader(new InputStreamReader(System.in)).readLine();

		} catch (Exception e) {
			
			e.printStackTrace();
			
		} finally {

			System.out.println("shutting down...");

			for (LeaderLatch exampleClient : leaderLatchs) {
				CloseableUtils.closeQuietly(exampleClient);
			}

			for (CuratorFramework client : clients) {
				CloseableUtils.closeQuietly(client);
			}

		}
	}
}