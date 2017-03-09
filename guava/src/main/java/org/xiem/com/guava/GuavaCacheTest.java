package org.xiem.com.guava;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import com.google.common.util.concurrent.ListenableFuture;

public class GuavaCacheTest {// 谷歌的本地缓存--http://blog.csdn.net/liangrui1988/article/details/46120533#

	// GUAVACACHE是一种本地缓存实现,支持多种缓存过期策略
	// GUAVACACHE与CONCURRENTMAP很相似,但也不完全一样.最基本的区别是CONCURRENTMAP会一直保存所有添加的元素,直到显式地移除
	// 而GUAVACACHE为了限制内存占用通常都设定为自动回收元素
	// 尽管LOADINGCACHE不回收元素它也是很有用的因为它会自动加载缓存

	public static void main(String[] args) throws ExecutionException {

		Test001();

		Test002();

//		LoadingCache<Key, Graph> graphs = CacheBuilder.newBuilder()
//				.maximumSize(1000)
//				.expireAfterWrite(10, TimeUnit.MINUTES)
//				.removalListener(MY_LISTENER)
//				.build(new CacheLoader<Key, Graph>() {
//					@Override
//					public Graph load(Key key) throws AnyException {
//						return createExpensiveGraph(key);
//					}
//				});
	}

	@Test
	public void testUserCacheLoader() throws ExecutionException {

		final List<Person> list = new ArrayList<Person>(5);

		list.add(new Person("1", "zhangsan"));
		list.add(new Person("2", "lisi"));
		list.add(new Person("3", "wangwu"));

		LoadingCache<String, Person> cache = CacheBuilder.newBuilder()
				.refreshAfterWrite(1, TimeUnit.MINUTES)// 给定时间内没有被读/写访问则回收
				.expireAfterWrite(5, TimeUnit.SECONDS)// 给定时间内没有写访问则回收
				.expireAfterAccess(3, TimeUnit.SECONDS)// 缓存过期时间为3秒
				.maximumSize(100)// 设置缓存个数
				.build(new CacheLoader<String, Person>() {
					@Override
					public Person load(String key) throws ExecutionException {// 当本地缓存没有命中时调用LOAD方法获取结果并将结果缓存
						return getPerson(key);
					}

					private Person getPerson(String key) throws ExecutionException {// 此时一般我们会进行相关处理(如到数据库去查询)
						for (Person p : list) {
							if (p.getId().equals(key)) {
								return p;
							}
						}
						return null;
					}
				});

		cache.get("1");
		cache.get("2");
		cache.get("3");
		System.out.println("======= sencond time  ==========");
		cache.get("1");
		cache.get("2");
		cache.get("3");
	}

	@Test
	public void testUserCallback() throws ExecutionException {

		final List<Person> list = new ArrayList<Person>(5);

		list.add(new Person("1", "zhangsan"));
		list.add(new Person("2", "lisi"));
		list.add(new Person("3", "wangwu"));

		final String key = "1";

		Cache<String, Person> cache2 = CacheBuilder.newBuilder().maximumSize(1000).build();

		@SuppressWarnings("unused")
		Person person = cache2.get(key, new Callable<Person>() {// 可以用一个CACHE对象缓存多种不同的数据(只需创建不同的CALLABLE对象即可)
			@Override
			public Person call() throws ExecutionException {
				return getPerson(key);
			}
			private Person getPerson(String key) throws ExecutionException {
				for (Person p : list) {
					if (p.getId().equals(key)) {
						return p;
					}
				}
				return null;
			}
		});

		System.out.println("======= sencond time  ==========");

		person = cache2.getIfPresent(key);
		person = cache2.getIfPresent(key);
	}

