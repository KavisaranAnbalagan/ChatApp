package com.zoho.api;

import com.zoho.apis.SessionContainerdao;
import com.zoho.interfaces.SessionInterfaceForCUD;
import com.zoho.model.SessionContainer;

import com.zoho.redisDao.StoreSessionDetailsInRedis;

public class SessionApi implements SessionInterfaceForCUD {
	public SessionContainer getSession(String sessionId) {

		StoreSessionDetailsInRedis sessionRedis = new StoreSessionDetailsInRedis();
		SessionContainerdao sessionObject = new SessionContainerdao();
		SessionContainer sessionDetails = sessionObject.getSession(sessionId);
		if (sessionDetails != null) {
			try {
				sessionRedis.storeSessionInRedis(sessionId, sessionDetails);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return sessionDetails;
	}

	@Override
	public void deleteSessionRecords(String sessionid) {
		// TODO Auto-generated method stub
	}
}
