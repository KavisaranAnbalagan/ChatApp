package com.zoho.utility;

import java.lang.reflect.Proxy;

import com.zoho.api.EmailApi;
import com.zoho.api.SessionApi;
import com.zoho.api.UserApi;
import com.zoho.apis.Emaildao;
import com.zoho.apis.SessionContainerdao;
import com.zoho.apis.UserDetailsdao;
import com.zoho.interfaces.EmailInterface;
import com.zoho.interfaces.EmailInterfaceForCUD;
import com.zoho.interfaces.SessionInterface;
import com.zoho.interfaces.SessionInterfaceForCUD;
import com.zoho.interfaces.UserInterFaceForCUD;
import com.zoho.interfaces.UserInterface;
import com.zoho.redisDao.GetMailDetailsFromRedis;
import com.zoho.redisDao.GetSessionDetailsFromRedis;
import com.zoho.redisDao.GetUserDetailsFromRedis;
public class ProxyObject {
	static UserInterFaceForCUD userProxy = null;
	static SessionInterfaceForCUD sessionProxy = null;
	static EmailInterfaceForCUD emailProxy = null;

	public static UserInterFaceForCUD getUserProxy() {
		if (userProxy == null) {
			synchronized (UserInterface.class) {
				if (userProxy == null)
					userProxy = (UserInterFaceForCUD) createProxy(new UserApi(), new GetUserDetailsFromRedis(),new UserDetailsdao(),
							UserInterFaceForCUD.class);
			}
		}
		return userProxy;
	}

	public static SessionInterfaceForCUD getSessionProxy() {

		if (sessionProxy == null) {
			synchronized (SessionInterface.class) {
				if (sessionProxy == null)
					sessionProxy = (SessionInterfaceForCUD) createProxy(new SessionApi(), new GetSessionDetailsFromRedis(),new SessionContainerdao(),
							SessionInterfaceForCUD.class);
			}
		}
		return sessionProxy;
	}

	public static EmailInterfaceForCUD getEmailProxy() {

		if (emailProxy == null) {
			synchronized (EmailInterface.class) {
				if (emailProxy == null)
					emailProxy = (EmailInterfaceForCUD) createProxy(new EmailApi(), new GetMailDetailsFromRedis(),new Emaildao(),
							 EmailInterfaceForCUD.class);
			}
		}
		return emailProxy;

	}

	public static Object createProxy(Object apiObj, Object redisObj,Object dbObj, Class<?>... interfaces) {
		return Proxy.newProxyInstance(dbObj.getClass().getClassLoader(), interfaces, new CommonProxy(dbObj, redisObj,apiObj));
	}
}
