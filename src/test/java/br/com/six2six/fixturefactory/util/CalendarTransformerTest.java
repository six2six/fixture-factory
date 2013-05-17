package br.com.six2six.fixturefactory.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.junit.Test;

import br.com.six2six.fixturefactory.util.CalendarTransformer;

public class CalendarTransformerTest {

	@Test
	public void transformCalendarToDate() {
		java.util.Date date = new java.util.Date();
		Calendar value = Calendar.getInstance();
		value.setTime(date);
		
		Date transform = new CalendarTransformer().transform(value, java.util.Date.class);

		assertNotNull("java.util.Date should not be null", transform);
		assertEquals("java.util.Dates should be equal", date, transform);
	}

	@Test
	public void transformCalendarToXMLGregorianCalendar() throws ParseException, DatatypeConfigurationException {
		Calendar value = Calendar.getInstance();

		XMLGregorianCalendar transform = new CalendarTransformer().transform(value, XMLGregorianCalendar.class);
		
		assertNotNull("XMLGregorianCalendar should not be null", transform);
		assertEquals("XMLGregorianCalendars should be equal", DatatypeFactory.newInstance().newXMLGregorianCalendar((GregorianCalendar) value), transform);
	}
	
	@Test
	public void transformCalendarToSqlDate() {
		Calendar value = Calendar.getInstance();

		java.sql.Date transform = new CalendarTransformer().transform(value, java.sql.Date.class);

		assertNotNull("java.sql.Date should not be null", transform);
		assertEquals("java.sql.Dates should be equal", new java.sql.Date(value.getTimeInMillis()), transform);
	}

	@Test
	public void transformCalendarToCalendar() {
		Calendar value = Calendar.getInstance();

		Calendar transform = new CalendarTransformer().transform(value, Calendar.class);

		assertNotNull("Calendar should not be null", transform);
		assertEquals("Calendars should be equal", value, transform);
	}
	
	@Test
	public void transformNull() {
		assertNull(new CalendarTransformer().transform(null, java.util.Date.class));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void transformInvalidType() {
		new CalendarTransformer().transform(Calendar.getInstance(), Character.class);
	}

}
