package com.zoho.interfaces;

import com.zoho.model.UserDetails;

public interface UserInterface {
	  @RedisAnnotation("EmailInterface")
	UserDetails getUser(int usrid);

}
