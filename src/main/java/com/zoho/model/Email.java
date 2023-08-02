package com.zoho.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Email extends ObjectFactory implements Serializable {
	private static final long serialVersionUID = 1L;
	private int userid;
	private String mailid;
	private int isprime;
	private int verified;

	public Email(HashMap<String, Object> hm) {
		super("Email", hm);
		hashmapMethod(hm);
	}

	public String getMailid() {
		return mailid;
	}

	public void setMailid(String mailid) {
		this.mailid = mailid;
	}

	public Email(String mailid, int isprime, int verified) {
		super();
		this.mailid = mailid;
		this.isprime = isprime;
		this.verified = verified;
	}

	public Email() {
		// TODO Auto-generated constructor stub
	}

	public int getIsprime() {
		return isprime;
	}

	public void setIsprime(int isprime) {
		this.isprime = isprime;
	}

	public int getIsverified() {
		return verified;
	}

	public void setIsverified(int isverified) {
		this.verified = isverified;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	Object ob;

	public void hashmapMethod(HashMap<String, Object> map) {

		// TODO Auto-generated method stub
		for (@SuppressWarnings("rawtypes")
		Map.Entry m : map.entrySet()) {

			if (m.getKey().equals("usrid")) {
				ob = m.getValue();
				Integer num = (Integer) ob;
				System.out.println("number=" + num);
				this.userid = num.intValue();
			}
			if (m.getKey().equals("mailId")) {
				ob = m.getValue();
				String s = ob.toString();
				this.mailid = s;
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
