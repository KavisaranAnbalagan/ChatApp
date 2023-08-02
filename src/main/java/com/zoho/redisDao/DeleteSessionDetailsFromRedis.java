package com.zoho.redisDao;
import com.zoho.utility.RedisCache;

public class DeleteSessionDetailsFromRedis {
	public void deleteSessionInRedis(String sessionId) {
		String key = sessionId;
		RedisCache.deleteInRedis(key);
		
	}

}
