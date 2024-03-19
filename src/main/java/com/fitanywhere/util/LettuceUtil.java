package com.fitanywhere.util;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.support.ConnectionPoolSupport;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

// Eugen
public class LettuceUtil {
    private static final String REDIS_HOST = "localhost";
    private static final int REDIS_PORT = 6379;
    private static final int DATABASE_INDEX = 0; // 指定Redis索引
    private static RedisClient redisClient;
    private static GenericObjectPool<StatefulRedisConnection<String, String>> pool;

    static {
        redisClient = RedisClient.create("redis://" + REDIS_HOST + ":" + REDIS_PORT);
        GenericObjectPoolConfig<StatefulRedisConnection<String, String>> poolConfig = new GenericObjectPoolConfig<>();
        poolConfig.setMaxTotal(25);
        poolConfig.setMinIdle(2);
        poolConfig.setMaxIdle(6);
        pool = ConnectionPoolSupport.createGenericObjectPool(() -> {
            StatefulRedisConnection<String, String> connection = redisClient.connect();
            connection.sync().select(DATABASE_INDEX); // 在這裡選擇數據庫
            return connection;
        }, poolConfig);
    }

    public static StatefulRedisConnection<String, String> getConnection() {
        try {
            return pool.borrowObject();
        } catch (Exception e) {
            throw new RuntimeException("Failed to borrow Redis connection from the pool", e);
        }
    }

    public static void returnConnection(StatefulRedisConnection<String, String> connection) {
        pool.returnObject(connection);
    }

    public static void close() {
        pool.close();
        redisClient.shutdown();
    }
}
