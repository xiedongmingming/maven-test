package org.xiem.com.curator.lock;

import java.util.concurrent.TimeUnit;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.framework.recipes.locks.InterProcessReadWriteLock;

public class ExampleClientReadWriteLocks {

	// �������д��SHARED REENTRANT READ WRITE LOCK
	// ����JDK��REENTRANTREADWRITELOCK.һ����д������һ����ص���.һ�����������,����һ������д����.
	// ��������д��û��ʹ��ʱ��ͬʱ�ɶ������ʹ��,��д��ʹ��ʱ�������(����).
	// �����ǿ������.һ��ӵ��д�����߳̿��������,���Ƕ���ȴ���ܽ���д��.��Ҳ��ζ��д�����Խ����ɶ���,��������д��->����->�ͷ�д��.
	// �Ӷ���������д���ǲ��ɵ�.

	private final InterProcessReadWriteLock lock;

	private final InterProcessMutex readLock;
	private final InterProcessMutex writeLock;

	private final FakeLimitedResource resource;

	private final String clientName;

	public ExampleClientReadWriteLocks(CuratorFramework client, String lockPath, FakeLimitedResource resource,
			String clientName) {
		this.resource = resource;
		this.clientName = clientName;
		lock = new InterProcessReadWriteLock(client, lockPath);
		readLock = lock.readLock();
		writeLock = lock.writeLock();
	}

	public void doWork(long time, TimeUnit unit) throws Exception {
		if (!writeLock.acquire(time, unit)) {
			throw new IllegalStateException(clientName + " could not acquire the writeLock");
		}
		System.out.println(clientName + " has the writeLock");

		if (!readLock.acquire(time, unit)) {
			throw new IllegalStateException(clientName + " could not acquire the readLock");
		}
		System.out.println(clientName + " has the readLock too");

		try {
			resource.use(); // access resource exclusively
		} finally {
			System.out.println(clientName + " releasing the lock");
			readLock.release(); // always release the lock in a finally block
			writeLock.release(); // always release the lock in a finally block
		}
	}
}
