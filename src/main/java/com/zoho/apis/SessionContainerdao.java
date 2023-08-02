package com.zoho.apis;

import java.sql.SQLException;

import java.util.*;

import com.zoho.DB.CRUDImplementation;
import com.zoho.interfaces.SessionInterface;
import com.zoho.model.SessionContainer;

import com.zoho.utility.RedisCache;

public class SessionContainerdao implements SessionInterface {

	public SessionContainer getSession(String sessionid) {
		CRUDImplementation dbc = new CRUDImplementation();
		SessionContainer sessionDetails = null;
		String classname = "SessionContainer";
		ArrayList<String> columnname = new ArrayList<>();
		columnname.add("usrid");
		columnname.add("sessionId");
		columnname.add("time");
		HashMap<HashMap<String, String>, String> where = new HashMap<HashMap<String, String>, String>();
		HashMap<String, String> hm = new HashMap<>();
		hm.put("sessionId", String.valueOf(sessionid));
		hm.put("condition", "=");
		where.put(hm, null);
		try {
			sessionDetails = (SessionContainer) dbc.selectOne(classname, columnname, where);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return sessionDetails;
	}

	public void deleteSessionRecords(String sessionid) {
		CRUDImplementation dbc = new CRUDImplementation();
		HashMap<HashMap<String, String>, String> hm1 = new HashMap<HashMap<String, String>, String>();
		HashMap<String, String> hm2 = new HashMap<>();
		hm2.put("sessionId", sessionid);
		hm2.put("condition", "=");
		String tablename = "SessionContainer";
		hm1.put(hm2, null);
		try {
			dbc.deleteRecords(tablename, hm1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void insertSessionData(int userid, String sessionid) {
		CRUDImplementation dbc = new CRUDImplementation();
		TimeConversion tc = new TimeConversion();
		HashMap<String, Object> sessionData = new HashMap<>();
		long time = tc.getCurrentTime();
		sessionData.put("usrid", userid);
		sessionData.put("time", time);
		sessionData.put("sessionId", sessionid);
		SessionContainer sessioncontainer = new SessionContainer(sessionData);
		try {
			dbc.insert(sessioncontainer);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void deleteKey(String sessionid) {
		RedisCache.deleteInRedis(sessionid);
	}

}