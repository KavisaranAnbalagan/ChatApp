package com.zoho.model;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.zoho.apis.*;

@SuppressWarnings("serial")
@WebFilter("/loginfilter")
public class logoutfilter extends HttpFilter implements Filter {

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
	
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		HttpSession session = req.getSession();
		if (session.getAttribute("DataField")=="InvalidSession") {
			System.out.println("page Redirect in logoutfilter page");
			((HttpServletResponse) response).sendRedirect("Login.jsp");
			return;
		}
		Cookie[] cookies = ((HttpServletRequest) request).getCookies();

		String sessionid;
		if (cookies != null) {
			SessionContainerdao sessionContainerdao = new SessionContainerdao();
			for (int i = 0; i < cookies.length; i++) {
				
				if (cookies[i].getName().equals("sessionid")) {
					
					sessionid = cookies[i].getValue();
					LRUcache.clear(sessionid);
				
					sessionContainerdao.deleteSessionRecords(sessionid);
					cookies[i].setMaxAge(0);
					session.removeAttribute("DataField");
					session.setAttribute("DataField", "InvalidSession");
				   // chain.doFilter(req, res);
					
				    System.out.println("tologin");
					res.sendRedirect("Login.jsp");
				}
			}

		}
	}

	

}
