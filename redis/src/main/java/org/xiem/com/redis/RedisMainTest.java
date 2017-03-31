package org.xiem.com;

import redis.clients.jedis.JedisPoolConfig;

public class RedisMainTest {

    public static void main(String[] args) {

        JedisPoolConfig poolConfig = new JedisPoolConfig();

        poolConfig.setMaxTotal(256);

        // JedisSentinelPool pool = new JedisSentinelPool(master,
        // ImmutableSet.copyOf(servers.split(",")));
    }

}
