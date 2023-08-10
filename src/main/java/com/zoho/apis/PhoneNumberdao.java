package com.zoho.apis;

import java.util.ArrayList;
import java.util.HashMap;

import com.zoho.DB.CRUDImplementation;
import com.zoho.model.*;

public class PhoneNumberdao {
	private static CRUDImplementation dbc = new CRUDImplementation();

	public int checkPhoneNumber(String phoneNumber) throws Exception {
		PhoneNumber phNo = null;
		String classname = "PhoneNumber";
		ArrayList<String> columnname = new ArrayList<>();
		columnname.add("usrid");
		HashMap<HashMap<String, String>, String> where = new HashMap<HashMap<String, String>, String>();
		HashMap<String, String> hm = new HashMap<>();
		hm.put("phonenumber", phoneNumber);
		hm.put("condition", "=");
		where.put(hm, null);
		try {
			phNo = (PhoneNumber) dbc.selectOne(classname, columnname, where);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return phNo.getUserid();
	}

	@SuppressWarnings("null")
	public static ArrayList<PhoneNumber> getAllPhno(int usrid) {
		String classname = "PhoneNumber";
		ArrayList<String> columnname = new ArrayList<>();
		ArrayList<ObjectFactory> OBArrayLists = null;
		ArrayList<PhoneNumber> phnoArrayLists = null;
		columnname.add("Phonenumber");
		HashMap<HashMap<String, String>, String> where = new HashMap<HashMap<String, String>, String>();
		HashMap<String, String> hm = new HashMap<>();
		hm.put("usrid", Integer.toString(usrid));
		hm.put("condition", null);
		where.put(hm, null);

		try {
			OBArrayLists = dbc.select(classname, columnname, where);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		PhoneNumber phNo;
		for (int i = 0; i < OBArrayLists.size(); i++) {
			phNo = (PhoneNumber) OBArrayLists.get(i);
			phnoArrayLists.add(phNo);
		}
		return phnoArrayLists;
	}
}
