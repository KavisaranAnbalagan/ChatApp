package com.zoho.model;

import java.io.IOException;
import java.util.UUID;
import java.util.regex.Pattern;


import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



import com.zoho.apis.*;
import com.zoho.interfaces.EmailInterface;
import com.zoho.utility.ProxyObject;


@SuppressWarnings("serial")
public class LoginServlet extends HttpServlet {
	private static final String EMAIL_PATTERN = "^\\w+([.-]?\\w+)*@\\w+([.-]?\\w+)*(\\.\\w{2,3})+$";

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		UserPassworddao passwordDao=new UserPassworddao();

		System.out.println("login servlet");

		String loginId = request.getParameter("userid");
		String password = request.getParameter("password");
		boolean isValid = validateEmail(loginId);
		PhoneNumberdao phno = new PhoneNumberdao();
		SessionContainerdao sessioncontainerdao = new SessionContainerdao();
		// check mail exists or not
		int userid = 0;
		try {
			if (isValid) {
				EmailInterface emailProxy = (EmailInterface) ProxyObject.getEmailProxy();
				Email email = (Email) emailProxy.getMail(loginId);
				userid = email.getUserid();
			} else {
				userid = phno.checkPhoneNumber(loginId);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		boolean check = false;
		if (userid != 0) {
			check = passwordDao.validatePassword(password, userid);
		}
		if (check == true) {
			String sessionid = generateSessionId();
			Cookie c = new Cookie("sessionid", sessionid);
			
			sessioncontainerdao.insertSessionData(userid, sessionid);// insert session id in db
			response.addCookie(c);	
			response.sendRedirect("home.jsp");
		} else {
			request.getRequestDispatcher("Login.jsp").forward(request, response);
		}
	}

	public synchronized String generateSessionId() {
		return String.valueOf(System.currentTimeMillis()).substring(8, 13)
				+ UUID.randomUUID().toString().substring(1, 10);
	}

	public static boolean validateEmail(String email) {
		Pattern pattern = Pattern.compile(EMAIL_PATTERN);
		return pattern.matcher(email).matches();
	}

}
