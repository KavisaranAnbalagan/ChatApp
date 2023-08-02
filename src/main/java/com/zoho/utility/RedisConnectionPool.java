package com.zoho.utility;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisConnectionPool {
	private static RedisConnectionPool rediscpi;

	private RedisConnectionPool() {
		if (rediscpi != null) {
			throw new RuntimeException("Please use getInstance method");
		}
	}

	public static RedisConnectionPool getInstance() {

		if (rediscpi == null) {
			synchronized (RedisConnectionPool.class) {
				if (rediscpi == null) {
					rediscpi = new RedisConnectionPool();
				}
			}
		}
		return rediscpi;
	}

	public Jedis getconnection() {

		JedisPoolConfig poolConfig = new JedisPoolConfig();
		poolConfig.setMaxTotal(10); // Maximum number of connections in the pool
		try (// Create a Jedis pool
		JedisPool jedisPool = new JedisPool(poolConfig, "localhost", 6379)) {
			Jedis jedis = jedisPool.getResource();
			return jedis;
		}
	}

	public void destroyConnection(Jedis jedis) {
		jedis.close();
	}

}
