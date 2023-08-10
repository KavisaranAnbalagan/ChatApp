package com.zoho.redisDao;

import java.util.ArrayList;

import com.zoho.model.Email;
import com.zoho.model.ObjectFactory;
import com.zoho.model.SessionContainer;
import com.zoho.model.UserDetails;

public class DeleteInRedis {

	public void deleteEmail(ObjectFactory oldvalue, ObjectFactory currentValue, ArrayList<String> columns,
			String action) {
		System.out.println("oldvalue="+oldvalue);
		System.out.println("newvalue="+currentValue);
		Email currentvalue = (Email) currentValue;
		int userId = currentvalue.getUserid();
		String mailId = currentvalue.getMailid();
		int verifyNewValue = currentvalue.getIsverified();
		DeleteMailDetailsFromRedis deleteMail = new DeleteMailDetailsFromRedis();
		DeleteUserDetailsFromRedis deleteUser = new DeleteUserDetailsFromRedis();

		// Action: Update
		if (action.equals("update")) {
			Email oldValue = (Email) oldvalue;
			deleteMail.deleteMailFromRedis(mailId);
			for (int i = 0; i < columns.size(); i++) {

				if (columns.get(i).equals("verified")) {
					deleteMail.deleteVerifiedAndUnVerified(userId, oldValue.getIsverified());
					
					deleteMail.deleteVerifiedAndUnVerified(userId, verifyNewValue);
				}
				if (columns.get(i).equals("isprime")) {
					deleteMail.deletePrimaryInRedis(userId);
				}

			}

		}
		// Action: Insert or Delete
		else {
			if (action.equals("insert")) {
				deleteMail.deleteAllMailsInRedis(userId);
				deleteUser.deleteUserFromRedis(userId);
			} else if (action.equals("delete")) {
				deleteMail.deleteAllMailsInRedis(userId);
				deleteMail.deleteMailFromRedis(mailId);
				deleteUser.deleteUserFromRedis(userId);
			}
			deleteMail.deleteVerifiedAndUnVerified(userId, verifyNewValue);
			if (currentvalue.getIsprime() == 1) {
				deleteMail.deletePrimaryInRedis(userId);
			}
		}

	}

	public void deleteUserDetails(ObjectFactory oldvalue, ObjectFactory currentValue, ArrayList<String> columns,
			String action) {
		UserDetails user = (UserDetails) currentValue;
		DeleteUserDetailsFromRedis deleteUser = new DeleteUserDetailsFromRedis();
		deleteUser.deleteUserFromRedis(user.getUsrid());
	}

	public void deleteSessionContainer(ObjectFactory oldvalue, ObjectFactory currentValue, ArrayList<String> columns,
			String action) {
		SessionContainer session = (SessionContainer) currentValue;
		DeleteSessionDetailsFromRedis deleteSession = new DeleteSessionDetailsFromRedis();
		deleteSession.deleteSessionInRedis(session.getSessionid());
	}
}
