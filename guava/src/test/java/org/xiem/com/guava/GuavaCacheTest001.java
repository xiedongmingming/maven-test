package org.xiem.com.guava;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheStats;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import com.google.common.cache.Weigher;

/**
 * CACHE的参数说明：
 * 
 * 回收的参数： 1. 大小的设置：CacheBuilder.maximumSize(long) CacheBuilder.weigher(Weigher)
 * CacheBuilder.maxumumWeigher(long) 2. 时间：expireAfterAccess(long, TimeUnit)
 * expireAfterWrite(long, TimeUnit) 3. 引用：CacheBuilder.weakKeys()
 * CacheBuilder.weakValues() CacheBuilder.softValues() 4. 明确的删除：invalidate(key)
 * invalidateAll(keys) invalidateAll() 5.
 * 删除监听器：CacheBuilder.removalListener(RemovalListener)
 * 
 * refresh机制： 1. LoadingCache.refresh(K) 在生成新的value的时候，旧的value依然会被使用。 2.
 * CacheLoader.reload(K, V) 生成新的value过程中允许使用旧的value 3.
 * CacheBuilder.refreshAfterWrite(long, TimeUnit) 自动刷新cache
 *
 *
 */
public class GuavaCacheTest001 {

	/**
	 * 基于容量的回收
	 *
	 *
	 * maximumSize(1) 缓存将尝试回收最近没有使用或总体上很少使用的缓存项
	 * 
	 * 不同的缓存项有不同的“权重”（weights）——例如，如果你的缓存值
	 * ，占据完全不同的内存空间，你可以使用CacheBuilder.weigher(Weigher)指定一个权重函数
	 * 
	 */

	@Test
	public void callablex() throws ExecutionException, InterruptedException {
		// .maximumSize(100)
		Cache<String, User2> cache = CacheBuilder.newBuilder().maximumWeight(5).weigher(new Weigher<String, User2>() {
			@Override
			public int weigh(String arg0, User2 user) {
				return 3;
			}
		}).removalListener(new RemovalListener<String, User2>() {
			@Override
			public void onRemoval(RemovalNotification<String, User2> rn) {
				System.out.println(rn.getKey() + "==被移除");
			}

		}).build();

		User2 result = cache.get("key", new Callable<User2>() {
			@Override
			public User2 call() {
				return new User(1, "liang");
			}
		});

		// Thread.sleep(10000);

		User result2 = (User) cache.get("key2", new Callable<User2>() {
			@Override
			public User2 call() {
				return new User(2, "liang2");
			}
		});

		User result3 = (User) cache.get("key3", new Callable<User>() {
			@Override
			public User call() {
				return new User(3, "liang3");
			}
		});

		System.out.println(result);
		System.out.println(result2);
		System.out.println(result3);
		System.out.println(cache.size());
	}

	/**
	 * 
	 * 
	 * 定时回收（Timed Eviction） expireAfterAccess(long,
	 * TimeUnit)：缓存项在给定时间内没有被读/写访问，则回收。请注意这种缓存的回收顺序和基于大小回收一样。
	 * expireAfterWrite(long,
	 * TimeUnit)：缓存项在给定时间内没有被写访问（创建或覆盖），则回收。如果认为缓存数据总是在固定时候后变得陈旧不可用，这种回收方式是可取的。
	 * 
	 * 
	 * 
	 * // .expireAfterWrite(5, TimeUnit.SECONDS)//给定时间内没有写访问，则回收。 27 //
	 * .expireAfterAccess(3, TimeUnit.SECONDS)// 缓存过期时间为3秒
	 * 
	 * @param args
	 */

	Cache<String, User2> cache2 = CacheBuilder.newBuilder().maximumSize(100).expireAfterWrite(3, TimeUnit.MILLISECONDS)
			// .expireAfterAccess(3000, TimeUnit.MILLISECONDS)
			.removalListener(new RemovalListener<String, User2>() {
				@Override
				public void onRemoval(RemovalNotification<String, User2> rn) {
					System.out.println(
							"Cause:" + rn.getCause() + " k: " + rn.getKey() + " v :" + rn.getValue() + "==被移除");
				}

			}).build();

