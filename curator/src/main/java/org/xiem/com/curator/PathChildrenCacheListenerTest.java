package org.xiem.com.curator;

import java.io.IOException;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCache.StartMode;
import org.apache.curator.retry.RetryNTimes;

public class PathChildrenCacheListenerTest {// 3.2 监听器

	// CURATOR提供了三种WATCHER(CACHE)来监听结点的变化:
	// •PATH CACHE: 监视一个路径下孩子结点的创建、删除、以及结点数据的更新.产生的事件会传递给注册的监听器:PathChildrenCacheListener
	// •NODE CACHE: 监视一个结点的创建、更新、删除并将结点的数据缓存在本地
	// •TREE CACHE: PATH CACHE和NODE CACHE的合体监视路径下的创建、更新、删除事件并缓存路径下所有孩子结点的数据.
	//
	// 下面就测试一下最简单的PATH WATCHER:

	private static final String ZK_ADDRESS = "192.168.186.135:2181";
	private static final String ZK_PATH = "/zktest";

	public static void main(String[] args) throws Exception {

		Test001();
	}

	private static void Test001() throws Exception, InterruptedException, IOException {
		CuratorFramework client = CuratorFrameworkFactory.newClient(ZK_ADDRESS, new RetryNTimes(10, 5000));// 新建客户端(第二个参数指明了重试时间间隔)
		client.start();// 启动客户端
		System.out.println("zk client start successfully !");

		PathChildrenCache watcher = new PathChildrenCache(client, ZK_PATH, true);
		
		watcher.getListenable().addListener((client1, event) -> {// 主要用于子路径监听
			
			// 当子路径下的节点发生变化时会回调该函数

			ChildData data = event.getData();
			
			if (data == null) {
				System.out.println("no data in event: " + event);
			} else {
				System.out.println("receive event: type=[" + event.getType() + "] path=[" + data.getPath() + "] data=["
						+ new String(data.getData()) + "] stat=[" + data.getStat() + "]");
			}

		});

		watcher.start(StartMode.BUILD_INITIAL_CACHE);// 启动WATCHER

		System.out.println("register zk watcher successfully!");

		Thread.sleep(Integer.MAX_VALUE);

		watcher.close();
	}
}
