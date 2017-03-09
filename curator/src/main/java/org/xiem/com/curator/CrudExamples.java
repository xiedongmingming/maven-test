package org.xiem.com.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;
import org.apache.zookeeper.data.Stat;

public class CrudExamples {

	/*
	 * ���ýӿ��� create()�� delete(): ɾ checkExists(): �ж��Ƿ���� setData(): �� getData():
	 * ��
	 * ������Щ��������forpath()��β������watch(����)��withMode��ָ��ģʽ������inBackground����̨���У��ȷ�����ʹ�á�
	 */
	private static CuratorFramework client = CuratorFrameworkFactory.newClient("",
			new ExponentialBackoffRetry(1000, 3));
	private static final String PATH = "/crud";

	public static void main(String[] args) {
		try {
			client.start();

			client.create().forPath(PATH, "I love messi".getBytes());

			byte[] bs = client.getData().forPath(PATH);
			System.out.println("�½��Ľڵ㣬dataΪ:" + new String(bs));

			client.setData().forPath(PATH, "I love football".getBytes());

			// ��������backgroundģʽ�»�ȡ��data����ʱ��bs����Ϊnull
			byte[] bs2 = client.getData().watched().inBackground().forPath(PATH);
			System.out.println("�޸ĺ��dataΪ" + new String(bs2 != null ? bs2 : new byte[0]));

			client.delete().forPath(PATH);
			Stat stat = client.checkExists().forPath(PATH);

			// Stat���Ƕ�zonde�������Ե�һ��ӳ�䣬 stat=null��ʾ�ڵ㲻���ڣ�
			System.out.println(stat);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			CloseableUtils.closeQuietly(client);
		}
	}
}