	@Test
	public void testListener() throws ExecutionException {// 关于移除监听器

		CacheLoader<String, Person> loader = new CacheLoader<String, Person>() {

			@Override
			public Person load(String key) throws ExecutionException {
				return getPerson(key);
			}

			private Person getPerson(String key) throws ExecutionException {
				return new Person(key, "zhang" + key);
			}
		};

		RemovalListener<String, Person> removalListener = new RemovalListener<String, Person>() {
			@Override
			public void onRemoval(RemovalNotification<String, Person> removal) {
				System.out.println("cause:" + removal.getCause() + " key:" + removal.getKey() + " value:" + removal.getValue());
			}
		};

		LoadingCache<String, Person> cache = CacheBuilder.newBuilder()
				.expireAfterWrite(2, TimeUnit.MINUTES)
				.maximumSize(1024)
				.removalListener(removalListener)// 可以声明一个监听器从而可以在缓存被移除时做一些其他的操作
				.build(loader);
		
		// cache.put(key, value); 显式插入:该方法可以直接向缓存中插入值(如果缓存中有相同KEY则之前的会被覆盖)
		// 显式清除:我们也可以对缓存进行手动清除
		// cache.invalidate(key); //单个清除
		// cache.invalidateAll(keys); //批量清除
		// cache.invalidateAll(); //清除所有缓存项
		// 基于时间的移除
		// expireAfterAccess(long, TimeUnit); 该键值对最后一次访问后超过指定时间再移除
		// expireAfterWrite(long, TimeUnit); 该键值对被创建或值被替换后超过指定时间再移除
		// 基于大小的移除:指如果缓存的对象格式即将到达指定的大小就会将不常用的键值对从CACHE中移除
		// cacheBuilder.maximumSize(long)
		// SIZE是指CACHE中缓存的对象个数(当缓存的个数开始接近SIZE的时候系统就会进行移除的操作)
		// 缓存清除执行的时间
		//
		// 使用CACHEBUILDER构建的缓存不会"自动"执行清理和回收工作,也不会在某个缓存项过期后马上清理,也没有诸如此类的清理机制.
		// 它是在写操作时顺带做少量的维护工作(清理);如果写操作太少,读操作的时候也会进行少量维护工作.因为如果要自动地持续清理缓存,
		// 就必须有一个线程.这个线程会和用户操作竞争共享锁/在某些环境下线程创建可能受限制,这样CACHEBUILDER就不可用了.
		
		
		cache.get("1");// 放入缓存
		cache.get("1");// 第二次获取(此时从缓存中获取)
		cache.invalidate("1");// 移除缓存
		cache.get("1");// 重新获取
		cache.get("1");// 再次获取(此时从缓存中获取)
	}

	private static void Test002() throws ExecutionException {// 方法二:创建缓存对像

		Cache<String, String> cache = CacheBuilder.newBuilder()
				.expireAfterWrite(10, TimeUnit.SECONDS)
				.maximumSize(10000)
				.build();

		// 调用缓存中的GET方法(当缓存命中时直接返回结果当不命中时通过给定的CALLABLE类的CALL方法返回结果再缓存


		cache.get("111", new Callable<String>() {// 这个方法倒更灵活:可以用一个CACHE对象缓存多种不同的数据只要用不同的CALLABLE对象就行
			@Override
			public String call() throws Exception {
				System.out.println("经过CALL()");
				return "value.";
			}
		});

		cache.get("222", new Callable<String>() {
			@Override
			public String call() throws Exception {
				System.out.println("经过CALL()");
				return "value.";
			}
		});
	}

	private static void Test001() throws ExecutionException {// 方法一:创建本地缓存(当本地缓存不命中时调用LOAD方法返回结果再缓存结果)

		// 注意:泛型参数指定了根据何种类型得到何种类型

		LoadingCache<String, String> loadingCache = CacheBuilder.newBuilder()
				.expireAfterWrite(10, TimeUnit.SECONDS)
				.maximumSize(10000)
				.recordStats()// 用来开启GUAVACACHE的统计功能: Cache.stats()
				.build(new CacheLoader<String, String>() {
					@Override
					public String load(String key) throws Exception {
						// 1、声明了异常: get(K)
						// 2、没有声明异常: getUnchecked(K)
						// 3、批量查询: getAll(Iterable<? extends K>)
						return getString(key);
					}

					@Override
					public ListenableFuture<String> reload(String key, String oldValue) throws Exception {
						return super.reload(key, oldValue);
					}

					@Override
					public Map<String, String> loadAll(Iterable<? extends String> keys) throws Exception {
						return super.loadAll(keys);
					}

				});

		loadingCache.get("test1");
		loadingCache.get("test2");

		loadingCache.stats().hitCount();
		loadingCache.stats().hitRate();// 缓存命中率
		loadingCache.stats().averageLoadPenalty();// 加载新值的平均时间(单位为纳秒)
		loadingCache.stats().evictionCount();// 缓存项被回收的总数(不包括显式清除)

		loadingCache.asMap();
		// ASMAP视图提供了缓存的CONCURRENTMAP形式但ASMAP视图与缓存的交互需要注意:
		// CACHE.ASMAP()包含当前所有加载到缓存的项
		// 因此相应地CACHE.ASMAP().KEYSET()包含当前所有已加载键
		// CACHE.ASMAP().GET(KEY)实质上等同于CACHE.GETIFPRESENT(KEY)而且不会引起缓存项的加载
		// 这和MAP的语义约定一致;所有读写操作都会重置相关缓存项的访问时间:包括Cache.asMap().get(Object)方法和Cache.asMap().put(K,V)方法
		// 但不包括Cache.asMap().containsKey(Object)方法,也不包括在Cache.asMap()的集合视图上的操作.
		// 比如遍历Cache.asMap().entrySet()不会重置缓存项的读取时间
	}

	public static String getString(String key) {

		System.out.println("经过GETSTRING()");

		return key + "--Test";

	}

	public static class Person {

		public String id;
		public String name;

		public Person(String id, String name) {
			this.id = id;
			this.name = name;
		}

		public String getId() {
			return id;
		}

	}
}
