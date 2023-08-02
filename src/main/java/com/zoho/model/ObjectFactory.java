package com.zoho.model;

import java.io.Serializable;
import java.util.HashMap;

@SuppressWarnings("serial")
public class ObjectFactory implements Serializable{
   private HashMap<String, HashMap<String, Object>> hmp1=null;
   
   
	public ObjectFactory(String tablename,HashMap<String,Object> hm) {
		this.hmp1=new HashMap<>();
		hmp1.put(tablename, hm);
		
	}


	public ObjectFactory() {
		// TODO Auto-generated constructor stub
	}


	public HashMap<String, HashMap<String, Object>> getHmp1() {
		return hmp1;
	}

	
}
