package com.zoho.api;

import java.util.ArrayList;

import com.zoho.apis.Emaildao;
import com.zoho.interfaces.EmailInterfaceForCUD;
import com.zoho.model.Email;
import com.zoho.redisDao.DeleteMailDetailsFromRedis;
import com.zoho.redisDao.StoreMailDetailsInRedis;

public class EmailApi implements EmailInterfaceForCUD {
	public Email getMail(String mailId) {
		Emaildao emailDao = new Emaildao();
		Email email = emailDao.getMail(mailId);
		StoreMailDetailsInRedis storeredisObject = new StoreMailDetailsInRedis();
		if (email != null) {
			try {
				storeredisObject.storeEmailInRedis(mailId, email);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return email;
	}

	public ArrayList<String> getAllMail(int userId) {
		// TODO Auto-generated method stub
		Emaildao emailDao = new Emaildao();
		StoreMailDetailsInRedis redisObject = new StoreMailDetailsInRedis();
		ArrayList<String> allEmails = null;
		allEmails = emailDao.getAllMail(userId);

		if (allEmails != null) {

			redisObject.setMailInRedisAsLists(allEmails, userId);

		}
		return allEmails;
	}

	public String getPrimary(int userId) {
		// TODO Auto-generated method stub
		Emaildao emailDao = new Emaildao();
		String mailId = emailDao.getPrimary(userId);
		if (mailId != null) {
			StoreMailDetailsInRedis mdr = new StoreMailDetailsInRedis();

			mdr.setPrimary(userId, mailId);
		}
		return mailId;
	}

	public ArrayList<String> getVerifiedOrUnVerified(int userId, int status) {
		Emaildao emailDao = new Emaildao();
		emailDao.getVerifiedOrUnVerified(userId, status);
		ArrayList<String> mails = emailDao.getVerifiedOrUnVerified(userId, status);
		StoreMailDetailsInRedis redisObject = new StoreMailDetailsInRedis();
		if (status == 1) {
			redisObject.storeVerifiedStatusInRedis(userId, mails);
		} else {
			redisObject.storeUnVerifiedStatusInRedis(userId, mails);
		}
		return mails;
	}

	@Override
	public void makeAsPrimary(String mailId, int userId) {
		DeleteMailDetailsFromRedis instance = new DeleteMailDetailsFromRedis();
		Emaildao emailDao = new Emaildao();
		emailDao.makeAsPrimary(mailId, userId);
		instance.deleteAllMailsInRedis(userId);
		instance.deleteMailFromRedis(mailId);
	}

	@Override
	public void addNewMail(int userid, String mailId) {
		// TODO Auto-generated method stub
		Emaildao emailDao = new Emaildao();
		emailDao.addNewMail(userid, mailId);
	}

	@Override
	public Object deleteMail(String mailID) {
		Emaildao emailDao = new Emaildao();
		emailDao.deleteMail(mailID);
		return null;
	}

	@Override
	public void makeAsVerified(String mailId, int userId) {
		Emaildao emailDao = new Emaildao();
		emailDao.makeAsVerified(mailId, userId);
		
	}

}
