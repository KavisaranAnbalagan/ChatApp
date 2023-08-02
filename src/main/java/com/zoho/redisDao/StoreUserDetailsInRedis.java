package com.zoho.redisDao;

import com.zoho.model.UserDetails;
import com.zoho.utility.ConvertForStoreInRedis;
import com.zoho.utility.RedisCache;

public class StoreUserDetailsInRedis {
	public void storeUserInRedis(int usrid, Object ob) throws Exception {

		UserDetails ud = (UserDetails) ob;
		String key = "usrid:" + usrid;

		String jsonString = ConvertForStoreInRedis.convertObjectToJsonString(ud);

		RedisCache.storeInRedisAsSet(key, jsonString);

	}

}
