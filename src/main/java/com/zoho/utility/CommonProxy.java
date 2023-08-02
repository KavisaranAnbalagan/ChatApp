package com.zoho.utility;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import com.zoho.interfaces.RedisAnnotation;

public class CommonProxy implements InvocationHandler {

	private Object objectForDB;
	private Object objectForRedis;
	private Object objectForApi;

	public CommonProxy(Object objectForDB, Object objectForRedis, Object objectForApi) {
		this.objectForDB = objectForDB;
		this.objectForRedis = objectForRedis;
		this.objectForApi = objectForApi;
	}

	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

		Object result = null;
		RedisCache redisCache = new RedisCache();
		if (redisCache.redisAvailableOrNot()) {
			RedisAnnotation proxyInterfaceAnnotation = method.getAnnotation(RedisAnnotation.class);
			if (proxyInterfaceAnnotation != null) {
				result = method.invoke(objectForRedis, args);
			}
			if (result == null) {
				result = method.invoke(objectForApi, args);
			}

		} else {
			result = method.invoke(objectForDB, args);
		}

		return result;
	}

}
