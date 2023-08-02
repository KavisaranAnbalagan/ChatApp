package com.zoho.model;

import java.util.Collections;
import java.util.HashMap;

import java.util.LinkedList;
import java.util.Map;

public class LRUcache {
	private final int capacity = 100;
	private static Map<String, Object> cache = Collections.synchronizedMap(new HashMap<String, Object>());
	private static Map<Integer, Object> Usercache = Collections.synchronizedMap(new HashMap<Integer, Object>());
	private final static LinkedList<String> order = new LinkedList<>();
	private final static LinkedList<Integer> Userorder = new LinkedList<>();
	private static LRUcache instance;

	public static LRUcache getInstance() {
		if (instance == null) {
			synchronized (LRUcache.class) {
				if (instance == null) {
					instance = new LRUcache();
				}
			}
		}
		return instance;
	}

	public Object get(String cacheKey) {
		if (cache.get(cacheKey) != null) {
			order.remove(cacheKey);
			order.addLast(cacheKey);
			return cache.get(cacheKey);
		}
		return null;
	}

	public void put(String cacheKey, Object value) {
		if (cache.size() == capacity) {
			cache.remove(order.poll());
		}
		cache.put(cacheKey, value);
		order.addLast(cacheKey);

	}

	@SuppressWarnings("unlikely-arg-type")
	public void putUserDetails(int cacheKey, Object value) {
		if (Usercache.size() == capacity) {
			Usercache.remove(order.poll());
		}
		Usercache.put(cacheKey, value);
		Userorder.addLast(cacheKey);

	}

	public Object getUserDetails(int cacheKey) {
		if (Usercache.get(cacheKey) != null) {
			
			// Userorder.remove(cacheKey);
			// Userorder.addLast(cacheKey);
		
			return Usercache.get(cacheKey);
		}
		return null;
	}

	public static void clear(String cacheKey) {
		cache.remove(cacheKey);
		order.remove(cacheKey);
	}
}
