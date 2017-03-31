package org.xiem.com.curator.lock;

public class Lock {// 分布式锁

	// 锁
	// 分布式的锁全局同步(这意味着任何一个时间点不会有两个客户端都拥有相同的锁)

	// 可重入锁(SHARED REENTRANT LOCK):
	// 首先我们先看一个全局可重入的锁
	// SHARED意味着锁是全局可见的(客户端都可以请求锁)
	// REENTRANT和JDK的REENTRANTLOCK类似意味着同一个客户端在拥有锁的同时可以多次获取不会被阻塞
	// 实现类(实例可以重用): InterProcessMutex
	// 它的构造函数为:
	// public InterProcessMutex(CuratorFramework client, String path)

	// 通过ACQUIRE获得锁并提供超时机制:
	// public void acquire()
	// public boolean acquire(long time, TimeUnit unit)
	// 通过RELEASE方法释放锁
	//

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}



	public class ExampleClientThatLocks {


	}
}
