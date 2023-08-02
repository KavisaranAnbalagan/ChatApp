package com.zoho.redisDao;

import java.lang.reflect.Method;
import java.util.ArrayList;

import com.zoho.model.ObjectFactory;
import com.zoho.utility.RedisCache;

public class RedisDeletion {
	public void deleteInRedis(ObjectFactory ob, String tableName,ArrayList<String> columns,String action) {
		RedisCache redisCache = new RedisCache();
		if (redisCache.redisAvailableOrNot()) {
			Class<?> myClass = DeleteInRedis.class;
			try {
				Object myClassInstance = myClass.getDeclaredConstructor().newInstance();
				String methodName = "delete" + tableName;
				System.out.println(methodName+columns+""+action);
				Method method = myClass.getMethod(methodName, ObjectFactory.class,ArrayList.class,String.class);
				method.invoke(myClassInstance, ob,columns,action);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
