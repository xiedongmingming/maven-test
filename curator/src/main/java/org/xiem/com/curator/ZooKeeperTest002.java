package org.xiem.com.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListener;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.retry.RetryNTimes;
import org.apache.curator.utils.EnsurePath;

/**
 * ����Ⱥ���ĳ������DOWN��ʱ,���ǿ���Ҫ��SLAVE�����ѡ��һ����Ϊ�µ�MASTER,��ʱ����Ҫһ�����ڷֲ�ʽ�������Զ�Э����LEADERѡ�ٷ���.
 * CURATOR�ṩ��LEADERSELECTOR������ʵ��LEADERѡ�ٹ���.
 * 
 * ͬһʱ��ֻ��һ��LISTENER�����TAKELEADERSHIP()����,˵�����ǵ�ǰ��LEADER.
 */

@SuppressWarnings("deprecation")
public class ZooKeeperTest002 {// LEADERѡ��

	private static final String ZK_ADDRESS = "192.168.37.134:2181";
	private static final String ZK_PATH = "/zktest";

	public static void main(String[] args) throws InterruptedException {

		// ע��:��LEADER��TAKELEADERSHIP()�˳�ʱ��˵����������LEADER���,��ʱCURATOR������ZOOKEEPER�ٴ�ʣ���LISTENER��ѡ��һ���µ�LISTENER
		LeaderSelectorListener listener = new LeaderSelectorListener() {// ������
			@Override
			public void takeLeadership(CuratorFramework client) throws Exception {// ����÷���ʱ��ȡ�˳��÷���ʱ����

				// takeleadership() method should only return when leadership is being relinquished.

				System.out.println(Thread.currentThread().getName() + " take leadership!");

				Thread.sleep(5000L);

				System.out.println(Thread.currentThread().getName() + " relinquish leadership!");
			}

			@Override
			public void stateChanged(CuratorFramework client, ConnectionState state) {
			}
		};

		// ���������߳�
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
		selector.autoRequeue();// ʹ����LEADERSHIP��LISTENER�л������»��LEADERSHIP,��������õĻ������˵�LISTENER�ǲ����ٱ��LEADER��
		selector.start();
	}
}
