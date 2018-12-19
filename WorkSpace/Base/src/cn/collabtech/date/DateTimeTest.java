package cn.collabtech.date;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Calendar;

public class DateTimeTest {
	public static void main(String[] args) {
		/**
		 *   如何取得年 月日、小时分钟秒？
		 */
		Calendar cal = Calendar.getInstance();
		System.out.println(cal.get(Calendar.YEAR));
		System.out.println(cal.get(Calendar.MONTH)); // 0 - 11
		System.out.println(cal.get(Calendar.DATE));
		System.out.println(cal.get(Calendar.HOUR_OF_DAY));
		System.out.println(cal.get(Calendar.MINUTE));
		System.out.println(cal.get(Calendar.SECOND));
		System.out.println("----------------------------------");
		// java 8
		LocalDateTime dt = LocalDateTime.now();
		System.out.println(dt.getYear());
		System.out.println(dt.getMonthValue()); // 1 - 12
		System.out.println(dt.getDayOfMonth());
		System.out.println(dt.getHour());
		System.out.println(dt.getMinute());
		System.out.println(dt.getSecond());
		/**
		 * 如何取得从 1970  年 1  月  1  日 0   时 0  分 0  秒到现在的毫秒数 ？
		 */
		 Calendar.getInstance().getTimeInMillis(); //第一种方式
		 System.currentTimeMillis(); //第二种方式
		 // Java 8
		 Clock.systemDefaultZone().millis();
	}
}
