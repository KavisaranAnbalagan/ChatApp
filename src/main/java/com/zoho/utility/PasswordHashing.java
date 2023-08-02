package com.zoho.utility;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordHashing {
	public static Object PasswordEncryption(String userpassword) {
		String salt = BCrypt.gensalt();
		String hashedPassword = BCrypt.hashpw(userpassword, salt);
		return hashedPassword;
	}

	public static boolean PassswordDecryption(String userpassword, String hashedPassword) {
		
		if (BCrypt.checkpw(userpassword, hashedPassword)) {
			System.out.println("true");
			return true;
		} else {
			System.out.println("false");
			return false;
		}
	}
}
