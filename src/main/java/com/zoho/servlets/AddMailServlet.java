package com.zoho.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.zoho.interfaces.EmailInterfaceForCUD;
import com.zoho.model.*;
import com.zoho.utility.ProxyObject;

@SuppressWarnings("serial")
public class AddMailServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String mailId = request.getParameter("mailId");
		HttpSession session = request.getSession();
		int userid = (int) session.getAttribute("userid");
		Email email = null;
		try {

			EmailInterfaceForCUD emailProxy =  ProxyObject.getEmailProxy();
			email = (Email) emailProxy.getMail(mailId);

		} catch (Exception e) {

			e.printStackTrace();
		}

		if (email == null) {
			
			EmailInterfaceForCUD emailCUDProxy = ProxyObject.getEmailProxy();
			emailCUDProxy.addNewMail(userid, mailId);
		}
		response.sendRedirect("home.jsp");
	}
}