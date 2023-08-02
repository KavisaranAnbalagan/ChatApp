package com.zoho.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.zoho.DB.CRUDImplementation;

@SuppressWarnings("serial")
public class UserPassword extends ObjectFactory implements Serializable  {

	private String password;
	CRUDImplementation dbc = new CRUDImplementation();
	HashMap<String, Object> hm = new HashMap<>();

	public UserPassword(HashMap<String, Object> hm) {
		super("UserPassword", hm);
		hashmapMethod(hm);
	}

	public String getPassword() {
		return password;
	}

	public UserPassword(String password) {
		this.password = password;
	}

	public void selectMethod(HashMap<String, Object> hm) {

	}

	public UserPassword() {
		// TODO Auto-generated constructor stub
	}

	Object ob;

	@SuppressWarnings("rawtypes")
	public void hashmapMethod(HashMap<String, Object> map) {

		// TODO Auto-generated method stub
		for (Map.Entry m : map.entrySet()) {
			if (m.getKey().equals("password")) {
				ob = m.getValue();
				this.password = ob.toString();
              
			}

		}

	}

}
