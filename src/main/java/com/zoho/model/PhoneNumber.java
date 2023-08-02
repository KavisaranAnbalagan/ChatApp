package com.zoho.model;

import java.util.HashMap;
import java.util.Map;

import com.zoho.DB.CRUDImplementation;


@SuppressWarnings("serial")
public class PhoneNumber extends ObjectFactory {
	private int userid;
	private long phNo;
	private int isprime;
	private int verified;
	CRUDImplementation dbc = new CRUDImplementation();
	HashMap<String, Object> hm = new HashMap<>();
	Object ob;

	public PhoneNumber(HashMap<String, Object> hm) {
		super("PhoneNumber", hm);
		hashmapMethod(hm);
	}

	public PhoneNumber() {
		// TODO Auto-generated constructor stub
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public long getPhNo() {
		return phNo;
	}

	public void setPhNo(long phNo) {
		this.phNo = phNo;
	}

	public int getIsprime() {
		return isprime;
	}

	public void setIsprime(int isprime) {
		this.isprime = isprime;
	}

	public int getVerified() {
		return verified;
	}

	public void setVerified(int verified) {
		this.verified = verified;
	}

	@SuppressWarnings("rawtypes")
	public void hashmapMethod(HashMap<String, Object> map) {

		// TODO Auto-generated method stub
		for (Map.Entry m : map.entrySet()) {

			if (m.getKey().equals("usrid")) {
				ob = m.getValue();
				Integer num = (Integer) ob;
				System.out.println("number=" + num);
				this.userid = num.intValue();
			}
			if (m.getKey().equals("phonenumber")) {
				ob = m.getValue();
				String s = ob.toString();
				this.phNo = Long.parseLong(s);
				System.out.println(s);
			}
			if (m.getKey().equals("isprime")) {
				ob = m.getValue();
				Integer num = (Integer) ob;
				this.isprime = num.intValue();
			}
			if (m.getKey().equals("verified")) {
				ob = m.getValue();
				Integer num = (Integer) ob;
				this.verified = num.intValue();
			}

		}

	}
}
