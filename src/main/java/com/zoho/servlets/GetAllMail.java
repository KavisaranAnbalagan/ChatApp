package com.zoho.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.zoho.interfaces.EmailInterfaceForCUD;
import com.zoho.utility.ProxyObject;

@SuppressWarnings("serial")
public class GetAllMail extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		int userid = (int) session.getAttribute("userid");
		EmailInterfaceForCUD emailProxy = ProxyObject.getEmailProxy();
		ArrayList<String> allMails = emailProxy.getAllMail(userid);
	
		String mailId = emailProxy.getPrimary(userid);
	

		StringBuilder emailBuilder = new StringBuilder();
		for (int i = 0; i < allMails.size(); i++) {

			String mail = allMails.get(i);
			if (mail.equals(mailId)) {
				emailBuilder.append(allMails.get(i))
						.append("<form action=\"Delete\" method=\"post\" style=\"display: inline;\">")
						.append("<button type=\"submit\" name=\"Buttonvalue\" value=\"").append(allMails.get(i))
						.append("\">Delete</button></form><br>");
			} else {
				emailBuilder.append(allMails.get(i))
						.append("<form action=\"Delete\" method=\"post\" style=\"display: inline;\">")
						.append("<button type=\"submit\" name=\"Buttonvalue\" value=\"").append(allMails.get(i))
						.append("\">Delete</button></form>")
						.append("<form action=\"toprime\" method=\"post\" style=\"display: inline;\">")
						.append("<button type=\"submit\" name=\"Buttonvalue\" value=\"").append(allMails.get(i))
						.append("\">Make as primary</button></form><br>");
			}

		}


		String emailHtml = emailBuilder.toString();
		// Set the response content type
		response.setContentType("text/html");

		// Write the emails as the response
		PrintWriter out = response.getWriter();
		out.println(emailHtml);
	}

}
