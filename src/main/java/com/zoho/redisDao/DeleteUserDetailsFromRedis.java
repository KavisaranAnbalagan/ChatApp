package com.zoho.redisDao;

import com.zoho.utility.RedisCache;

public class DeleteUserDetailsFromRedis {
	public void deleteUserFromRedis(int userId) {
		String key = "usrid:" + Integer.toString(userId);
		RedisCache.deleteInRedis(key);
	}
}
