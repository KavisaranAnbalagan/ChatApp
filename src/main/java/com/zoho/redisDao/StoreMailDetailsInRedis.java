package com.zoho.redisDao;

import java.util.ArrayList;

import com.zoho.model.Email;
import com.zoho.utility.ConvertForStoreInRedis;
import com.zoho.utility.RedisCache;

public class StoreMailDetailsInRedis {

	public void storeEmailInRedis(String mailId, Email email) throws Exception {
		String key = mailId;
		String jsonString = ConvertForStoreInRedis.convertObjectToJsonString(email);
		RedisCache.storeInRedisAsSet(key, jsonString);
	}

	public void setMailInRedisAsLists(ArrayList<String> allMails, int userId) {
		String keyRedis = "Mailids" + userId;
		RedisCache.storeInRedisAsLists(keyRedis, allMails);
	}

	public void setPrimary(int userId, String mailId) {
		String key = "isprime:" + Integer.toString(userId);
		RedisCache.storeInRedisAsSet(key, mailId);
	}

	public void storeVerifiedStatusInRedis(int userId, ArrayList<String> verifiedMails) {
		String key = "verified" + Integer.toString(userId);
		RedisCache.storeInRedisAsLists(key, verifiedMails);
	}

	public void storeUnVerifiedStatusInRedis(int userId, ArrayList<String> unVerifiedMails) {
		String key = "unVerified" + Integer.toString(userId);
		RedisCache.storeInRedisAsLists(key, unVerifiedMails);
	}
}
