package org.xiem.com.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListener;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.retry.RetryNTimes;
import org.apache.curator.utils.EnsurePath;

@SuppressWarnings("deprecation")
public class CuratorLeaderTest {// 4.2 LEADER选举
	
	// **************************************************************************************************
	// 当集群里的某个服务DOWN机时我们可能要从SLAVE结点里选出一个作为新的MASTER.这时就需要一套能在分布式环境中自动协调的LEADER选举方
	// 法.CURATOR提供了LEADERSELECTOR监听器来实现LEADER选举功能.同一时刻只有一个LISTENER会进入TAKELEADERSHIP()方法.说明
	// 它是当前的LEADER.注意当LEADER从TAKELEADERSHIP()退出时就说明它放弃了LEADER身份.这时CURATOR会利用ZOOKEEPER再从剩余的
	// LISTENER中选出一个新的LISTENER.AUTOREQUEUE()方法使放弃LEADERSHIP的LISTENER有机会重新获得LEADERSHIP.如果不设置的
	// 话放弃了的LISTENER是不会再变成LEADER的.
	// **************************************************************************************************

	private static final String ZK_ADDRESS = "192.168.186.135:2180";

	private static final String ZK_PATH = "/xxxx";

	private static LeaderSelector[] selector = new LeaderSelector[3];

	public static void main(String[] args) throws InterruptedException {

		LeaderSelectorListener listener = new LeaderSelectorListener() {
			@Override
			public void takeLeadership(CuratorFramework client) throws Exception {

				// *********************************************************************
				// this method should only return when leadership is being relinquished.
				// *********************************************************************

				System.out.println(Thread.currentThread().getName() + " take leadership!");

				Thread.sleep(1000);

				System.out.println(Thread.currentThread().getName() + " relinquish leadership!");
			}

			@Override
			public void stateChanged(CuratorFramework client, ConnectionState state) {
				System.out.println("state change " + state.name());
			}
		};

		new Thread(() -> {
			registerListener(listener, 0);
		}).start();
		new Thread(() -> {
			registerListener(listener, 1);
		}).start();
		new Thread(() -> {
			registerListener(listener, 2);
		}).start();

		Thread.sleep(Integer.MAX_VALUE);
	}

	@SuppressWarnings({})
	private static void registerListener(LeaderSelectorListener listener, int i) {

		CuratorFramework client = CuratorFrameworkFactory.newClient(ZK_ADDRESS, new RetryNTimes(10, 5000));

		client.start();

		try {
			new EnsurePath(ZK_PATH).ensure(client.getZookeeperClient());
		} catch (Exception e) {
			e.printStackTrace();
		}

		selector[i] = new LeaderSelector(client, ZK_PATH, listener);

		selector[i].autoRequeue();

		selector[i].start();
	}
}