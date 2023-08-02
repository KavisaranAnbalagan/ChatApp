package com.zoho.interfaces;

public interface EmailInterfaceForCUD extends EmailInterface {
	void makeAsPrimary(String mailId, int userid);

	void addNewMail(int userid, String mailId);

	Object deleteMail(String userKey);

	void makeAsVerified(String mailId, int userId);

}
