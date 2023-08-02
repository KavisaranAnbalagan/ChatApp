package com.zoho.model;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("serial")
public class GetId extends ObjectFactory {
	private String idname;
	private int id;
	private int auditId;

	public GetId(HashMap<String, Object> hm) {

		hashmapMethod(hm);
	}

	public GetId() {
		// TODO Auto-generated constructor stub
	}

	Object ob;

	@SuppressWarnings("rawtypes")
	private void hashmapMethod(HashMap<String, Object> map) {

		for (Map.Entry m : map.entrySet()) {

			if (m.getKey().equals("idname")) {
				this.idname = (String) m.getValue();
			}
			if (m.getKey().equals("id")) {
				this.id = (int) m.getValue();
				System.out.println(this.id);
			}
			if (m.getKey().equals("auditId")) {
				this.auditId = (int) m.getValue();
			}
		}
	}

	public int getAuditId() {
		return auditId;
	}

	public String getIdname() {
		return idname;
	}

	public int getId() {
		return id;
	}

}
