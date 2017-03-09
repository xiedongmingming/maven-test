package org.xiem.com.curator;

import java.io.IOException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.ZooKeeper;  
  
public class WatchClient implements Runnable {// ΪZNODE����WATCHER

	// ZNODE�ı仯����ֱ��ͨ��EVENT.GETTYPE����ȡ
	// ʹ��ZK.EXISTS(PATH,WC)��ΪPATH�ڵ�����WATCHER(���нڵ㶼����ʹ��WC��ΪWATCHER)

	@SuppressWarnings("unused")
	private static final Log LOG = LogFactory.getLog(WatchClient.class);

	public static final int CLIENT_PORT = 2181;

	public static final String PATH = "/zk";// ��Ҫ��صĽ��

	private static ZooKeeper zk;// �ͻ���

	private static List<String> nodeList;// ��Ҫ��صĽ����ӽ���б�
  
	public static void main(String[] args) throws Exception {

		PropertyConfigurator.configure("E:\\Eclipse\\java-neon\\eclipse\\workspace\\CuratorProject\\src\\log4j.properties");

		WatchClient client = new WatchClient();

		Thread th = new Thread(client);

		th.start();
    }  
  
	// ********************************************************************************************
	public WatchClient() throws IOException {// ���캯��
  
		zk = new ZooKeeper("192.168.186.128:" + CLIENT_PORT, 21810,
				new Watcher() {// ע��ļ�����(�����������Ҫ���ڼ��������ص��¼�)
					@Override
					public void process(WatchedEvent event) {
						System.out.println("��������ʱ���¼�����: " + event.getType());
					}
				});
	}
	// ********************************************************************************************

    @Override  
	public void run() {// ����WATCH���߳�

		Watcher wc = new Watcher() {// �����������Ҫ���ڼ���ĳ���ڵ�������Ϣ

            @Override  
            public void process(WatchedEvent event) {

				List<String> nodeListBefore = nodeList;// ������ݸı�֮ǰ�Ľ���б�

				if (event.getType() == EventType.NodeDataChanged) {// ���������ݷ����ı�ʱ
					System.out.println("node data changed:" + event.getPath());
                }
				if (event.getType() == EventType.NodeDeleted) {
					System.out.println("node deleted:" + event.getPath());
                }
				if (event.getType() == EventType.NodeCreated) {
					System.out.println("node created:" + event.getPath());
                }
  
				try {// ��ȡ���º��NODELIST
                    nodeList = zk.getChildren(event.getPath(), false);
                } catch (KeeperException e) {
					System.out.println(event.getPath() + " has no child, deleted.");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

				List<String> nodeListNow = nodeList;

				if (nodeListBefore.size() < nodeListNow.size()) {// �����½��
                    for (String str : nodeListNow) {
                        if (!nodeListBefore.contains(str)) {
							System.out.println("node created:" + event.getPath() + "/" + str);
						}
					}
				}
			}
		};

		while (true) {// �������PATH�µĽ��

			try {
				zk.exists(PATH, wc);// ��Ҫ��ص������
			} catch (KeeperException | InterruptedException e) {
				e.printStackTrace();
			}

			try {
				nodeList = zk.getChildren(PATH, wc);
			} catch (KeeperException | InterruptedException e) {
				e.printStackTrace();
			}
  
			for (String nodeName : nodeList) {// ��PATH�µ�ÿ����㶼����һ��WATCHER
				try {
					zk.exists(PATH + "/" + nodeName, wc);
				} catch (KeeperException | InterruptedException e) {
					e.printStackTrace();
				}
			}
              
			try {
				Thread.sleep(3000);// SLEEPһ�����CUPռ����
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}