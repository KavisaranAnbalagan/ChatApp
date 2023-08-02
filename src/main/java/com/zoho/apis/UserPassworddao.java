package com.zoho.apis;

import java.util.ArrayList;
import java.util.HashMap;

import com.zoho.DB.CRUDImplementation;

import com.zoho.model.UserPassword;
import com.zoho.utility.*;

public class UserPassworddao {
	CRUDImplementation dbc = new CRUDImplementation();

	public boolean validatePassword(String password, int userid) {
		CRUDImplementation dbc = new CRUDImplementation();
		String classname = "UserPassword";
		ArrayList<String> columnname = new ArrayList<>();
		columnname.add("password");
		HashMap<HashMap<String, String>, String> where = new HashMap<HashMap<String, String>, String>();
		HashMap<String, String> hm = new HashMap<>();
		hm.put("usr_id", String.valueOf(userid));
		hm.put("condition","=");
		where.put(hm,null);
		UserPassword userpasswordInstance = null;
		try {
			userpasswordInstance = (UserPassword) dbc.selectOne(classname, columnname, where);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String dbpassword = userpasswordInstance.getPassword();

		boolean check = PasswordHashing.PassswordDecryption(password, dbpassword);

		return check;
	}
}
