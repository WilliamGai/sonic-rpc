package org.sonic.rpc.core;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.function.Supplier;

public class TimeUtil {
	public static final int HOUR_SECONDS = 3600;
	public static final int DAY_SECONDS = HOUR_SECONDS * 24;
	public static final int WEEK_SECONDS = DAY_SECONDS * 7;
	public static final int DAY_MILLISECONDS = DAY_SECONDS * 1000;
	public static final int WEEK_MILLISECONDS = DAY_MILLISECONDS * 7;
	/**sdf有全局变量线程不安全,用ThreadLocal提供线程安全的sdf*/
	public static final ThreadLocal<DateFormat> SDF = new ThreadLocal<DateFormat>() {
		@Override
		protected DateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
		}
	};
	public static final ThreadLocal<DateFormat> SDF_WITH_MILLIS = new ThreadLocal<DateFormat>() {
		@Override
		protected DateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss:SSS");
		}
	};
	/**HH:mm:ss*/
	public static final ThreadLocal<DateFormat> SDF_TIME = new ThreadLocal<DateFormat>() {
		@Override
		protected DateFormat initialValue() {
			return new SimpleDateFormat("HH:mm:ss");
		}
	};
	public static boolean isSameWeek(long a, long b){
		Calendar ca = Calendar.getInstance();
		ca.setTimeInMillis(a);
		Calendar cb = Calendar.getInstance();
		cb.setTimeInMillis(b);
		
		if(ca.get(Calendar.YEAR) != cb.get(Calendar.YEAR)){
			return false;
		}
		return ca.get(Calendar.WEEK_OF_YEAR) == cb.get(Calendar.WEEK_OF_YEAR);
	}
	/**
	 * 汉语习惯周日为一周的最后一天
	 * @param time0
	 * @param time1
	 * @return
	 */
	public static boolean isSameSinoWeek(long time0, long time1){
		Calendar ca = Calendar.getInstance();
		ca.setTimeInMillis(time0);
		ca.add(Calendar.DAY_OF_YEAR, -1);
		Calendar cb = Calendar.getInstance();
		cb.setTimeInMillis(time1);
		cb.add(Calendar.DAY_OF_YEAR, -1);
		if(ca.get(Calendar.YEAR) != cb.get(Calendar.YEAR)){
			return false;
		}
		return ca.get(Calendar.WEEK_OF_YEAR) == cb.get(Calendar.WEEK_OF_YEAR);
	}
	public static boolean isSameDay(long ta, long tb) {
		return formatTime(ta, "yyyyMMdd").equals(formatTime(tb, "yyyyMMdd"));
	}
	public static String formatTime(long timestamp, String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.format(new Date(timestamp));
	}
	public static long parseAndGetTime(String s, String pattern) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.parse(s).getTime();
	}
	public static String getLocalTime(){
		return SDF.get().format(new Date());
	}
	public static String getLocalTimeFull(){
		return SDF_WITH_MILLIS.get().format(new Date());
	}
	/**
	 * 2017年4月20日21:51:21
	 * @return yyyy-MM-dd-HH:mm:ss时间
	 */
	public static String getTimeString(String value, Supplier<Long> supplier) {
		try {
			long time = supplier.get();
			return TimeUtil.SDF.get().format(new Date(time));
		} catch (Exception e) {
			LogCore.BASE.warn("time formatter err{}:", value,  e);
			return value;
		}
	}
	public static String getTimeStr(){
		return SDF_TIME.get().format(new Date());
	}
	public static Long getTime(String timeStr) throws ParseException{
		return SDF.get().parse(timeStr).getTime();
	}
}
