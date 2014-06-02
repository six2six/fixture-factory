package br.com.six2six.fixturefactory.function;

import static junit.framework.Assert.assertEquals;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.junit.Test;

import br.com.six2six.fixturefactory.base.CalendarInterval;
import br.com.six2six.fixturefactory.base.CalendarSequence;
import br.com.six2six.fixturefactory.function.impl.SequenceFunction;
import br.com.six2six.fixturefactory.util.DateTimeUtil;

public class CalendarSequenceFunctionTest {

	@Test
	public void addOneDay() {
		Calendar baseCalendar = DateTimeUtil.toCalendar("2011-04-09", new SimpleDateFormat("yyyy-MM-dd"));
		SequenceFunction sequenceFunction = new SequenceFunction(new CalendarSequence(baseCalendar, new CalendarInterval(1, Calendar.DAY_OF_MONTH)));
		
		for (int i=0; i<3; i++) {
			Calendar calendar = sequenceFunction.generateValue();
			assertEquals("Calendars should be equal", baseCalendar, calendar);
			baseCalendar.add(Calendar.DAY_OF_MONTH, 1);
		}
	}
	
	@Test
	public void subtractOneDay() {
		Calendar baseCalendar = DateTimeUtil.toCalendar("2011-04-09", new SimpleDateFormat("yyyy-MM-dd"));
		SequenceFunction sequenceFunction = new SequenceFunction(new CalendarSequence(baseCalendar, new CalendarInterval(-1, Calendar.DAY_OF_MONTH)));
		
		for (int i=0; i<3; i++) {
			Calendar calendar = sequenceFunction.generateValue();
			assertEquals("Calendars should be equal", baseCalendar, calendar);
			baseCalendar.add(Calendar.DAY_OF_MONTH, -1);
		}
	}
	
	@Test
	public void addThreeDays() {
		Calendar baseCalendar = DateTimeUtil.toCalendar("2011-04-09", new SimpleDateFormat("yyyy-MM-dd"));
		SequenceFunction sequenceFunction = new SequenceFunction(new CalendarSequence(baseCalendar, new CalendarInterval(3, Calendar.DAY_OF_MONTH)));
		
		for (int i=0; i<3; i++) {
			Calendar calendar = sequenceFunction.generateValue();
			assertEquals("Calendars should be equal", baseCalendar, calendar);
			baseCalendar.add(Calendar.DAY_OF_MONTH, 3);
		}
	}
	
	@Test
	public void subtractThreeDays() {
		Calendar baseCalendar = DateTimeUtil.toCalendar("2011-04-09", new SimpleDateFormat("yyyy-MM-dd"));
		SequenceFunction sequenceFunction = new SequenceFunction(new CalendarSequence(baseCalendar, new CalendarInterval(-3, Calendar.DAY_OF_MONTH)));
		
		for (int i=0; i<3; i++) {
			Calendar calendar = sequenceFunction.generateValue();
			assertEquals("Calendars should be equal", baseCalendar, calendar);
			baseCalendar.add(Calendar.DAY_OF_MONTH, -3);
		}
	}
}
