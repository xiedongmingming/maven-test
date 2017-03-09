package org.xiem.com.curator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderLatch;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.EnsurePath;

@SuppressWarnings("deprecation")
public class MainTest {

	public static void main(String[] args) throws InterruptedException {

		List<Thread> list = new ArrayList<Thread>();
		
		Thread thread1 = new Thread(() -> {
			try {
				checkleadersip("192.168.186.128:2181", "/zktest", "node1");
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		thread1.start();
		list.add(thread1);

		Thread thread2 = new Thread(() -> {
			try {
				checkleadersip("192.168.186.128:2181", "/zktest", "node2");
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		thread2.start();
		list.add(thread2);

		Thread thread3 = new Thread(() -> {
			try {
				checkleadersip("192.168.186.128:2181", "/zktest", "node3");
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		thread3.start();
		list.add(thread3);

		Thread thread4 = new Thread(() -> {
			try {
				checkleadersip("192.168.186.128:2181", "/zktest", "node4");
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		thread4.start();
		list.add(thread4);

		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		scanner.useDelimiter("\n");
		while (scanner.hasNext()) {
			String input = scanner.nextLine();
			try {
				int code = Integer.parseInt(input);
				switch (code) {
				case 1:
					list.get(0).interrupt();
					break;
				case 2:
					list.get(1).interrupt();
					break;
				case 3:
					list.get(2).interrupt();
					break;
				case 4:
					list.get(3).interrupt();
					break;
				}
			} catch (Exception e) {
				continue;
			}

		}
	}

	private static void checkleadersip(String zkQuorum, String zkPath, String nodeName) throws Exception {

		System.out.println("connecting to " + zkQuorum + " : " + zkPath);

		CuratorFramework client = CuratorFrameworkFactory.newClient(zkQuorum, new ExponentialBackoffRetry(1000, 3));
		if (client == null) {
			System.out.println("fail to create zookeeper curator client");
			return;
		}

		client.start();

		try {
			new EnsurePath(zkPath).ensure(client.getZookeeperClient());// ����ȷ��ָ��·��������(�����ھʹ���)
		} catch (Exception e) {
			e.printStackTrace();
		}

		LeaderLatch leaderLatch = new LeaderLatch(client, zkPath, nodeName);

		try {
			leaderLatch.start();
		} catch (Exception e) {
			System.out.println("meet error when start curator leader " + e);
		}

		System.out.println("complete setup audit node {} on zooKeeper." + nodeName);

		while (true) {

			if (!hasLeadership(leaderLatch)) {
				System.out.println(Thread.currentThread().getName() + ": ����LEADER,��ǰ��LEADER��: " + leaderLatch.getLeader().getId());
			} else {
				System.out.println(Thread.currentThread().getName() + ": ��LEADER");
			}

			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				try {
					leaderLatch.close();// ��һ���ܹؼ�(���������ǰ�ڵ���LEADER���ýڵ�崻�ʱû��ѡ���µ�LEADER)
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				break;
			}
		}
	}

	private static boolean hasLeadership(LeaderLatch leaderLatch) {
		if (leaderLatch != null) {
			return leaderLatch.hasLeadership();
		}
		return false;
	}
}
