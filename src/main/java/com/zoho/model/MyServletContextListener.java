package com.zoho.model;

import java.sql.SQLException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.zoho.apis.GetIddao;

public class MyServletContextListener implements ServletContextListener {
	public void contextInitialized(ServletContextEvent arg0) {
		
		try {
			GetIddao gd = GetIddao.generateCommonObject();
			gd.setId();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		GetIddao gtd = GetIddao.generateCommonObject();
		try {
			gtd.updateId();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


}
