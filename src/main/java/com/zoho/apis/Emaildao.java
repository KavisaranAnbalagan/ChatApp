package com.zoho.apis;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import com.zoho.DB.CRUDImplementation;
import com.zoho.interfaces.EmailInterfaceForCUD;
import com.zoho.model.Email;
import com.zoho.model.ObjectFactory;
import com.zoho.utility.ProxyObject;

public class Emaildao implements EmailInterfaceForCUD {

	public Email getMail(String mailId) {
		Email mail = null;
		CRUDImplementation db = new CRUDImplementation();
		String className = "Email";
		ArrayList<String> columnNames = new ArrayList<>();
		columnNames.add("usrid");
		columnNames.add("isprime");
		columnNames.add("verified");
		HashMap<HashMap<String, String>, String> where = new HashMap<>();
		HashMap<String, String> hm = new HashMap<>();
		hm.put("mailid", mailId);
		hm.put("condition", "=");
		where.put(hm, null);

		try {
			mail = (Email) db.selectOne(className, columnNames, where);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return mail;
	}

	public ArrayList<String> getAllMail(int userId) {
		CRUDImplementation db = new CRUDImplementation();
		ArrayList<String> allMails = new ArrayList<>();
	
		String className = "Email";
		ArrayList<String> columnNames = new ArrayList<>();
		ArrayList<ObjectFactory> OBArrayLists = null;
		columnNames.add("mailId");
		HashMap<HashMap<String, String>, String> where = new HashMap<>();
		HashMap<String, String> hm = new HashMap<>();
		hm.put("usrid", Integer.toString(userId));
		hm.put("condition", "=");
		where.put(hm, null);

		try {
			OBArrayLists = db.select(className, columnNames, where);
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		Email e = null;
		int len = OBArrayLists.size();
		System.out.println("obsize:" + OBArrayLists.size());
		for (int i = 0; i < len; i++) {
			e = (Email) OBArrayLists.get(i);
			allMails.add(e.getMailid());
		}

		if (OBArrayLists.size() == 0) {
			return null;
		}

		return allMails;
	}
	public void makeAsVerified(String mailId, int userId) {
		CRUDImplementation db = new CRUDImplementation();
		String className = "Email";
		HashMap<String, String> colAndVal = new HashMap<>();
		colAndVal.put("verified", "1");
		HashMap<HashMap<String, String>, String> where = new HashMap<>();
		HashMap<String, String> compareCol = new HashMap<>();
		compareCol.put("usrid", Integer.toString(userId));
		compareCol.put("condition", "=");
		where.put(compareCol, "and");
		HashMap<String, String> compareCol2= new HashMap<>();
		compareCol2.put("mailId", mailId);
		compareCol2.put("condition", "=");
		where.put(compareCol2, null);
		try {
			db.update(colAndVal, className, where);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void makeAsPrimary(String mailId, int userId) {
		EmailInterfaceForCUD emailProxy = ProxyObject.getEmailProxy();
		Email email=emailProxy.getMail(mailId);
		if(email.getIsverified()==0) {
			return;
		}
		CRUDImplementation db = new CRUDImplementation();
		String className = "Email";
		HashMap<String, String> colAndVal = new HashMap<>();
		colAndVal.put("isprime", "0");
		HashMap<HashMap<String, String>, String> where = new HashMap<>();
		HashMap<String, String> compareCol = new HashMap<>();
		compareCol.put("usrid", Integer.toString(userId));
		compareCol.put("condition", "=");
		where.put(compareCol, "and");
		HashMap<String, String> compareCol2= new HashMap<>();
		compareCol2.put("isprime", "1");
		compareCol2.put("condition", "=");
		where.put(compareCol2, null);
		try {
			db.update(colAndVal, className, where);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		colAndVal.clear();
		colAndVal.put("isprime", "1");
		where.clear();
		compareCol2.clear();
		compareCol2.put("mailid",mailId);
		compareCol2.put("condition", "=");
		where.put(compareCol2, null);
		try {
			db.update(colAndVal, className, where);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("update successfully done");
	}

	public  Object deleteMail(String mailId) {
		CRUDImplementation db = new CRUDImplementation();
		String className = "Email";
		HashMap<HashMap<String, String>, String> where = new HashMap<>();
		HashMap<String, String> compareCol = new HashMap<>();
		compareCol.put("mailid", mailId);
		compareCol.put("condition", "=");
		where.put(compareCol, null);

		try {
			db.deleteRecords(className, where);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void addNewMail(int userId, String mailId) {
		CRUDImplementation db = new CRUDImplementation();
		TimeConversion tm = new TimeConversion();
		long millis = tm.getCurrentTime();
		HashMap<String, Object> mailHashMap = new HashMap<>();
		mailHashMap.put("usrid", userId);
		mailHashMap.put("mailid", mailId);
		mailHashMap.put("isprime", 0);
		mailHashMap.put("verified", 1);
		mailHashMap.put("m_time", millis);
		Email email=new Email(mailHashMap);
		ObjectFactory ob = email;

		try {
			db.insert(ob);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public String getPrimary(int userId) {
		CRUDImplementation db = new CRUDImplementation();
		String tableName = "Email";
	
		ArrayList<String> columnNames = new ArrayList<>();
		columnNames.add("mailId");
		columnNames.add("isprime");
		HashMap<HashMap<String, String>, String> where = new HashMap<>();
		HashMap<String, String> hm = new HashMap<>();
		hm.put("usrid", Integer.toString(userId));
		hm.put("condition", "=");
		where.put(hm, null);

		ArrayList<ObjectFactory> obArrayLists = new ArrayList<>();
		System.out.println(obArrayLists.size());
		try {
			obArrayLists = db.select(tableName, columnNames, where);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		System.out.println(obArrayLists.size());
		for (int i = 0; i < obArrayLists.size(); i++) {
			Email email = (Email) obArrayLists.get(i);
			if (email.getIsprime() == 1) {
				String mailId = email.getMailid();
				return mailId;
			}
		}
		return null;
	}
	public ArrayList<String> getVerifiedOrUnVerified(int userId, int status){
		CRUDImplementation db = new CRUDImplementation();
		ArrayList<String> mails = new ArrayList<>();
	
		String className = "Email";
		ArrayList<String> columnNames = new ArrayList<>();
		ArrayList<ObjectFactory> OBArrayLists = null;
		columnNames.add("mailId");
		columnNames.add("verified");
		HashMap<HashMap<String, String>, String> where = new HashMap<>();
		HashMap<String, String> hm = new HashMap<>();
		hm.put("usrid", Integer.toString(userId));
		hm.put("condition", "=");
		where.put(hm, "and");
		HashMap<String, String> compareCol2= new HashMap<>();
		compareCol2.put("verified", Integer.toString(status));
		compareCol2.put("condition", "=");
		where.put(compareCol2, null);
		try {
			OBArrayLists = db.select(className, columnNames, where);
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		Email e = null;
		int len = OBArrayLists.size();
		System.out.println("obsize:" + OBArrayLists.size());
		for (int i = 0; i < len; i++) {
			e = (Email) OBArrayLists.get(i);
			mails.add(e.getMailid());
		}

		if (OBArrayLists.size() == 0) {
			return null;
		}

		return mails;
	}


	

}
