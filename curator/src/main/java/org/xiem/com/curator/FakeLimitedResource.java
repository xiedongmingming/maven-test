package org.xiem.com.curator;

import java.util.concurrent.atomic.AtomicBoolean;

public class FakeLimitedResource {

	// ���������Ǵ���һ��ģ��Ĺ�����Դ(�����Դ����ֻ�ܵ��̵߳ķ���,������в�������)

	private final AtomicBoolean inUse = new AtomicBoolean(false);

	public void use() throws InterruptedException {

		// ��ʵ���������ǻ����������/ά��һ���������Դ
		// ���������ʹ����������²���Ƿ������쳣:IllegalStateException
		// �������������������SLEEP��һ��ʱ��������׳��쳣

		if (!inUse.compareAndSet(false, true)) {
			throw new IllegalStateException("needs to be used by one client at a time");
		}

		try {
			Thread.sleep((long) (3 * Math.random()));
		} finally {
			inUse.set(false);
		}
	}
}