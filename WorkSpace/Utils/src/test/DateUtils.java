package test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
	
	public static void main(String[] args) {
		System.out.println(getBeforeMonthFirstDay(new Date()));
	}

	public static boolean comperDate(Date beforeDate, Date endDate) {
		long btime = beforeDate.getTime();
		long eime = endDate.getTime();
		if (eime > btime) {
			return true;
		}
		return false;
	}

	public static Date getBeforeMonthLastDay() {
		Calendar cale = Calendar.getInstance();
		cale.set(Calendar.DAY_OF_MONTH, 0);// 设置为1号,当前日期既为本月第一天
		cale.set(Calendar.HOUR_OF_DAY, 23);
		cale.set(Calendar.MINUTE, 59);
		cale.set(Calendar.SECOND, 59);
		cale.set(Calendar.MILLISECOND, 999);
		Date time = cale.getTime();

		return time;
	}

	public static Date getBeforeMonthFirstDay() {

		Calendar cale = Calendar.getInstance();// 获取当前日期
		cale.add(Calendar.MONTH, -1);
		cale.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
		cale.set(Calendar.HOUR_OF_DAY, 0);
		cale.set(Calendar.MINUTE, 0);
		cale.set(Calendar.SECOND, 0);
		cale.set(Calendar.MILLISECOND, 0);
		Date time = cale.getTime();
		return time;
	}

	public static Date getBeforeMonthLastDay(Date date) {
		Calendar cale = Calendar.getInstance();
		cale.setTime(date);
		cale.set(Calendar.DAY_OF_MONTH, 0);// 设置为1号,当前日期既为本月第一天
		cale.set(Calendar.HOUR_OF_DAY, 23);
		cale.set(Calendar.MINUTE, 59);
		cale.set(Calendar.SECOND, 59);
		cale.set(Calendar.MILLISECOND, 999);
		Date time = cale.getTime();

		return time;
	}

	public static Date getBeforeMonthFirstDay(Date date) {

		Calendar cale = Calendar.getInstance();// 获取当前日期
		cale.setTime(date);
		cale.add(Calendar.MONTH, -1);
		cale.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
		cale.set(Calendar.HOUR_OF_DAY, 0);
		cale.set(Calendar.MINUTE, 0);
		cale.set(Calendar.SECOND, 0);
		cale.set(Calendar.MILLISECOND, 0);
		Date time = cale.getTime();
		return time;
	}

	public static int getBeforeMonth() {
		Calendar cale = Calendar.getInstance();// 获取当前日期
		cale.add(Calendar.MONTH, -1);
		return cale.get(Calendar.MONTH) + 1;
	}

	public static String getBeforeYearMonth() {
		Calendar cale = Calendar.getInstance();// 获取当前日期
		cale.add(Calendar.MONTH, -1);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		return sdf.format(cale.getTime());
	}

}
