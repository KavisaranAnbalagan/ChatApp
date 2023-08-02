package com.zoho.interfaces;


import com.zoho.model.SessionContainer;


public interface SessionInterface {
	  @RedisAnnotation("SessionInterface")
	SessionContainer getSession(String sessionId);

}
