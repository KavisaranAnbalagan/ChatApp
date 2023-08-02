package com.zoho.utility;

import java.io.ByteArrayOutputStream;

import java.io.ObjectOutputStream;
import java.nio.charset.StandardCharsets;



public class ConvertForStoreInRedis {

	public static String convertObjectToJsonString(Object ob) throws Exception {
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeObject(ob);
			oos.close();
			String str = new String(bos.toByteArray(), StandardCharsets.ISO_8859_1);

			return str;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
