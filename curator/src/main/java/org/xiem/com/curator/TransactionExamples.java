package org.xiem.com.curator;

import java.util.Collection;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.transaction.CuratorTransaction;
import org.apache.curator.framework.api.transaction.CuratorTransactionResult;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;

public class TransactionExamples {

	// Curator��֧������һ��crud����ͬ��ͬ��
	private static CuratorFramework client = CuratorFrameworkFactory.newClient("",
			new ExponentialBackoffRetry(1000, 3));

	public static void main(String[] args) {
		try {
			client.start();
			// ��������
			CuratorTransaction transaction = client.inTransaction();

			Collection<CuratorTransactionResult> results = transaction.create()
					.forPath("/a/path", "some data".getBytes()).and().setData()
					.forPath("/another/path", "other data".getBytes()).and().delete().forPath("/yet/another/path").and()
					.commit();

			for (CuratorTransactionResult result : results) {
				System.out.println(result.getForPath() + " - " + result.getType());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// �ͷſͻ�������
			CloseableUtils.closeQuietly(client);
		}

	}
}
