package com.zoho.model;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.zoho.apis.CacheValidation;
import com.zoho.interfaces.SessionInterface;
import com.zoho.interfaces.UserInterface;

import com.zoho.utility.ProxyObject;

@SuppressWarnings("serial")
@WebFilter("/login")
public class CheckFilter extends HttpFilter implements Filter {
	
	public static ThreadLocal<UserDetails> threadLocalVariable = new ThreadLocal<>();

	public void init(FilterConfig fConfig) throws ServletException {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpSession session = ((HttpServletRequest) request).getSession(true);
		
		HttpServletResponse res = (HttpServletResponse) response;
		Cookie[] cookies = ((HttpServletRequest) request).getCookies();
		int check = 0;
//		EmailInterface emailProxy = ProxyObject.getEmailProxy();
//		Emaildao emailDao=new Emaildao();
//		emailDao.makeAsVerified("kavi@gmail.com",5);
//		ArrayList<String> allMails = emailProxy.getVerifiedOrUnVerified(5,1);
//		System.out.println(allMails);
		if (cookies != null) {
			String sessionid;
			for (int i = 0; i < cookies.length; i++) {

				if (cookies[i].getName().equals("sessionid")) {

					sessionid = cookies[i].getValue();
					try {
						check = CacheValidation.checkId(sessionid);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (check == 1) {
						
						SessionInterface sessionProxy=ProxyObject.getSessionProxy();
						SessionContainer ob = (SessionContainer)sessionProxy.getSession(sessionid);
						int usrid = ob.getUsrid();
					
						UserInterface userProxy=ProxyObject.getUserProxy();
						UserDetails ud = (UserDetails) userProxy.getUser(usrid);
						int spamCheck = ud.getSpamCheck();
						if (spamCheck == 1) {
							res.sendRedirect("Login.jsp");
						} else {
							session.setAttribute("userid", usrid);
							threadLocalVariable.set(ud);
							
							chain.doFilter(request, response);
						
						}
					}
					
					if (check == 2 || check == 3) {
						cookies[i].setMaxAge(0);
						res.addCookie(cookies[i]);
					}
				}

			}
		}
		if (check == 0 || check == 2 || check == 3) {
			res.sendRedirect("Login.jsp");
		}

	}

	public void destroy() {
		System.out.println("destroy method");
	}

}
