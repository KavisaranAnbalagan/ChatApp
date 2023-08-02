package com.zoho.apis;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.zoho.DB.CRUDImplementation;
import com.zoho.model.*;

import org.json.JSONObject;

public class AuditHistorydao {

	public  void insertInToAudit(ObjectFactory newValue, ObjectFactory oldValue, String action) {

		if (action == "insert") {
			HashMap<String, HashMap<String, Object>> insertedDetails = newValue.getHmp1();
			insertInToAudit(insertedDetails, action, null);
		} else if (action == "delete") {
			HashMap<String, HashMap<String, Object>> deletedDetails = oldValue.getHmp1();
			insertInToAudit(null, action, deletedDetails);
		} else {
			HashMap<String, HashMap<String, Object>> update=null;
			if(oldValue!=null) {
				 update = oldValue.getHmp1();
			}
			
			HashMap<String, HashMap<String, Object>> newValue1=null;
			if(newValue!=null) {
				newValue1=newValue.getHmp1();
				}
			insertInToAudit(newValue1, "update", update);
			
		}

	}

	public void insertInToAudit(HashMap<String, HashMap<String, Object>> newValue, String action,
			HashMap<String, HashMap<String, Object>> oldValue) {
		GetIddao getId = GetIddao.generateCommonObject();
		TimeConversion getTime = new TimeConversion();
		CRUDImplementation db = new CRUDImplementation();
		String oldvalue = null;
		String newvalue = null;
		String tableName = null;
		HashMap<String, Object> values = new HashMap<>();
		if (oldValue != null) {
			for (Map.Entry<String, HashMap<String, Object>> set : oldValue.entrySet()) {
				tableName = set.getKey();
				values = set.getValue();
				JSONObject jsonObject = new JSONObject(values);
				oldvalue = jsonObject.toString();
			}
		}
		if (newValue != null) {
			values.clear();
			for (Map.Entry<String, HashMap<String, Object>> set : newValue.entrySet()) {
				tableName = set.getKey();
				values = set.getValue();
				JSONObject jsonObject = new JSONObject(values);
				newvalue = jsonObject.toString();
			}
		}
		values.clear();
		long millis = getTime.getCurrentTime();
		values.put("AuditId", getId.getValueAuditid());
		values.put("action", action);
		values.put("tableName", tableName);
		values.put("oldValue", oldvalue);
		values.put("newValue", newvalue);
		values.put("modifiedTime", Long.toString(millis));
		AuditHistory audit = new AuditHistory(values);
		try {
			db.insert(audit);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
