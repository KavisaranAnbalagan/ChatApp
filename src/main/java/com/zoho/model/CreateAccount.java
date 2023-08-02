package com.zoho.model;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletContextListener;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zoho.DB.CRUDImplementation;
import com.zoho.apis.*;
import com.zoho.interfaces.EmailInterface;
import com.zoho.utility.*;

@SuppressWarnings("serial")
@WebServlet("/account")
public class CreateAccount extends HttpServlet implements ServletContextListener {

	public void service(HttpServletRequest req, HttpServletResponse res) throws IOException {

		String fname = req.getParameter("fname");
		String lname = req.getParameter("lname");
		String mailid = req.getParameter("mailid");
		String phoneNo = req.getParameter("ph_no");
		long phone = Long.parseLong(phoneNo);
		String gender = req.getParameter("gender");
		String DOB = req.getParameter("dob");
		LocalDate dob = null;

		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			dob = LocalDate.parse(DOB, formatter);
		} catch (Exception e) {
			e.printStackTrace();
		}

		String password = req.getParameter("password");
		

		// Check if the email already exists
		int check = 0;
		try {
			EmailInterface emailProxy = (EmailInterface) ProxyObject.getEmailProxy();
			Email email = (Email) emailProxy.getMail(mailid);
			check = email.getUserid();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Insert signup details if the email doesn't exist
		if (check == 0) {
			ArrayList<ObjectFactory> objectFactoryArrayList = new ArrayList<>();
			
			TimeConversion tm = new TimeConversion();
			GetIddao getid = GetIddao.generateCommonObject();
			int usrid = 0;
			try {
				usrid = getid.FindId("usrid");
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
					| NoSuchMethodException | SecurityException | SQLException e1) {
				e1.printStackTrace();
			}

			long millis = tm.getCurrentTime();
			HashMap<String, Object> userHashMap = new HashMap<>();
			userHashMap.put("usrid", usrid);
			userHashMap.put("fname", fname);
			userHashMap.put("lname", lname);
			userHashMap.put("dob", dob);
			userHashMap.put("gender", gender);
			userHashMap.put("createtime", millis);
			userHashMap.put("updatetime", millis);
			UserDetails ud = new UserDetails(userHashMap);
			objectFactoryArrayList.add(ud);

			HashMap<String, Object> mailHashMap = new HashMap<>();
			mailHashMap.put("usrid", usrid);
			mailHashMap.put("mailid", mailid);
			mailHashMap.put("isprime", 1);
			mailHashMap.put("verified", 1);
			mailHashMap.put("m_time", millis);
			Email mail = new Email(mailHashMap);
			objectFactoryArrayList.add(mail);

			HashMap<String, Object> phnoHashMap = new HashMap<>();
			phnoHashMap.put("usrid", usrid);
			phnoHashMap.put("phonenumber", phone);
			phnoHashMap.put("isprime", 1);
			phnoHashMap.put("verified", 1);
			phnoHashMap.put("m_time", millis);
			PhoneNumber phno = new PhoneNumber(phnoHashMap);
			objectFactoryArrayList.add(phno);

			HashMap<String, Object> passwordHashMap = new HashMap<>();
			passwordHashMap.put("usr_id", usrid);
			passwordHashMap.put("password", PasswordHashing.PasswordEncryption(password));
			passwordHashMap.put("createtime", millis);
			passwordHashMap.put("modify_time", millis);
			UserPassword up = new UserPassword(passwordHashMap);
			objectFactoryArrayList.add(up);

			CRUDImplementation dbc = new CRUDImplementation();
			try {
				dbc.insertBatch(objectFactoryArrayList);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
