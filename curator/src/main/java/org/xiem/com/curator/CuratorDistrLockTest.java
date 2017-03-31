package org.xiem.com.curator;

import java.util.concurrent.TimeUnit;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.RetryNTimes;

/**
 * curator framework's distributed lock test.
 */

public class CuratorDistrLockTest {

	// 4、CURATOR菜谱
	// 既然MAVEN包叫做CURATOR-RECIPES,那说明CURATOR有它独特的菜谱:
	// •锁: 包括共享锁、共享可重入锁、读写锁等.
	// •选举: LEADER选举算法.
	// •BARRIER: 阻止分布式计算直至某个条件被满足的栅栏,可以看做JDK CONCURRENT包中BARRIER的分布式实现.
	// •缓存: 前面提到过的三种CACHE及监听机制.
	// •持久化结点: 连接或SESSION终止后仍然在ZOOKEEPER中存在的结点.
	// •队列: 分布式队列、分布式优先级队列等.

	// 4.1、分布式锁
	// 分布式编程时,比如最容易碰到的情况就是应用程序在线上多机部署,于是当多个应用同时访问某一资源时,就需要某种机制去协调它们.例如,现在一台应用正在REBUILD缓存内容,要临时锁住某个区域暂时不让访问;又比如调度程序每次只想一个任务被一台应用执行等等.下面的程序会启动两个线程T1和T2去争夺锁,拿到锁的线程会占用5秒.运行多次可以观察到,有时是T1先拿到锁而T2等待,有时又会反过来.CURATOR会用我们提供的LOCK路径的结点作为全局锁,这个结点的数据类似这种格式:
	// [_C_64E0811F-9475-44CA-AA36-CLDB65AE5350-LOCK-0000000005]
	// 每次获得锁时会生成这种串,释放锁时清空数据.

	private static final String ZK_ADDRESS = "192.168.1.100:2181";
	private static final String ZK_LOCK_PATH = "/zktest";

	public static void main(String[] args) throws InterruptedException {
		// 1. connect to zk
		CuratorFramework client = CuratorFrameworkFactory.newClient(ZK_ADDRESS, new RetryNTimes(10, 5000));
		client.start();
		System.out.println("zk client start successfully!");

		Thread t1 = new Thread(() -> {
			doWithLock(client);
		}, "t1");
		Thread t2 = new Thread(() -> {
			doWithLock(client);
		}, "t2");

		t1.start();
		t2.start();
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
}
