package com.zoho.redisDao;

import com.zoho.model.SessionContainer;
import com.zoho.utility.ConvertForStoreInRedis;
import com.zoho.utility.RedisCache;

public class StoreSessionDetailsInRedis {
	public void storeSessionInRedis(String sessionid, Object ob) throws Exception {
		SessionContainer sc = (SessionContainer) ob;
		String key = sessionid;
		System.out.println("sessionstoreInRedis="+sc.getUsrid());
		
		String jsonString = ConvertForStoreInRedis.convertObjectToJsonString(sc);
		System.out.println("session str=" + jsonString);
		RedisCache.storeInRedisAsSet(key, jsonString);
		System.out.println("session str=" + jsonString);

	}
}
