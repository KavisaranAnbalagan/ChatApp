package com.zoho.redisDao;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.charset.StandardCharsets;
import com.zoho.interfaces.UserInterface;
import com.zoho.model.UserDetails;
import com.zoho.utility.RedisCache;

public class GetUserDetailsFromRedis implements UserInterface {

	@Override
	public UserDetails getUser(int usrid) {
		String key = "usrid:" + usrid;
		UserDetails user = new UserDetails();
		String jsonString = RedisCache.getFromRedisAsSet(key);
		if (jsonString == null) {
			return null;
		}
		try {
			user = deserializeStringToObject(jsonString);
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return user;

	}

	public UserDetails deserializeStringToObject(String json) throws IOException, ClassNotFoundException {
		byte[] byteArray = json.getBytes(StandardCharsets.ISO_8859_1);
		try (ByteArrayInputStream bis = new ByteArrayInputStream(byteArray);
				ObjectInputStream ois = new ObjectInputStream(bis)) {
			UserDetails user = (UserDetails) ois.readObject();
			return user;
		}

	}

}
