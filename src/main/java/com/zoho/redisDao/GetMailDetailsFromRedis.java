package com.zoho.redisDao;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.charset.StandardCharsets;

import java.util.ArrayList;
import com.zoho.interfaces.EmailInterface;
import com.zoho.model.Email;
import com.zoho.utility.RedisCache;

public  class GetMailDetailsFromRedis implements EmailInterface {
	public Email getMail(String mailId) {

		String key = mailId;

		String jsonString = RedisCache.getFromRedisAsSet(key);
		if (jsonString == null) {
			return null;
		}
		try {
			return deserializeStringToObject(jsonString);
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ArrayList<String> getAllMail(int userId) {
		ArrayList<String> allMails = new ArrayList<String>();
		allMails = RedisCache.getFromRedisASLists("Mailids" + Integer.toString(userId));

		if (allMails.size() == 0) {
			return null;
		}
		return allMails;
	}

	public String getPrimary(int userId) {
		String key = "isprime:" + Integer.toString(userId);
		String primeMail = RedisCache.getFromRedisAsSet(key);
		return primeMail;
	}

	public ArrayList<String> getVerifiedOrUnVerified(int userId, int status) {
		ArrayList<String> mails = new ArrayList<String>();
		if (status == 1) {
			String key = "verified" + Integer.toString(userId);
			mails = RedisCache.getFromRedisASLists(key);

		} else {
			String key = "unVerified" + Integer.toString(userId);
			mails = RedisCache.getFromRedisASLists(key);
		}
		if (mails.size() == 0) {
			return null;
		}
		return mails;
	}

	public Email deserializeStringToObject(String json) throws ClassNotFoundException, IOException {
		byte[] byteArray = json.getBytes(StandardCharsets.ISO_8859_1);
		try (ByteArrayInputStream bis = new ByteArrayInputStream(byteArray);
				ObjectInputStream ois = new ObjectInputStream(bis)) {
			Email email = (Email) ois.readObject();
			return email;
		}

	}

}
