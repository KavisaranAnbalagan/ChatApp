package com.zoho.DB;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.zoho.apis.AuditHistorydao;
import com.zoho.model.ObjectFactory;
import com.zoho.redisDao.RedisDeletion;

public class CRUDImplementation {

	public static DBCPImpl instanceOfDBCP;

	public ArrayList<ObjectFactory> select(String classname, ArrayList<String> al,
			HashMap<HashMap<String, String>, String> where) throws Exception {
		StringBuilder sb1 = new StringBuilder("");
		for (int i = 0; i < al.size(); i++) {
			if (i > 0) {
				sb1.append(",");
			}
			sb1.append(al.get(i));
		}

		StringBuilder forWhere = buildWhereQuery(where);

		Connection con = DBCPImpl.getInstance().getconnection();
		Statement st = con.createStatement();
		String query = "select " + sb1 + " from " + classname + " " + forWhere;
		System.out.println("selectquery=" + query);
		ResultSet rs = st.executeQuery(query);
		HashMap<String, Object> hm = new HashMap<>();
		ResultSetMetaData resultSetMetaData = rs.getMetaData();
		int columncount = rs.getMetaData().getColumnCount();

		ArrayList<ObjectFactory> list = new ArrayList<>();
		classname = "com.zoho.model." + classname;

		while (rs.next()) {
			Object ob1 = null;

			for (int i = 1; i <= columncount; i++) {
				String str = resultSetMetaData.getColumnLabel(i);
				ob1 = rs.getObject(i);
				hm.put(str, ob1);
			}
			ObjectFactory ob = getInstanceOfHashMap(hm, classname);
			list.add(ob);
		}
		con.close();
		if (list.size() == 0) {
			return null;
		}
		return list;
	}

	public ObjectFactory selectOne(String classname, ArrayList<String> columnname,
			HashMap<HashMap<String, String>, String> where) throws Exception {
		StringBuilder sb1 = new StringBuilder("");
		for (int i = 0; i < columnname.size(); i++) {
			if (i > 0) {
				sb1.append(",");
			}
			sb1.append(columnname.get(i));
		}
		System.out.println(sb1);
		StringBuilder forWhere = buildWhereQuery(where);
		Connection con = DBCPImpl.getInstance().getconnection();
		Statement st = con.createStatement();
		String query = "select " + sb1 + " from " + classname + " " + forWhere;
		System.out.println("select=" + query);
		ResultSet rs = st.executeQuery(query);
		HashMap<String, Object> hm = new HashMap<>();

		ResultSetMetaData resultSetMetaData = rs.getMetaData();
		int columncount = rs.getMetaData().getColumnCount();
		if (rs.next()) {
			Object ob1 = null;
			for (int i = 1; i <= columncount; i++) {
				String str = resultSetMetaData.getColumnLabel(i);
				ob1 = rs.getObject(i);
				hm.put(str, ob1);
			}
		} else {
			return null;
		}

		classname = "com.zoho.model." + classname;
		con.close();
		ObjectFactory ob = getInstanceOfHashMap(hm, classname);
		return ob;
	}

	public void insert(ObjectFactory ob) throws SQLException {
		HashMap<String, HashMap<String, Object>> hmp = ob.getHmp1();
		String tableName = null;
		for (Map.Entry<String, HashMap<String, Object>> set : hmp.entrySet()) {

			HashMap<String, Object> hm = new HashMap<>();
			hm = set.getValue();
			tableName = set.getKey();
			String query = generateQuery(tableName, hm);

			Connection con = DBCPImpl.getInstance().getconnection();
			Statement st = con.createStatement();

			st.executeUpdate(query);

			con.close();
		}
		if (tableName != "AuditHistory") {
			AuditHistorydao auditHistorydao = new AuditHistorydao();
			auditHistorydao.insertInToAudit(ob, null, "insert");
			RedisDeletion redisDeletion = new RedisDeletion();
			redisDeletion.deleteInRedis(null, ob, tableName, null, "insert");
		}
	}

	public String generateQuery(String tablename, HashMap<String, Object> hm) {

		StringBuilder sb = new StringBuilder("");// to get column
		StringBuilder sb1 = new StringBuilder("");// to get values
		int itr = 0;
		int len = hm.size();
		// to append column names and their values
		for (Map.Entry<String, Object> entry : hm.entrySet()) {
			String str1 = entry.getKey();
			String str2 = null;
			if (entry.getValue() != null) {
				str2 = entry.getValue().toString();
			}
			if (itr == 0 && itr == len - 1) {
				str1 = "(" + str1 + ")";
				str2 = "('" + str2 + "')";
			}
			if (itr == 0 && itr != len - 1) {
				str1 = "(" + str1;
				str2 = "('" + str2 + "'";
			}
			if (itr != 0 && itr != len - 1) {
				str1 = "," + str1;
				str2 = ",'" + str2 + "'";
			}
			if (itr != 0 && itr == len - 1) {
				str1 = "," + str1 + ")";
				str2 = ",'" + str2 + "');";
			}
			sb.append(str1);
			sb1.append(str2);
			itr++;
		}

		String query = "insert into " + tablename + " " + sb + " values " + sb1;
		return query;
	}

