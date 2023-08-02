package com.zoho.redisDao;

import com.zoho.model.Email;
import com.zoho.utility.RedisCache;

public class DeleteMailDetailsFromRedis {
	public void deleteAllMailsInRedis(int userId) {
		String key = "Mailids" + userId;
		RedisCache.deleteInRedis(key);
	}

	public void deletePrimaryInRedis(int userId) {
		String key = "isprime:" + Integer.toString(userId);
		RedisCache.deleteInRedis(key);
	}

	public void deleteMailFromRedis(String mailId) {
		if (mailId != null) {
			RedisCache.deleteInRedis(mailId);
		}

	}

	public void DeleteAllMailKeys(Email email) {
		deleteAllMailsInRedis(email.getUserid());
		deletePrimaryInRedis(email.getUserid());
		deleteMailFromRedis(email.getMailid());
	}

	public void deleteVerifiedAndUnVerified(int userId, int status) {
		if (status == 1) {

			String key = "verified" + Integer.toString(userId);
			RedisCache.deleteInRedis(key);
		} else {

			String key1 = "unVerified" + Integer.toString(userId);
			RedisCache.deleteInRedis(key1);
		}
	}
}
