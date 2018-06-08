package com.xzq.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 操作时间的一些工具类
 *
 */
public class TimeUtil {
	
	/**
	 * yyyyMMdd
	 */
	public static final String ISO_DATE_FORMAT = "yyyyMMdd";
	
	/**
	 * yyyy-MM-dd
	 */
	public static final String ISO_EXPANDED_DATE_FORMAT = "yyyy-MM-dd";
	
	
	/**
     * yyyy-MM-dd hh:mm:ss
     */
	public static String DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
	
	/**
	 * yyyyMMddHHmmss
	 */
    public static String DATE_PATTERN = "yyyyMMddHHmmss";
    
    /**
     * 是否严格判断日期格式
     */
    private static boolean LENIENT_DATE = false;
	
	/**
	 * 将毫秒数转换为date
	 * @param millis
	 * @return
	 */
	public static Date longToDate(long millis){
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(millis);
		return calendar.getTime();
	}
	
	/**
	 * 将秒换算成时分秒
	 * @param time
	 * @return
	 */
	public static String secToTime(long time) {
		String timeStr = null;
		long hour = 0;
		long minute = 0;
		long second = 0;
		if (time <= 0)
			return "00:00";
		else {
			minute = time / 60;
			if (minute < 60) {
				second = time % 60;
				timeStr = unitFormat(minute) + ":" + unitFormat(second);
			} else {
				hour = minute / 60;
				minute = minute % 60;
				second = time - hour * 3600 - minute * 60;
				timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":"
						+ unitFormat(second);
			}
		}
		return timeStr;
	}

	public static String unitFormat(long i) {
		String retStr = null;
		if (i >= 0 && i < 10)
			retStr = "0" + Long.toString(i);
		else
			retStr = "" + i;
		return retStr;
	}
	
	/**
	 * 获取昨天开始和截止时间
	 * @return
	 */
	public static Date[] getYesterdayStartAndEnd(){
		Date[] result = new Date[2];
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR, -1);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		result[1] = calendar.getTime();		
		calendar.set(Calendar.HOUR_OF_DAY, 00);
		calendar.set(Calendar.MINUTE, 00);
		calendar.set(Calendar.SECOND, 00);
		result[0] = calendar.getTime();
		return result;
	}
	
	public static Date[] getLastWeekStartAndEnd(){
		Date[] result = new Date[2];
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.WEEK_OF_YEAR, -1);
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		result[1] = calendar.getTime();
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		calendar.set(Calendar.HOUR_OF_DAY, 00);
		calendar.set(Calendar.MINUTE, 00);
		calendar.set(Calendar.SECOND, 00);
		result[0] = calendar.getTime();
		return result;
	}
	
	public static Date[] getLastMonthStartAndEnd(){
		Date[] result = new Date[2];
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.add(Calendar.DAY_OF_YEAR, -1);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		result[1] = calendar.getTime();
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 00);
		calendar.set(Calendar.MINUTE, 00);
		calendar.set(Calendar.SECOND, 00);
		result[0] = calendar.getTime();
		return result;
	}
	
	public static Date getYesterday(){
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR, -1);		
		return calendar.getTime();
	}
	public static String dateToString(Date date, String pattern) {

        if (date == null) {

            return null;
        }

        try {

            SimpleDateFormat sfDate = new SimpleDateFormat(pattern);
            sfDate.setLenient(false);

            return sfDate.format(date);
        } catch (Exception e) {

            return null;
        }
    }
	
	/**
     * 将时间转换成yyyy-MM-dd格式的字符串
     * @param date
     * 
     */
    public static String dateToString(Date date) {
        return dateToString(date, ISO_EXPANDED_DATE_FORMAT);
    }
}
