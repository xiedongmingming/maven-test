package org.xiem.com.curator.leader;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;

public class ExampleClient extends LeaderSelectorListenerAdapter implements Closeable {

	// 异常处理
	// LEADERSELECTORLISTENER类继承CONNECTIONSTATELISTENER
	// LEADERSELECTOR必须小心连接状态的改变
	// 如果实例成为LEADER它应该小心SUSPENDED或LOST
	// 当SUSPENDED状态出现时实例必须假定在重新连接成功之前它可能不再是LEADER了(如果LOST状态出现实例不再是LEADER并且TAKELEADERSHIP方法返回)
	//
	// 重要:
	// 推荐处理方式是当收到SUSPENDED或LOST时抛出CANCELLEADERSHIPEXCEPTION异常
	// 这会导致LEADERSELECTOR实例中断并取消执行TAKELEADERSHIP方法的异常
	// 这非常重要
	// 你必须考虑扩展(提供了推荐的处理逻辑): LeaderSelectorListenerAdapter
	//
	private final String name;

	private final LeaderSelector leaderSelector;

	private final AtomicInteger leaderCount = new AtomicInteger();

	public ExampleClient(CuratorFramework client, String path, String name) {

		this.name = name;

		leaderSelector = new LeaderSelector(client, path, this);

		leaderSelector.autoRequeue();// 表示放弃后下次会继续获得选举机会--保证在此实例释放领导权之后还可能获得领导权
	}

	public void start() throws IOException {
		leaderSelector.start();
	}

	@Override
	public void close() throws IOException {
		leaderSelector.close();// 当你不再使用LEADERSELECTOR实例时应该调用它的CLOSE方法
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