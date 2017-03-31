package org.xiem.com;

import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

public class RedisUtil {// 工具类

    static Response<String> set(Pipeline pipeline, String key, long val) {// 表示字符串键值对设置
        return pipeline.set(key, String.valueOf(val));
    }

    // ***************************************************************
    static Response<Long> hset(Pipeline pipeline, String table, int key, long val) {
        return hset(pipeline, table, String.valueOf(key), val);
    }
    static Response<Long> hset(Pipeline pipeline, String table, String key, long val) {
        return hset(pipeline, table, key, String.valueOf(val));
    }
    static Response<Long> hset(Pipeline pipeline, String table, String key, double val) {
        return hset(pipeline, table, key, String.valueOf(val));
    }
    static Response<Long> hset(Pipeline pipeline, String table, String key, String val) {// 底层实现:表示HASH表键值对设置(键值对全部为字符串)
        return pipeline.hset(table, key, val);
    }
    // ***************************************************************
    static Response<Long> hincrBy(Pipeline pipeline, String table, String key, long val) {// 增加
        return pipeline.hincrBy(table, key, val);
    }
    static Response<Long> hdel(Pipeline pipeline, String table, String key) {// 删除键
        return pipeline.hdel(table, key);
    }
}
