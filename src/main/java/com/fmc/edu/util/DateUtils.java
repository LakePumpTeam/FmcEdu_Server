package com.fmc.edu.util;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Yove on 5/8/2015.
 */
public class DateUtils {

    public static final String PATTERN_STUDENT_BIRTH = "yyyy-MM-dd";

    public static final String PATTERN_TIME = "HH:mm:ss";

    public static final String PATTERN_DATE = "yyyy-MM-dd HH:mm:ss";

    public static final String[] WEEK = new String[]{"星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"};

    public static Timestamp getDaysLater(int pDays) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, pDays);
        return new Timestamp(calendar.getTimeInMillis());
    }

    public static Date convertStringToDate(String pBirthString) throws ParseException {
        if (StringUtils.isBlank(pBirthString)) {
            return null;
        }
        return (new SimpleDateFormat(PATTERN_STUDENT_BIRTH)).parse(pBirthString);
    }

    public static Date convertStringToDateTime(String pBirthString) throws ParseException {
        if (StringUtils.isBlank(pBirthString)) {
            return null;
        }
        return (new SimpleDateFormat(PATTERN_STUDENT_BIRTH + " " + PATTERN_TIME)).parse(pBirthString);
    }

    public static String convertDateToString(Date pBirth) {
        return (new SimpleDateFormat(PATTERN_STUDENT_BIRTH)).format(pBirth);
    }

    public static Date addSeconds(final int pSeconds, Date pDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(pDate);
        cal.add(Calendar.SECOND, pSeconds);
        return cal.getTime();
    }


    public static Time convertStringToTime(String pTimeString) throws ParseException {
        if (StringUtils.isBlank(pTimeString)) {
            return null;
        }
        Date date = (new SimpleDateFormat(PATTERN_TIME)).parse(pTimeString);
        return new Time(date.getTime());
    }

    public static String convertTimeToString(Time pTime) {
        return (new SimpleDateFormat(PATTERN_TIME)).format(pTime);
    }

    public static Map<String, Date> getOneWeekDatePeriod(Date pTimestamp) {
        Map<String, Date> weekDate = new HashMap<String, Date>(2);
        Calendar cal = Calendar.getInstance();
        cal.setTime(pTimestamp);
        cal.add(Calendar.DATE, -1);

        SimpleDateFormat df = new SimpleDateFormat(PATTERN_STUDENT_BIRTH);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        try {
            weekDate.put("startDate", convertStringToDateTime(convertDateToString(new Date(cal.getTime().getTime())) + " 00:00:00"));
            System.out.println(df.format(cal.getTime()));
            cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
            cal.add(Calendar.WEEK_OF_YEAR, 1);
            System.out.println(df.format(cal.getTime()));
            weekDate.put("endDate", convertStringToDateTime(convertDateToString(new Date(cal.getTime().getTime())) + " 23:59:59"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return weekDate;
    }

    public static Date getDateTimeStart(Date pTimestamp) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(pTimestamp);
        try {
            return convertStringToDateTime(convertDateToString(new Date(cal.getTime().getTime())) + " 00:00:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date getDateTimeEnd(Date pTimestamp) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(pTimestamp);
        try {
            return convertStringToDateTime(convertDateToString(new Date(cal.getTime().getTime())) + " 23:59:59");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date getDateTimeMiddle(Date pTimestamp) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(pTimestamp);
        try {
            return convertStringToDateTime(convertDateToString(new Date(cal.getTime().getTime())) + " 12:00:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean isMorning(Date pDate) {
        return pDate.before(getDateTimeMiddle(new Timestamp(pDate.getTime())));
    }

    public static Date addDays(Date pDate, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(pDate);
        cal.add(Calendar.DATE, days);
        return cal.getTime();
    }

    public static Date minusDays(Date pDate, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(pDate);
        cal.add(Calendar.DATE, -days);
        return cal.getTime();
    }

    public static int getWeek(Date pDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(pDate);
        cal.add(Calendar.DATE, -1);
        System.out.println(cal.get(Calendar.DAY_OF_WEEK));
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    public static String convertWeekToString(int pWeek) {
        if (pWeek < 1 || pWeek > 7) {
            return "";
        }
        return WEEK[pWeek - 1];
    }

    public static String convertDateToDateTimeString(Date pDate) {
        if (pDate == null) {
            return "";
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(PATTERN_DATE);
        return simpleDateFormat.format(pDate);
    }

    public static void main(String[] args) {
        System.out.println(convertDateToString(new Date(minusDays(new Date(System.currentTimeMillis()), 7 + 1).getTime())));
        System.out.println(convertWeekToString(getWeek(minusDays(new Date(System.currentTimeMillis()), 7 + 1))));
    }
}
