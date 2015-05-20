package com.fmc.edu.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Yove on 5/8/2015.
 */
public class DateUtils {

    public static final String PATTERN_STUDENT_BIRTH = "yyyy-MM-dd";

    public static Timestamp getDaysLater(int pDays) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, pDays);
        return new Timestamp(calendar.getTimeInMillis());
    }

    public static Date getStudentBirth(String pBirthString) throws ParseException {
        if (StringUtils.isBlank(pBirthString)) {
            return null;
        }
        return (new SimpleDateFormat(PATTERN_STUDENT_BIRTH)).parse(pBirthString);
    }

	public static String getStudentBirthString(Date pBirth) {
		return (new SimpleDateFormat(PATTERN_STUDENT_BIRTH)).format(pBirth);
	}

	public static Date addSeconds(final int pSeconds, Date pDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(pDate);
		cal.add(Calendar.SECOND, pSeconds);
		return cal.getTime();
	}
}
