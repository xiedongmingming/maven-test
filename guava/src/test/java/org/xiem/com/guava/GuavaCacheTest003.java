package org.xiem.com.guava;


import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import org.junit.Test;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheStats;

/**
 * 统计 信息
 * 
 * @author lenovo
 *
 */
public class GuavaCacheTest003 {
	Cache<String, String> cache = CacheBuilder.newBuilder().maximumSize(2).recordStats().build();

	@Test
	public void loadCached() throws ExecutionException {
		String result = cache.get("key", new Callable<String>() {
			@Override
			public String call() {
				return "result";
			}
		});

		String result2 = cache.get("key2", new Callable<String>() {
			@Override
			public String call() {
				return "result2";
			}
		});

		String result3 = cache.get("key3", new Callable<String>() {
			@Override
			public String call() {
				return "result3";
			}
		});

		result = cache.get("key", new Callable<String>() {
			@Override
			public String call() {
				return "result";
			}
		});

		System.out.println(result);
		System.out.println(result2);
		System.out.println(result3);
		System.out.println(cache.getIfPresent("key"));

		//
		CacheStats cstats = cache.stats();
		System.out.println("loadCount:" + cstats.loadCount() + "  loadSuccessCount： " + cstats.loadSuccessCount());

		System.out.println("缓存命中率:" + cstats.hitRate() + " hitCount: " + cstats.hitCount());// 缓存命中率；
		System.out.println("加载新值的平均时间:" + cstats.averageLoadPenalty() + " 纳秒");// 加载新值的平均时间，单位为纳秒；
		System.out.println("缓存项被回收的总数:" + cstats.evictionCount());// 缓存项被回收的总数，不包括显式清除。
		System.out.println();

		// cache.asMap().entrySet()
		Set<String> set = cache.asMap().keySet();// 所有健
		Iterator<String> it = set.iterator();
		System.out.println("all key====");
		while (it.hasNext()) {

			System.out.print(it.next() + " \t ");
		}
		System.out.println();

	}

}
