package org.xiem.com.curator;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.test.TestingServer;
import org.apache.curator.utils.CloseableUtils;

public class InterProcessMutexExample {
	
	// �㲻Ӧ���ڶ���߳�����ͬһ��InterProcessMutex,
	// �������ÿ���߳��ж�����һ��InterProcessMutexʵ��,���ǵ�path��һ��,�������ǿ��Թ���ͬһ����.

	private static final int QTY = 5;
	
	private static final int REPETITIONS = QTY * 10;
	
	private static final String PATH = "/examples/locks";

	public static void main(String[] args) throws Exception {
		
		final FakeLimitedResource resource = new FakeLimitedResource();// ��ʾ������Դ
		
		ExecutorService service = Executors.newFixedThreadPool(QTY);// �̳߳�
		
		final TestingServer server = new TestingServer();
		
		try {
			
			for (int i = 0; i < QTY; ++i) {
				
				final int index = i;
				
				Callable<Void> task = new Callable<Void>() {//
					@Override
					public Void call() throws Exception {
						
						CuratorFramework client = CuratorFrameworkFactory.newClient(server.getConnectString(), new ExponentialBackoffRetry(1000, 3));
						
						try {
							
							client.start();
							
							final ExampleClientThatLocks example = new ExampleClientThatLocks(client, PATH, resource, "Client " + index);
							
							for (int j = 0; j < REPETITIONS; ++j) {
								example.doWork(10, TimeUnit.SECONDS);
							}
							
						} catch (Throwable e) {
							e.printStackTrace();
						} finally {
							CloseableUtils.closeQuietly(client);
						}
						
						return null;
					}
				};

				service.submit(task);
			}

			service.shutdown();
			service.awaitTermination(10, TimeUnit.MINUTES);

		} finally {
			CloseableUtils.closeQuietly(server);
		}
	}
}
