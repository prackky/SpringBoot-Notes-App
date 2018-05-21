package com.sbnote.service;

import static com.sbnote.constants.RedisConstants.*;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public enum RedisService {
    INSTANCE;

    private final JedisPool pool;

    RedisService() {
        pool = new JedisPool(new JedisPoolConfig(), REDIS_HOST, REDIS_PORT);
    }

    public Long hsetnx(String field, String value) {
        Jedis jedis = null;
        Long confirmation;
        try{
            jedis = pool.getResource();
            confirmation = jedis.hsetnx(REDIS_DB_KEY, field, value);
            Long exp = jedis.expire(REDIS_DB_KEY, REDIS_KEY_EXPIRATION_SECONDS);
            System.out.println("Expiration time set for KEY: " + exp);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return confirmation;
    }

    public Long hdel(String field, String value) {
        Jedis jedis = null;
        Long confirm;
        try{
            jedis = pool.getResource();
            confirm = jedis.hdel(REDIS_DB_KEY, field, value);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return confirm;
    }

    public boolean hexists(String field) {
        Jedis jedis = null;
        try{
            jedis = pool.getResource();
            return jedis.hexists(REDIS_DB_KEY, field);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }
    
    public String hget(String field) {
        Jedis jedis = null;
        try{
            jedis = pool.getResource();
            return jedis.hget(REDIS_DB_KEY, field);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }
    
}