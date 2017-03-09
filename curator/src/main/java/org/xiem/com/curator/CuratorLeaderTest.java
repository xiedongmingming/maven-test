package org.xiem.com.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListener;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.retry.RetryNTimes;
import org.apache.curator.utils.EnsurePath;

@SuppressWarnings("deprecation")
public class CuratorLeaderTest {// ����LEADER��ѡ��
	
	private static final String ZK_ADDRESS = "192.168.186.128:2181";

	private static final String ZK_PATH = "/zktest";

	public static void main(String[] args) throws InterruptedException {

		LeaderSelectorListener listener = new LeaderSelectorListener() {// ��ʾLEADERѡ�ټ�����
			@Override
			public void takeLeadership(CuratorFramework client) throws Exception {

				// �����LEADERȨ��

				System.out.println(Thread.currentThread().getName() + " take leadership!");

				Thread.sleep(5000L);

				// �˳���ʾ����LEADERȨ��

				System.out.println(Thread.currentThread().getName() + " relinquish leadership!");
			}

			@Override
			public void stateChanged(CuratorFramework client, ConnectionState state) {
				// ����״̬�����˱仯
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

	private static void registerListener(LeaderSelectorListener listener) {

		CuratorFramework client = CuratorFrameworkFactory.newClient(ZK_ADDRESS, new RetryNTimes(10, 5000));// CuratorFrameworkImpl

		client.start();

		try {
			new EnsurePath(ZK_PATH).ensure(client.getZookeeperClient());// ����ȷ��ָ��·��������(�����ھʹ���)
		} catch (Exception e) {
			e.printStackTrace();
		}

		@SuppressWarnings("resource")
		LeaderSelector selector = new LeaderSelector(client, ZK_PATH, listener);// ��ʾLEADERѡ����

		selector.autoRequeue();

		selector.start();
	}
}