package br.com.six2six.fixturefactory.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;

public class DateTimeUtils {

	public static Calendar toCalendar(String source, DateFormat format) {
		Calendar date = Calendar.getInstance();
		try {
			date.setTimeInMillis(format.parse(source).getTime());
		} catch (ParseException e) {
			throw new IllegalArgumentException(e);
		}
		return date;
	}
	
}
