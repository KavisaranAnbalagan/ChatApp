package com.zoho.redisDao;

import java.util.ArrayList;

import com.zoho.model.Email;
import com.zoho.model.ObjectFactory;
import com.zoho.model.SessionContainer;
import com.zoho.model.UserDetails;

public class DeleteInRedis {

	public void deleteEmail(ObjectFactory ob, ArrayList<String> columns, String action) {
		Email email = (Email) ob;
		int userId = email.getUserid();
		String mailId = email.getMailid();
		int verifiedOrUnverified = email.getIsverified();
		DeleteMailDetailsFromRedis deleteMail = new DeleteMailDetailsFromRedis();
		DeleteUserDetailsFromRedis deleteUser = new DeleteUserDetailsFromRedis();

		// Action: Update
		if (action.equals("update")) {
			deleteMail.deleteMailFromRedis(mailId);
			for (int i = 0; i < columns.size(); i++) {
				if (columns.get(i).equals("verified")) {
					deleteMail.deleteVerifiedAndUnVerified(userId, 0);
					deleteMail.deleteVerifiedAndUnVerified(userId, 1);
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
			deleteMail.deleteVerifiedAndUnVerified(userId, verifiedOrUnverified);
			if (email.getIsprime() == 1) {
				deleteMail.deletePrimaryInRedis(userId);
			}
		}

	}

	public void deleteUserDetails(ObjectFactory ob, ArrayList<String> columns, String action) {
		UserDetails user = (UserDetails) ob;
		DeleteUserDetailsFromRedis deleteUser = new DeleteUserDetailsFromRedis();
		deleteUser.deleteUserFromRedis(user.getUsrid());
	}

	public void deleteSessionContainer(ObjectFactory ob, ArrayList<String> columns, String action) {
		SessionContainer session = (SessionContainer) ob;
		DeleteSessionDetailsFromRedis deleteSession = new DeleteSessionDetailsFromRedis();
		deleteSession.deleteSessionInRedis(session.getSessionid());
	}
}
