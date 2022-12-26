package br.com.six2six.fixturefactory.util;

import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.reflect.MethodUtils;

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
	
	public static Object toZonedDateTime(Calendar value) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		Object timeZone = MethodUtils.invokeMethod(value, "getTimeZone", ArrayUtils.EMPTY_OBJECT_ARRAY);
		Object zoneId = MethodUtils.invokeMethod(timeZone, "toZoneId", ArrayUtils.EMPTY_OBJECT_ARRAY);
		Object instant = MethodUtils.invokeMethod(value, "toInstant", ArrayUtils.EMPTY_OBJECT_ARRAY);
		Object zone = MethodUtils.invokeMethod(instant, "atZone", new Object[]{zoneId});
		return zone;
	}
	
}
