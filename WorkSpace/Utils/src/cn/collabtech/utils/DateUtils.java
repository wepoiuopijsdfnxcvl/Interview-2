package cn.collabtech.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
	public static void main(String[] args) {

		 String time="2010-11-20 00:02:00"; 
		  Date date=null; 
		  try {
		  SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
			date=formatter.parse(time);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			int i = calendar.get(Calendar.MINUTE);
			System.out.println(i);
		} catch (Exception e) {
			e.printStackTrace();
		} 

	}

}
