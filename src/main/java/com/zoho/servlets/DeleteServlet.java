package com.zoho.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.zoho.interfaces.EmailInterfaceForCUD;
import com.zoho.utility.ProxyObject;

@SuppressWarnings("serial")
public class DeleteServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String mailId = request.getParameter("Buttonvalue");
		EmailInterfaceForCUD emailProxy = ProxyObject.getEmailProxy();
		emailProxy.deleteMail(mailId);
		response.sendRedirect("home.jsp");
	}
}