package com.recharge.common;

/**
 * Created by IntelliJ IDEA.
 * User: liuyonglu
 * Date: 2011-6-22
 * Time: 17:40:45
 * To change this template use File | Settings | File Templates.
 */

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * 日期工具类
 * File: DateUtil.java
 * User: leizhimin
 * Date: 2008-2-14 15:17:20
 */
public class DateUtil {

    /**
     * 获取日期中的年
     *
     * @param date 日期
     * @return 年份
     */
    public static String getYear(Date date) {
        DateFormat f_year = new SimpleDateFormat("yyyy");
        return f_year.format(date).toString();
    }

    /**
     * 获取日期中的月
     *
     * @param date 日期
     * @return 月份
     */
    public static String getMonth(Date date) {
        DateFormat f_month = new SimpleDateFormat("MM");
        return f_month.format(date).toString();
    }

    /**
     * 获取日期中天
     *
     * @param date 日期
     * @return 天
     */
    public static String getDay(Date date) {
        DateFormat f_day = new SimpleDateFormat("dd");
        return f_day.format(date).toString();
    }

    /**
     * 获取日期中的星期
     *
     * @param date 日期
     * @return 星期
     */
    public static String getWeek(Date date) {
        DateFormat f_week = new SimpleDateFormat("EEEEEEE");
        return f_week.format(date).toString();
    }

    /**
     * 获取日期中的时间
     *
     * @param date 日期
     * @return 时间
     */
    public static String getTime(Date date) {
        DateFormat f_time = new SimpleDateFormat("HH时mm分 ss秒");
        return f_time.format(date).toString();
    }

    public Date convertXmlGregorianCalendartoDate(XMLGregorianCalendar date) {
        GregorianCalendar dt = date.toGregorianCalendar();
        Date dd = dt.getTime();
        return dd;
    }

    /**
     * 取当前日期
     * @return
     */
    public static String getNow() {
        Date date=new Date();
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
        return sd.format(date);
    }
    public static XMLGregorianCalendar convertToXMLGregorianCalendar(Date date) {

        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        XMLGregorianCalendar gc = null;
        try {
            gc = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
        } catch (Exception e) {

            e.printStackTrace();
        }
        return gc;
    }

    
    /**
     * 给定日期上加多少月
     * @param date
     * @param i
     * @return
     */
    public static Date getDateAddMon(Date date,int i) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, i);
        calendar.set(Calendar.DATE, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        
        return calendar.getTime();

    }
    public static String format(Date date, String pattern) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }
    /**
     * webservice时间转换类
     *
     * @param dateStr
     * @return
     */
    public static XMLGregorianCalendar convertToXmlGregorianCalendar(String dateStr) {
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        XMLGregorianCalendar xgc = null;
        try {
            Date date = sd.parse(dateStr);
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTime(date);

            xgc = DatatypeFactory.newInstance().newXMLGregorianCalendar(gc);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
        }
        return xgc;
    }
    public static String  getDateFormtimestamp(String timestamp){
 
    	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    	String date = sdf.format(new Date(Long.parseLong(timestamp)));

    	return date;
    }
    
    public static Date strToDate(String DateStr){
    	if(null==DateStr||"".trim().equals(DateStr))return null;
    	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
    	Date date = null;
    	try {
    		date = sdf.parse(DateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
    	return date;
    }
    
    public static Date getSunDay(Date date) {

        Calendar calendar = Calendar.getInstance();

        calendar.setTime(date);

        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        Date date1 = calendar.getTime();
        date1.setHours(0);
        date1.setMinutes(0);
        date1.setSeconds(0);

        return date1;

   } 
    public static Date getSaturDay(Date date) {

        Calendar calendar = Calendar.getInstance();

        calendar.setTime(date);

        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        Date date1 = calendar.getTime();
        date1.setHours(23);
        date1.setMinutes(59);
        date1.setSeconds(59);

        return date1;

   } 
    
    public static String formatYmdhmsm(Date date){
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	return sdf.format(date);
    }
    public static void main(String[] args){
        System.out.println(getSunDay(new Date()));  
    }
    
    
}
