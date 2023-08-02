package com.zoho.api;


import com.zoho.apis.UserDetailsdao;
import com.zoho.interfaces.UserInterFaceForCUD;
import com.zoho.model.UserDetails;
import com.zoho.redisDao.*;


public class UserApi implements UserInterFaceForCUD{
	public UserDetails getUser(int userId) {
		StoreUserDetailsInRedis userRedis = new StoreUserDetailsInRedis();
		UserDetailsdao userDetailsObject = new UserDetailsdao();
		UserDetails userDetails = userDetailsObject.getUser(userId);
		if (userDetails != null) {
			try {
				userRedis.storeUserInRedis(userId, userDetails);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return userDetails;
	}
}
