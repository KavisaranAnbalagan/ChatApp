package com.zoho.apis;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class TimeConversion {
	public  long getCurrentTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		SimpleDateFormat ldf = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
		Date d1 = null;

		try {
			d1 = ldf.parse(sdf.format(new Date()));
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}

		long millis = d1.getTime();

		return millis;
	}
	public Date getTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		SimpleDateFormat ldf = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
		Date d1 = null;

		try {
			d1 = ldf.parse(sdf.format(new Date()));
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}
		return d1;
	}
	public Date longToTime(long millis){

		TimeZone tz=TimeZone.getDefault();
		long l=millis+tz.getOffset(millis);
		Date d=new Date(l);
		System.out.println(d);
		return d;
	}
}
