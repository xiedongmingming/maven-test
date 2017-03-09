package org.xiem.com.guava;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.collect.ContiguousSet;
import com.google.common.collect.DiscreteDomain;
import com.google.common.collect.Range;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

public class MainTest {

	// GUAVA包含了GOOGLE的JAVA项目许多依赖的库如:集合、缓存、原生类型支持、并发库、通用注解、字符串处理、I/O等等


	public static void main(String[] args) {


		PreconditionsProject();

		DomainTest();
		Joiner joiner = Joiner.on(",").skipNulls();
		System.out.println(joiner.join("Harry", null, "Ron", "Hermione"));

		Joiner joiner2 = Joiner.on(",").useForNull("得到的");
		System.out.println(joiner2.join("Harry", null, "Ron", "Hermione"));
	}

	private static void PreconditionsProject() {

		String id = "";

		Preconditions.checkNotNull(id, "id cannot be null");
	}

	static ExpiringBloomFilter usedids = new ExpiringBloomFilter(10, TimeUnit.SECONDS);

	static synchronized boolean isNewUpdate(List<String> deviceId, String id2) {

		ExpiringBloomFilter f = usedids;

		f.expire();

		boolean isNewUpdate = true;

		for (String d : deviceId) {
			isNewUpdate &= f.put(d);
		}

		isNewUpdate &= f.put(id2);

		return isNewUpdate;
	}

	static private class ExpiringBloomFilter {

		private long startTime;

		private final long period;

		private BloomFilter<String> f;// Ŀ������

		public ExpiringBloomFilter(long period, TimeUnit unit) {// ���캯��
			startTime = now();// ��ʼʱ��ֵ
			this.period = unit.toNanos(period);// ������
			f = create();// ����
		}

		public boolean put(String object) {
			return f.put(object);
		}

		private long now() {// ��ǰ��������
			return System.nanoTime();
		}

		private BloomFilter<String> create() {// ����Ŀ������
			System.out.println("����һ��: " + System.nanoTime());
			return BloomFilter.create(Funnels.unencodedCharsFunnel(), 3000000, 0.001);
		}

		public void expire() {
			long now = now();
			if (now() - startTime >= period) {
				f = create();
				startTime = now;
			}
		}
	}

	private static void DomainTest() {

		DiscreteDomain<Integer> domain = DiscreteDomain.integers();

		System.out.println(domain.next(2147483646));
		System.out.println(domain.previous(-2147483647));

		System.out.println(domain.maxValue());
		System.out.println(domain.minValue());

		Collection<Integer> collections = ContiguousSet.create(Range.closed(20, 1000), domain);// ��ʾ��ȡ��Χ�ڵ����е�ֵ

		System.out.println(collections.size());
	}

}
