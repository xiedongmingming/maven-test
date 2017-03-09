package org.xiem.com.curator;

import java.util.concurrent.TimeUnit;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;

public class ExampleClientThatLocks {

	// ������������,ʹ����Դ,�ͷ�������һ�������ķ��ʹ���.

	private final InterProcessMutex lock;// InterProcessSemaphoreMutex

	private final FakeLimitedResource resource;

	private final String clientName;

	public ExampleClientThatLocks(CuratorFramework client, String lockPath, FakeLimitedResource resource, String clientName) {
		this.resource = resource;
		this.clientName = clientName;
		lock = new InterProcessMutex(client, lockPath);
	}

	public void doWork(long time, TimeUnit unit) throws Exception {

		if (!lock.acquire(time, unit)) {// ��ʾ��ȡ������
			throw new IllegalStateException(clientName + " could not acquire the lock");
		}

		System.out.println(clientName + " has the lock");

		if (!lock.acquire(time, unit)) {
			throw new IllegalStateException(clientName + " could not acquire the lock");
		}

		System.out.println(clientName + " has the lock again");

		try {

			resource.use();
		} finally {
			System.out.println(clientName + " releasing the lock");
			lock.release();
			lock.release();
		}
	}
}