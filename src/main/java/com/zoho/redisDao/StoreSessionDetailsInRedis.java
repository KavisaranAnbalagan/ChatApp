package com.zoho.redisDao;

import com.zoho.model.SessionContainer;
import com.zoho.utility.ConvertForStoreInRedis;
import com.zoho.utility.RedisCache;

public class StoreSessionDetailsInRedis {
	public void storeSessionInRedis(String sessionid, Object ob) throws Exception {
		SessionContainer sc = (SessionContainer) ob;
		String key = sessionid;
		
		String jsonString = ConvertForStoreInRedis.convertObjectToJsonString(sc);
		RedisCache.storeInRedisAsSet(key, jsonString);

	}
}
