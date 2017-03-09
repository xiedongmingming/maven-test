package org.xiem.com.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCache.StartMode;
import org.apache.curator.retry.RetryNTimes;

public class ZooKeeperTest001 {

	// audit.zk.path=/shadc-t1/audit
	// audit.zk.quorum=zookeeper1.t1.shadc.yosemitecloud.com:2181\,zookeeper2.t1.shadc.yosemitecloud.com:2181\,zookeeper3.t1.shadc.yosemitecloud.com:2181

	private static final String ZK_ADDRESS = "192.168.37.134:2181";
	private static final String ZK_PATH = "/zktest";

	public static void main(String[] args) throws Exception {

		// 1.connect to zk
		CuratorFramework client = CuratorFrameworkFactory.newClient(ZK_ADDRESS, new RetryNTimes(10, 5000));
		client.start();
		System.out.println("zk client start successfully!");

		// 2.register watcher
		PathChildrenCache watcher = new PathChildrenCache(client, ZK_PATH, true);//
		watcher.getListenable().addListener((client1, event) -> {
			ChildData data = event.getData();
			if (data == null) {
				System.out.println("no data in event[" + event + "]");
			} else {
				System.out.println("receive event: " + "type=[" + event.getType() + "]" + ", path=[" + data.getPath()
						+ "]" + ", data=[" + new String(data.getData()) + "]" + ", stat=[" + data.getStat() + "]");
			}
		});

		watcher.start(StartMode.BUILD_INITIAL_CACHE);

		System.out.println("register zk watcher successfully!");

		Thread.sleep(Integer.MAX_VALUE);

		watcher.close();
	}

}
