package org.xiem.com.redis;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Named;

import com.google.common.collect.ImmutableSet;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;

public class RedisAccess {

	// REDIS_HOST("redis.host")
	// REDIS_MASTER("redis.master")
	// REDIS_SERVERS("redis.servers")

    // final String redisMaster  = config.getString(ConfigurationKey.REDIS_MASTER,  "shadc-t1"                                  );
    // final String redisServers = config.getString(ConfigurationKey.REDIS_SERVERS, "redis1.t1.shadc.yosemitecloud.com:26379"   );
    // final String redisHost    = config.getString(ConfigurationKey.REDIS_HOST,    "redis1.t1.shadc.yosemitecloud.com"         );

	private final JedisSentinelPool pool;

	@Inject
	public RedisAccess(@Named("redis.master") String master, @Named("redis.servers") String servers) throws IOException {

		JedisPoolConfig poolConfig = new JedisPoolConfig();

		poolConfig.setMaxTotal(256);//

		pool = new JedisSentinelPool(master, ImmutableSet.copyOf(servers.split(",")));
	}

	public Jedis getResource() {//
		return pool.getResource();
	}
}
