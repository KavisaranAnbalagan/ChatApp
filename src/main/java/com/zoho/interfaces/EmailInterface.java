package com.zoho.interfaces;

import java.util.ArrayList;

import com.zoho.model.Email;

public interface EmailInterface {
	@RedisAnnotation("EmailInterface")
	Email getMail(String mailId);

	@RedisAnnotation("EmailInterface")
	ArrayList<String> getAllMail(int userId);

	@RedisAnnotation("EmailInterface")
	String getPrimary(int userId);

	@RedisAnnotation("EmailInterface")
	ArrayList<String> getVerifiedOrUnVerified(int userId, int status);
}
