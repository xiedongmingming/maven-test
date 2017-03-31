package org.xiem.com.curator.ephemeral;

import java.util.concurrent.TimeUnit;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.nodes.PersistentEphemeralNode;
import org.apache.curator.framework.recipes.nodes.PersistentEphemeralNode.Mode;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.test.KillSession;
import org.apache.curator.utils.CloseableUtils;

@SuppressWarnings("deprecation")
public class PersistentEphemeralNodeExample {// 临时节点--http://ifeve.com/zookeeper-ephemeral-node/

	private static final String ZK_ADDRESS = "192.168.186.135:2180";

	private static final String PATH1 = "/example/ephemeral";
	private static final String PATH2 = "/example/persistent";

	public static void main(String[] args) throws Exception {

		// TestingServer server = new TestingServer();

		CuratorFramework client = null;

		PersistentEphemeralNode node = null;

		try {

			client = CuratorFrameworkFactory.newClient(ZK_ADDRESS, new ExponentialBackoffRetry(1000, 3));

			client.getConnectionStateListenable().addListener(new ConnectionStateListener() {
				@Override
				public void stateChanged(CuratorFramework client, ConnectionState newState) {
					System.out.println("state: " + newState.name());
				}
			});

			client.start();

			node = new PersistentEphemeralNode(client, Mode.EPHEMERAL, PATH1, "ephemeral".getBytes());// 创建临时节点
			node.start();
			node.waitForInitialCreate(3, TimeUnit.SECONDS);

			String actualPath = node.getActualPath();
			System.out.println(actualPath + "----->" + new String(client.getData().forPath(actualPath)));

			try {

				client.blockUntilConnected();

				client.create().creatingParentContainersIfNeeded().forPath(PATH2, "persistent".getBytes());

				System.out.println(PATH2 + "----->" + new String(client.getData().forPath(PATH2)));

			} catch (Exception ex) {
				ex.printStackTrace();
			}

			KillSession.kill(client.getZookeeperClient().getZooKeeper(), ZK_ADDRESS);

			System.out.println(actualPath + "----->" + (client.checkExists().forPath(actualPath) == null));

			System.out.println(PATH2 + "----->" + new String(client.getData().forPath(PATH2)));

		} catch (Exception ex) {

			ex.printStackTrace();

		} finally {

			CloseableUtils.closeQuietly(node);
			CloseableUtils.closeQuietly(client);

		}
	}
}