	@Test
	public void timerEvication() throws ExecutionException, InterruptedException {

		User2 user = cache2.get("k1", new Callable<User2>() {
			@Override
			public User2 call() throws Exception {

				return new User(100, "hello");
			}
		});
		Thread.sleep(8000);
		System.out.println(cache2.size());

		User2 user2 = cache2.get("k2", new Callable<User2>() {
			@Override
			public User2 call() throws Exception {

				return new User(200, "hello2");
			}
		});
		System.out.println(user);
		Thread.sleep(8000);
		user = cache2.get("k1", new Callable<User2>() {
			@Override
			public User2 call() throws Exception {

				return new User(10000000, "k1k1k1k1k1k1k1");
			}
		});

		System.out.println(cache2.size());

		User2 user3 = cache2.get("k3", new Callable<User2>() {
			@Override
			public User2 call() throws Exception {

				return new User(300, "hello3");
			}
		});

		System.out.println(user);
		System.out.println(user2);
		System.out.println(user3);

		Thread.sleep(10000);
		System.out.println(cache2.size());
		CacheStats status = cache2.stats();
		status.missCount();

	}

	/**
	 * 显式清除
	 * 
	 * 任何时候，你都可以显式地清除缓存项，而不是等到它被回收：
	 * 
	 * 个别清除：Cache.invalidate(key) 批量清除：Cache.invalidateAll(keys)
	 * 清除所有缓存项：Cache.invalidateAll()
	 * 
	 * @param args
	 */

	Cache<String, User2> cache3 = CacheBuilder.newBuilder().maximumSize(100)
			.removalListener(new RemovalListener<String, User2>() {
				@Override
				public void onRemoval(RemovalNotification<String, User2> rn) {
					System.out.println(
							"Cause:" + rn.getCause() + " k: " + rn.getKey() + " v :" + rn.getValue() + "==被移除");
				}

			}).build();

	@Test
	public void clear() throws ExecutionException {

		User2 u = cache3.get("u1", new Callable<User2>() {
			@Override
			public User2 call() throws Exception {
				System.out.println("exec call>>>return result");
				return new User(500, "world");
			}
		});

		System.out.println(u);

		u = cache3.get("u1", new Callable<User2>() {
			@Override
			public User2 call() throws Exception {
				System.out.println("exec call>>>return result");
				return new User(500, "world");
			}
		});
		System.out.println(u);
		cache3.invalidate("u1");
		u = cache3.get("u1", new Callable<User2>() {
			@Override
			public User2 call() throws Exception {
				System.out.println("exec call>>>return result");
				return new User(500, "world");
			}
		});

	}

	public class User2 {
		private int age;

		private User3 user;

		/**
		 * 
		 */
		public User2() {
			super();
		}

		/**
		 * @param age
		 */
		public User2(int age) {
			super();
			this.age = age;
		}

		public int getAge() {
			return age;
		}

		public void setAge(int age) {
			this.age = age;
		}

		@Override
		public String toString() {
			return "User2 [age=" + age + "]";
		}

		public User3 getUser() {
			return user;
		}

		public void setUser(User3 user) {
			this.user = user;
		}

	}

	public class User extends User2 {

		/**
		 * 
		 */
		public User() {
			super();
		}

		/**
		 * @param id
		 * @param name
		 */
		public User(Integer id, String name) {
			super();
			this.id = id;
			this.name = name;
		}

		private Integer id;
		private String name;

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	@Override
	public String toString()
	{
		return "User [id=" + id + ", name=" + name + "]";
	}

}
	public class User3 {
		private String pass;

		public String getPass() {
			return pass;
		}

		public void setPass(String pass) {
			this.pass = pass;
		}

	}
}
