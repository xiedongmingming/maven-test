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
public class AuditLeaderTest {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws InterruptedException {

		List<Thread> list = new ArrayList<Thread>();// 线程列表
		
		Thread thread1 = new Thread(() -> {// 第一个线程
			try {
				checkleadersip("192.168.186.135:2180", "/zktest", "node1");
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		thread1.start();
		list.add(thread1);
		Thread thread2 = new Thread(() -> {// 第二个线程
			try {
				checkleadersip("192.168.186.135:2181", "/zktest", "node2");
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		thread2.start();
		list.add(thread2);
		Thread thread3 = new Thread(() -> {// 第三个线程
			try {
				checkleadersip("192.168.186.135:2180", "/zktest", "node3");
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		thread3.start();
		list.add(thread3);
		Thread thread4 = new Thread(() -> {// 第四个线程
			try {
				checkleadersip("192.168.186.135:2182", "/zktest", "node4");
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		thread4.start();
		list.add(thread4);

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

		// 第一个参数表示ZK服务器的监听地址
		// 第二个参数表示ZK路径(相对于根路径)
		// 第三个参数表示NODENAME(节点名称)

		System.out.println("connecting to " + zkQuorum + " : " + zkPath);

		CuratorFramework client = CuratorFrameworkFactory.newClient(zkQuorum, new ExponentialBackoffRetry(1000, 3));
		if (client == null) {
			System.out.println("fail to create zookeeper curator client");
			return;
		}

		client.start();// 启动客户端线程

		try {
			new EnsurePath(zkPath).ensure(client.getZookeeperClient());// 确保路径存在(不存在则新建)
		} catch (Exception e) {
			e.printStackTrace();
		}

		LeaderLatch leaderLatch = new LeaderLatch(client, zkPath, nodeName);

		try {
			leaderLatch.start();// 用于LEADER选举
		} catch (Exception e) {
			System.out.println("meet error when start curator leader " + e);
		}

		System.out.println("complete setup audit node {} on zooKeeper." + nodeName);

		while (true) {

			if (hasLeadership(leaderLatch)) {
				System.out.println(Thread.currentThread().getName() + ": 是LEADER: " + leaderLatch.getLeader().getId());
			} else {
				System.out.println(Thread.currentThread().getName() + ": 不是LEADER");
			}

			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				try {
					leaderLatch.close();
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
