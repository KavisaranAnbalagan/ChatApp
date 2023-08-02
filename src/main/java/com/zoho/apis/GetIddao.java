package com.zoho.apis;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

import com.zoho.DB.CRUDImplementation;
import com.zoho.model.GetId;
import com.zoho.model.ObjectFactory;

public class GetIddao {

	private AtomicInteger usridcounter = new AtomicInteger();
	private AtomicInteger grpidcounter = new AtomicInteger();
	private AtomicInteger auditIdCounter = new AtomicInteger();
	private static GetIddao gd;

	public int FindId(String idname) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException, SQLException {

		int num = 0;

		if (idname == "usrid") {
			num = getValueUsrid();
		} else if (idname == "grpid") {
			num = getValueGrpid();
		} else if (idname == "auditId") {
			num = getValueAuditid();
		}
		return num;
	}

	public void setId() throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		selectId();

	}

	
	public static GetIddao generateCommonObject() {

		if (gd == null) {
			synchronized (GetIddao.class) {
				if (gd == null)
					gd = new GetIddao();
			}
		}
		return gd;
	}

	public void selectId() throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		GetId gi = null;
		CRUDImplementation dbc = new CRUDImplementation();
		String tablename = "GetId";
		ArrayList<String> columnname = new ArrayList<>();
		columnname.add("idname");
		columnname.add("id");
		HashMap<HashMap<String, String>, String> where = null;
		ArrayList<ObjectFactory> gd = new ArrayList<>();
		try {
			gd = dbc.select(tablename, columnname, where);
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		for (int i = 0; i < gd.size(); i++) {
			gi = (GetId) gd.get(i);
			switch (gi.getIdname()) {
			case "usrid":
				int IdNumber = gi.getId();
				AtomicInteger num = new AtomicInteger(IdNumber);
				this.usridcounter = num;
				break;
			case "grpid":
				IdNumber = gi.getId();
				num = new AtomicInteger(IdNumber);
				this.grpidcounter = num;
				break;
			case "auditId":
				IdNumber = gi.getId();
				num = new AtomicInteger(IdNumber);
				this.auditIdCounter = num;
				break;
			}
			gi = null;
		}

	}

	public void updateId() throws SQLException {

		updateIdDetails("usrid", String.valueOf(this.usridcounter));
		updateIdDetails("grpid", String.valueOf(this.grpidcounter));
		updateIdDetails("auditId", String.valueOf(this.auditIdCounter));
	}

	public void updateIdDetails(String idname, String value) throws SQLException {
		CRUDImplementation dbc = new CRUDImplementation();
		HashMap<String, String> colAndVal = new HashMap<>();

		colAndVal.put("id", value);
		HashMap<HashMap<String, String>, String> where = new HashMap<>();
		HashMap<String, String> condition = new HashMap<>();
		condition.put("idname", idname);
		condition.put("condition", "=");
		where.put(condition, null);
		try {
			dbc.update(colAndVal, "GetId", where);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int getValueUsrid() {
		return usridcounter.incrementAndGet();

	}

	public int getValueGrpid() {
		return grpidcounter.incrementAndGet();

	}

	public int getValueAuditid() {
		// TODO Auto-generated method stub
		return auditIdCounter.incrementAndGet();
	}
}
