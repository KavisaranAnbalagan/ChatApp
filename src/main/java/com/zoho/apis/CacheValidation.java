package com.zoho.apis;

import java.sql.SQLException;

import java.text.ParseException;

import com.zoho.model.SessionContainer;
import com.zoho.utility.ProxyObject;
import com.zoho.interfaces.*;

/**
 * Class for validating session cache
 */
public class CacheValidation {

	private static final long THREE_MINUTES_IN_MILLIS = 3 * 60 * 1000L;

	/**
	 * Checks the validity of a session ID
	 * 
	 * @return an integer indicating the session status (1 = valid, 2 = expired, 3 =
	 *         invalid)
	 * 
	 */
	public static int checkId(String sessionid) throws ParseException, SQLException {
		
		int check = 1;
		TimeConversion tc = new TimeConversion();
		SessionContainerdao sessioncontainerdao = new SessionContainerdao();
		SessionInterface sessionProxy = ProxyObject.getSessionProxy();
		SessionContainer sessioncontainer = (SessionContainer) sessionProxy.getSession(sessionid);

		if (sessioncontainer != null) {
			long sessionCreationTime = sessioncontainer.getTime();

			if ((sessionCreationTime + THREE_MINUTES_IN_MILLIS) < tc.getCurrentTime()) {
				// Expired session
				SessionInterfaceForCUD sessionProxyForCUD = (SessionInterfaceForCUD )sessionProxy;
				sessionProxyForCUD.deleteSessionRecords(sessionid);
				sessioncontainerdao.deleteSessionRecords(sessionid);
				sessioncontainerdao.deleteKey(sessionid);
				check = 2;
				return check;
			}

			// Valid session
			return check;
		} else {
			// Invalid session
			sessioncontainerdao.deleteSessionRecords(sessionid);
			sessioncontainerdao.deleteKey(sessionid);
			check = 3;
			return check;
		}
	}

}
