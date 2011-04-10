package br.com.fixturefactory.function;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import junit.framework.Assert;

import org.junit.Test;

import br.com.fixturefactory.util.DateTimeUtil;


public class CalendarSequenceFunctionTest {

	@Test
	public void addOneDay() {
		Calendar baseCalendar = DateTimeUtil.toCalendar("2011-04-09", new SimpleDateFormat("yyyy-MM-dd"));
		CalendarSequenceFunction sequenceFunction = new CalendarSequenceFunction(baseCalendar, new CalendarInterval(1, Calendar.DAY_OF_MONTH));
		
		for (int i=0; i<3; i++) {
			Calendar calendar = sequenceFunction.generateValue();
			Assert.assertEquals("Calendars should be equal", baseCalendar, calendar);
			baseCalendar.add(Calendar.DAY_OF_MONTH, 1);
		}
	}
	
	@Test
	public void subtractOneDay() {
		Calendar baseCalendar = DateTimeUtil.toCalendar("2011-04-09", new SimpleDateFormat("yyyy-MM-dd"));
		CalendarSequenceFunction sequenceFunction = new CalendarSequenceFunction(baseCalendar, new CalendarInterval(-1, Calendar.DAY_OF_MONTH));
		
		for (int i=0; i<3; i++) {
			Calendar calendar = sequenceFunction.generateValue();
			Assert.assertEquals("Calendars should be equal", baseCalendar, calendar);
			baseCalendar.add(Calendar.DAY_OF_MONTH, -1);
		}
	}
	
	@Test
	public void addThreeDays() {
		Calendar baseCalendar = DateTimeUtil.toCalendar("2011-04-09", new SimpleDateFormat("yyyy-MM-dd"));
		CalendarSequenceFunction sequenceFunction = new CalendarSequenceFunction(baseCalendar, new CalendarInterval(3, Calendar.DAY_OF_MONTH));
		
		for (int i=0; i<3; i++) {
			Calendar calendar = sequenceFunction.generateValue();
			Assert.assertEquals("Calendars should be equal", baseCalendar, calendar);
			baseCalendar.add(Calendar.DAY_OF_MONTH, 3);
		}
	}
	
	@Test
	public void subtractThreeDays() {
		Calendar baseCalendar = DateTimeUtil.toCalendar("2011-04-09", new SimpleDateFormat("yyyy-MM-dd"));
		CalendarSequenceFunction sequenceFunction = new CalendarSequenceFunction(baseCalendar, new CalendarInterval(-3, Calendar.DAY_OF_MONTH));
		
		for (int i=0; i<3; i++) {
			Calendar calendar = sequenceFunction.generateValue();
			Assert.assertEquals("Calendars should be equal", baseCalendar, calendar);
			baseCalendar.add(Calendar.DAY_OF_MONTH, -3);
		}
	}
}
