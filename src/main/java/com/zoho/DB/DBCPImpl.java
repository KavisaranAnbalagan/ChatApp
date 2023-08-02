package com.zoho.DB;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;

public class DBCPImpl {

	private static DBCPImpl dbcpi;
	private static BasicDataSource bds;

	private DBCPImpl() {
		if (dbcpi != null) {
			throw new RuntimeException("Please use getInstance method");
		}
	}

	public static DBCPImpl getInstance() throws SQLException {
		if (dbcpi == null) {
			synchronized (DBCPImpl.class) {
				if (dbcpi == null) {
					dbcpi = new DBCPImpl();
				}
			}
		}
		return dbcpi;
	}

	public Connection getconnection() throws SQLException {

		if (bds == null) {
			synchronized (this) {
				if (bds == null) {
					bds = new BasicDataSource();
					try {
							bds.setDriverClassName("com.mysql.cj.jdbc.Driver");
							bds.setUrl("jdbc:mysql://localhost:3306/chatbox");
							bds.setUsername("root");
							bds.setPassword("");
							bds.setMinIdle(5);
							bds.setMaxIdle(10);

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		return bds.getConnection();
	}

	
}