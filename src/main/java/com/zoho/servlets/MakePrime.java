package com.zoho.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.zoho.interfaces.EmailInterfaceForCUD;
import com.zoho.redisDao.DeleteMailDetailsFromRedis;
import com.zoho.utility.ProxyObject;

@SuppressWarnings("serial")
public class MakePrime extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String mailId = request.getParameter("Buttonvalue");
		HttpSession session = ((HttpServletRequest) request).getSession(true);
		int userid = (int) session.getAttribute("userid");
		EmailInterfaceForCUD emailCUDProxy = ProxyObject.getEmailProxy();
		System.out.println("makepriME"+userid);
		emailCUDProxy.makeAsPrimary(mailId,userid);
		DeleteMailDetailsFromRedis instance = new DeleteMailDetailsFromRedis();
		instance.deletePrimaryInRedis(userid);
		response.sendRedirect("home.jsp");
	}
}