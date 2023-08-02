package com.zoho.model;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;



@SuppressWarnings("serial")
public class SessionContainer extends ObjectFactory implements Serializable  {
	private String sessionid;
	private long time = 0;
	private int usrid;

	public SessionContainer(HashMap<String, Object> hm) {

		super("SessionContainer", hm);
		hashmapMethod(hm);

	}

	public SessionContainer() {
		// TODO Auto-generated constructor stub
	}

	public SessionContainer(int userid, String sessionid, long currentTime) {
		// TODO Auto-generated constructor stub
		this.sessionid = sessionid;
		this.usrid = userid;
		this.time = currentTime;
	}

	@SuppressWarnings("rawtypes")
	private void hashmapMethod(HashMap<String, Object> map) {

		Object ob;
		// TODO Auto-generated method stub
		for (Map.Entry m : map.entrySet()) {

			if (m.getKey().equals("sessionId")) {
				ob = m.getValue();
				String sid = (String) ob;
				System.out.println("number=" + sid);
				this.sessionid = sid;
			}
			if (m.getKey().equals("time")) {
				ob = m.getValue();
				long num = (long) ob;
				System.out.println("time=" + num);
				this.time = num;
			}
			if (m.getKey().equals("usrid")) {
				ob = m.getValue();
				this.usrid = (int) ob;

			}

		}

	}

	public int getUsrid() {
		return usrid;
	}

	public void setUsrid(int usrid) {
		this.usrid = usrid;
	}

	public String getSessionid() {
		return sessionid;
	}

	public void setSessionid(String sessionid) {
		this.sessionid = sessionid;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}
}
