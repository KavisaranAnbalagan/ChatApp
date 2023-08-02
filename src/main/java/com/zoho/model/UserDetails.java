package com.zoho.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.zoho.apis.TimeConversion;

@SuppressWarnings("serial")
public class UserDetails extends ObjectFactory implements Serializable {
	private String fname;
	private String lname;
	private LocalDate dob;
	private String gender;
	private Date createtime;
	private Date updatetime;
	private int usrid;
	private int spamCheck;

	public int getSpamCheck() {
		return spamCheck;
	}

	public void setSpamCheck(int spamCheck) {
		this.spamCheck = spamCheck;
	}

	public UserDetails(HashMap<String, Object> map2) {
		//System.out.println("1.5");
		super("UserDetails", map2);
		System.out.println("1.55");
		hashmapMethod(map2);
		System.out.println("1.75");
	}

	public UserDetails(String fname2, String lname2, LocalDate dob2, String gender2) {
		this.fname = fname2;
		this.lname = lname2;
		this.dob = dob2;
		this.gender = gender2;
	}

	public UserDetails() {
		// TODO Auto-generated constructor stub
	}

	public UserDetails(HashMap<String, String> hm, String string) {
		super();
	}

	public int getUsrid() {
		return usrid;
	}

	public void setUsrid(int usrid) {
		this.usrid = usrid;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public LocalDate getDob() {
		return dob;
	}

	public void setDob(LocalDate dob) {
		this.dob = dob;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	Object ob;

	@SuppressWarnings("rawtypes")
	public void hashmapMethod(HashMap<String, Object> map) {
		TimeConversion tc = new TimeConversion();
		// TODO Auto-generated method stub
		for (Map.Entry m : map.entrySet()) {
			
			if (m.getKey().equals("fname")) {
				ob = m.getValue();

				String s = ob.toString();
				fname = s;
				
			}
			if (m.getKey().equals("lname")) {
				ob = m.getValue();
				String s = ob.toString();
				
				lname = s;
			}
			if (m.getKey().equals("gender")) {
				ob = m.getValue();
				String s = ob.toString();
				
				gender = s;
			}
			if (m.getKey().equals("createtime")) {
				ob = m.getValue();
				long num = (long) ob;
				Date dt = tc.longToTime(num);
				setCreatetime(dt);
			}
			if (m.getKey().equals("updatetime")) {
				ob = m.getValue();
				long num = (long) ob;
				Date dt = tc.longToTime(num);
				setUpdatetime(dt);
			}
			if (m.getKey().equals("dob")) {
			
				ob = m.getValue();
				DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				LocalDate ld = LocalDate.parse(ob.toString(), format);
				dob = ld;
				

			}
			if(m.getKey().equals("SpamOrNot")){
				ob=m.getValue();
				int num=(int)ob;
				spamCheck=num;
				System.out.println("spamcheck="+num);
			}
		}
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

}
