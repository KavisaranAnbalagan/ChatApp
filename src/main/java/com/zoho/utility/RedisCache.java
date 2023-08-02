package com.zoho.utility;

import redis.clients.jedis.Jedis;
import java.util.*;

public class RedisCache {

	public static void storeInRedisAsHashMap(String keyName, HashMap<String, String> values) {
		Jedis instance = RedisConnectionPool.getInstance().getconnection();
		String key = keyName;
		instance.hmset(key, values);
		instance.close();
	}

	public static void storeInRedisAsSet(String keyName, String value) {
		Jedis instance = RedisConnectionPool.getInstance().getconnection();
		String key = keyName;
		instance.set(key, value);
		instance.close();
	}

	public static String getFromRedisAsSet(String keyName) {
		Jedis instance = RedisConnectionPool.getInstance().getconnection();
		String value = instance.get(keyName);
		instance.close();
		return value;
	}

	public static HashMap<String, String> getFromRedisASHashMap(String key) {
		Jedis instance = RedisConnectionPool.getInstance().getconnection();
		HashMap<String, String> values = (HashMap<String, String>) instance.hgetAll(key);
		instance.close();
		return values;
	}

	public static void deleteInRedis(String key) {
		Jedis instance = RedisConnectionPool.getInstance().getconnection();
		instance.del(key);
		instance.close();
	}

	public static void storeInRedisAsLists(String keyRedis, ArrayList<String> values) {
		// TODO Auto-generated method stub
		Jedis instance = RedisConnectionPool.getInstance().getconnection();
		for (String value : values) {
			instance.rpush(keyRedis, value);
		}
		instance.close();
	}

	public static ArrayList<String> getFromRedisASLists(String keyRedis) {
		Jedis instance = RedisConnectionPool.getInstance().getconnection();
		List<String> myList = instance.lrange(keyRedis, 0, -1);
		instance.close();
		return (ArrayList<String>) myList;
	}

	public boolean redisAvailableOrNot() {
		return true;
	}
}
