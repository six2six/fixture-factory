package br.com.six2six.fixturefactory.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;

public final class DateTimeUtils {

	private DateTimeUtils() throws InstantiationException {
		throw new InstantiationException("The class can't be instantiated");		
	}

	public static Calendar toCalendar(String source, DateFormat format) {
		Calendar date = Calendar.getInstance();
		try {
			date.setTimeInMillis(format.parse(source).getTime());
		} catch (ParseException e) {
			throw new IllegalArgumentException(e);
		}
		return date;
	}
	
	public static java.time.ZonedDateTime toZonedDateTime(Calendar value) {
		return value.toInstant().atZone(value.getTimeZone().toZoneId());
	}
	
}
