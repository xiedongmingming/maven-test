package org.xiem.com.curator;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;

public class ExampleClient extends LeaderSelectorListenerAdapter implements Closeable {

	private final String name;

	// LEADERSELECTOR����С������״̬�ĸı�.���ʵ����ΪLEADER,��Ӧ����ӦSUSPENDED��LOST.��
	// SUSPENDED״̬����ʱʵ������ٶ����������ӳɹ�֮ǰ�����ܲ�����LEADER��.���LOST״̬����,ʵ��������LEADER,
	// TAKELEADERSHIP��������.
	// ��Ҫ:�Ƽ�����ʽ�ǵ��յ�SUSPENDED��LOSTʱ�׳�CANCELLEADERSHIPEXCEPTION�쳣.
	// ��ᵼ��LEADERSELECTORʵ���жϲ�ȡ��ִ��TAKELEADERSHIP�������쳣.��ǳ���Ҫ,
	// ����뿼����չLEADERSELECTORLISTENERADAPTER.LEADERSELECTORLISTENERADAPTER�ṩ���Ƽ��Ĵ����߼�.
	private final LeaderSelector leaderSelector;

	private final AtomicInteger leaderCount = new AtomicInteger();

	public ExampleClient(CuratorFramework client, String path, String name) {
		this.name = name;
		leaderSelector = new LeaderSelector(client, path, this);
		leaderSelector.autoRequeue();
	}

	public void start() throws IOException {

		// ������ʵ��ȡ���쵼Ȩʱ���LISTENER��TAKELEADERSHIP()����������.
		// ��TAKELEADERSHIP()����ֻ���쵼Ȩ���ͷ�ʱ�ŷ���.���㲻��ʹ��LEADERSELECTORʵ��ʱӦ�õ�������CLOSE����.

		leaderSelector.start();
	}

	@Override
	public void close() throws IOException {
		leaderSelector.close();
	}

	@Override
	public void takeLeadership(CuratorFramework client) throws Exception {

		final int waitSeconds = (int) (5 * Math.random()) + 1;

		System.out.println(name + " is now the leader. waiting " + waitSeconds + " seconds...");
		System.out.println(name + " has been leader " + leaderCount.getAndIncrement() + " time(s) before.");

		try {

			Thread.sleep(TimeUnit.SECONDS.toMillis(waitSeconds));

		} catch (InterruptedException e) {

			System.err.println(name + " was interrupted.");

			Thread.currentThread().interrupt();

		} finally {
			System.out.println(name + " relinquishing leadership.\n");
		}
	}
}