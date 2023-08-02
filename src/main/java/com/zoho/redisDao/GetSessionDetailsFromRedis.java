package com.zoho.redisDao;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.charset.StandardCharsets;

import com.zoho.interfaces.SessionInterface;
import com.zoho.model.SessionContainer;

import com.zoho.utility.RedisCache;

public class GetSessionDetailsFromRedis implements SessionInterface {

	@Override
	public SessionContainer getSession(String sessionId) {
		String key = sessionId;
		SessionContainer session = new SessionContainer();
		String jsonString = RedisCache.getFromRedisAsSet(key);
		if (jsonString == null) {
			return null;
		}
		try {
			session = deserializeStringToObject(jsonString);
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return session;
	}

	public SessionContainer deserializeStringToObject(String json) throws IOException, ClassNotFoundException {

		byte[] byteArray = json.getBytes(StandardCharsets.ISO_8859_1);
		try (ByteArrayInputStream bis = new ByteArrayInputStream(byteArray);
				ObjectInputStream ois = new ObjectInputStream(bis)) {
			SessionContainer session = (SessionContainer) ois.readObject();
			return session;
		}

	}

	
}