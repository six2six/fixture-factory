package br.com.six2six.fixturefactory.util;

import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.lang.ClassUtils;

public class CalendarTransformer {

	public <T> T transform(Object value, Class<T> type)  {
		Object returnValue = null;
		
		if (value == null) {
			return null;
		}
		
		if (ClassUtils.isAssignable(type, XMLGregorianCalendar.class)) {
			try {
				returnValue = DatatypeFactory.newInstance().newXMLGregorianCalendar((GregorianCalendar) value);
			} catch (DatatypeConfigurationException e) {
				throw new IllegalArgumentException("Error parser Calendar to XMLGregorianCalendar", e);
			}
			
		} else if (ClassUtils.isAssignable(type, java.sql.Date.class)) {
			returnValue = new java.sql.Date(((Calendar) value).getTimeInMillis());
			
		} else if (ClassUtils.isAssignable(type, java.util.Date.class)) {
			returnValue = ((Calendar) value).getTime();
		
		} else if (ClassUtils.isAssignable(type, Calendar.class)) { 
			returnValue = value;
			
		} else {
			throw new IllegalArgumentException("Incorrect type for transformer: " + type.getCanonicalName());
		}
		
		return type.cast(returnValue);
	}
	
}
