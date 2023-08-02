package com.zoho.apis;

import java.util.ArrayList;
import java.util.HashMap;

import com.zoho.DB.CRUDImplementation;
import com.zoho.interfaces.UserInterface;
import com.zoho.model.UserDetails;


public class UserDetailsdao implements UserInterface {
	TimeConversion tm = new TimeConversion();
	Emaildao e = new Emaildao();

	public UserDetails getUser(int userid) {
		CRUDImplementation dbc = new CRUDImplementation();
		String classname = "UserDetails";
		ArrayList<String> columnname = new ArrayList<>();
		UserDetails ud = null;
		columnname.add("fname");
		columnname.add("lname");
		columnname.add("dob");
		columnname.add("gender");
		columnname.add("SpamOrNot");
		HashMap<HashMap<String, String>, String> where = new HashMap<HashMap<String, String>, String>();
		HashMap<String, String> hm = new HashMap<>();
		hm.put("usrid", Integer.toString(userid));
		hm.put("condition", "=");
		where.put(hm, null);
		try {
			ud = (UserDetails) dbc.selectOne(classname, columnname, where);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return ud;
	}

}