	public void insertBatch(ArrayList<ObjectFactory> ob) throws SQLException {

		Connection con = DBCPImpl.getInstance().getconnection();
		con.setAutoCommit(false);
		Statement st = con.createStatement();
		int size = ob.size();
		String tableName = null;
		try {
			for (int i = 0; i < size; i++) {
				ObjectFactory of = ob.get(i);
				HashMap<String, HashMap<String, Object>> hmp = of.getHmp1();

				for (Map.Entry<String, HashMap<String, Object>> set : hmp.entrySet()) {

					HashMap<String, Object> hm = new HashMap<>();
					hm = set.getValue();
					tableName = set.getKey();
					String query = generateQuery(tableName, hm);
					st.executeUpdate(query);

				}
				if (tableName != "AuditHistory") {
					AuditHistorydao auditHistorydao = new AuditHistorydao();
					auditHistorydao.insertInToAudit(of, null, "insert");
					RedisDeletion redisDeletion = new RedisDeletion();
					redisDeletion.deleteInRedis(null, of, tableName, null, "insert");
				}
			}
			con.commit();
		} catch (Exception e) {
			e.printStackTrace();
			con.rollback();
		}
		con.close();

	}

	public void update(HashMap<String, String> colAndval, String tablename,
			HashMap<HashMap<String, String>, String> where) throws Exception {
		CRUDImplementation db = new CRUDImplementation();
		int len = colAndval.size();
		StringBuilder sb = new StringBuilder("");
		// to append column names and their values
		int itr = 0;
		ArrayList<String> column = new ArrayList<>(colAndval.keySet());
		ArrayList<String> allColumns = new ArrayList<>();
		allColumns.add("*");
		StringBuilder forWhere = buildWhereQuery(where);

		ObjectFactory oldValueForDeletion = db.selectOne(tablename, allColumns, where);
		for (Map.Entry<String, String> entry : colAndval.entrySet()) {

			itr++;
			String str1 = entry.getKey();
			str1 = str1 + "=";
			String str2 = entry.getValue();
			str1 = str1 + str2;
			if (itr != len) {
				str1 = str1 + ",";
			}
			sb.append(str1);
		}
		ObjectFactory oldValue = db.selectOne(tablename, column, where);
		

		System.out.println("forWhere=" + forWhere);
		Connection con = DBCPImpl.getInstance().getconnection();
		Statement st = con.createStatement();
		String query = "update " + tablename + " set " + sb + " " + forWhere;
		System.out.println("update query=" + query);
		st.executeUpdate(query);
		ObjectFactory newValue = db.selectOne(tablename, column, where);

		AuditHistorydao auditHistorydao = new AuditHistorydao();

		auditHistorydao.insertInToAudit(newValue, oldValue, "update");

		ObjectFactory currentValue = db.selectOne(tablename, allColumns, where);
		con.close();
		if (tablename != "AuditHistory" && tablename != "GetId") {
			RedisDeletion redisDeletion = new RedisDeletion();
			redisDeletion.deleteInRedis(oldValueForDeletion, currentValue, tablename, column, "update");
		}

	}

	public void deleteRecords(String tablename, HashMap<HashMap<String, String>, String> where) throws Exception {
		CRUDImplementation db = new CRUDImplementation();
		ArrayList<String> column = new ArrayList<>();
		column.add("*");
		ObjectFactory ob = db.selectOne(tablename, column, where);

		StringBuilder forWhere = buildWhereQuery(where);

		Connection con = DBCPImpl.getInstance().getconnection();
		Statement st = con.createStatement();
		String query = "Delete from " + tablename + " " + forWhere;
		st.executeUpdate(query);

		AuditHistorydao auditHistorydao = new AuditHistorydao();
		auditHistorydao.insertInToAudit(null, ob, "delete");
		con.close();
		if (tablename != "AuditHistory") {
			RedisDeletion redisDeletion = new RedisDeletion();
			redisDeletion.deleteInRedis(null, ob, tablename, null, "delete");
		}
	}

	public StringBuilder generateWhere(HashMap<String, String> temp, String oper, StringBuilder forWhere) {
		String col = null;
		String val = null;
		String operator = null;
		for (Entry<String, String> entry1 : temp.entrySet()) {
			if (entry1.getKey() == "condition") {
				operator = entry1.getValue();
			}
			col = entry1.getKey();
			val = entry1.getValue();
		}

		forWhere.append(col + operator + "'" + val + "' " + oper + " ");
		return forWhere;

	}

	public StringBuilder buildWhereQuery(HashMap<HashMap<String, String>, String> where) {
		StringBuilder forWhere = new StringBuilder();
		if (where != null) {
			String oper = null;
			HashMap<String, String> temp = new HashMap<>();

			for (Entry<HashMap<String, String>, String> entry1 : where.entrySet()) {
				if (entry1.getValue() != null) {
					temp = entry1.getKey();
					oper = entry1.getValue();
					forWhere = generateWhere(temp, oper, forWhere);
				} else {
					temp = entry1.getKey();
					oper = entry1.getValue();
					forWhere = generateWhere(temp, "", forWhere);
				}
			}
			forWhere.insert(0, "where ");

		}
		return forWhere;
	}

	public ObjectFactory getInstanceOfHashMap(HashMap<String, Object> hm, String classname)
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException, ClassNotFoundException {
		// call the printIt method
		Class<?> clazz = Class.forName(classname);
		Constructor<?> ctor = clazz.getConstructor(HashMap.class);
		ObjectFactory object = (ObjectFactory) ctor.newInstance(hm);
		return object;
	}

}
