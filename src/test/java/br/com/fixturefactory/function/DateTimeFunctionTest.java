package br.com.fixturefactory.function;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import junit.framework.Assert;

import org.junit.Test;

import br.com.fixturefactory.function.DateTimeFunction.DateType;

import static br.com.fixturefactory.util.DateTimeUtil.toCalendar;

public class DateTimeFunctionTest {

	private static DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	
	@Test
	public void afterDate() {
		String source = "2011-04-07";
		Calendar value = new DateTimeFunction(toCalendar(source, format), DateType.AFTER).generateValue();
		Assert.assertNotNull("Generated date can not be null", value);
		Assert.assertTrue(String.format("Generated date is not after %s", source), value.after(toCalendar(source, format)));
	}

	@Test
	public void beforeDate() {
		String source = "2011-04-07";
		Calendar value = new DateTimeFunction(toCalendar(source, format), DateType.BEFORE).generateValue();
		Assert.assertNotNull("Generated date can not be null", value);
		Assert.assertTrue(String.format("Generated date is not before %s", source), value.before(toCalendar(source, format)));
	}
	@Test
	public void randomDate() {
		String start = "2011-04-01", end = "2011-04-30";
		Calendar value = new DateTimeFunction(toCalendar(start, format), toCalendar(end, format)).generateValue();
		Assert.assertNotNull("Generated date can not be null", value);
		Assert.assertTrue(String.format("Generated date is not between %s and %s", start, end), toCalendar(start, format).before(value) && value.before(toCalendar(end, format)));
	}
	
}
