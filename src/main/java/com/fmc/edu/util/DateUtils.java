package com.fmc.edu.util;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Yove on 5/8/2015.
 */
public class DateUtils {

	public static Timestamp getDaysLater(int pDays) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DATE, pDays);
		return new Timestamp(calendar.getTimeInMillis());
	}
}
