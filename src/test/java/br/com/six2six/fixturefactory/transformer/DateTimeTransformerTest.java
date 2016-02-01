package br.com.six2six.fixturefactory.transformer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.TimeZone;

import javax.xml.datatype.DatatypeConfigurationException;

import org.junit.Test;

public class DateTimeTransformerTest {

	@Test
	public void transformCalendarToLocalDateTime() {
		Calendar value = Calendar.getInstance(TimeZone.getDefault());
		LocalDateTime date = value.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
		
		LocalDateTime transform = new DateTimeTransformer().transform(value, LocalDateTime.class);

		assertNotNull("LocalDateTime should not be null", transform);
		assertEquals("LocalDateTimes should be equal", date, transform);
	}

	@Test
	public void transformCalendarToLocalDate() throws DatatypeConfigurationException {
		Calendar value = Calendar.getInstance(TimeZone.getDefault());
		LocalDate date = value.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

		LocalDate transform = new DateTimeTransformer().transform(value, LocalDate.class);
		
		assertNotNull("LocalDate should not be null", transform);
		assertEquals("LocalDates should be equal", date, transform);
	}
	
	@Test
	public void transformCalendarToLocalTime() {
		Calendar value = Calendar.getInstance(TimeZone.getDefault());
		LocalTime time = value.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();

		LocalTime transform = new DateTimeTransformer().transform(value, LocalTime.class);

		assertNotNull("LocalTime should not be null", transform);
		assertEquals("LocalTimes should be equal", time, transform);
	}

	@Test
	public void transformCalendarToOffsetDateTime() {
		Calendar value = Calendar.getInstance(TimeZone.getDefault());
		OffsetDateTime offsetDateTime = value.toInstant().atZone(ZoneId.systemDefault()).toOffsetDateTime();

		OffsetDateTime transform = new DateTimeTransformer().transform(value, OffsetDateTime.class);

		assertNotNull("OffsetDateTime should not be null", transform);
		assertEquals("OffsetDateTimes should be equal", offsetDateTime, transform);
	}
	
	@Test
	public void transformCalendarToOffsetTime() {
		Calendar value = Calendar.getInstance(TimeZone.getDefault());
		OffsetTime offsetTime = value.toInstant().atZone(ZoneId.systemDefault()).toOffsetDateTime().toOffsetTime();
		
		OffsetTime transform = new DateTimeTransformer().transform(value, OffsetTime.class);
		
		assertNotNull("OffsetTime should not be null", transform);
		assertEquals("OffsetTimes should be equal", offsetTime, transform);
	}
	
	@Test
	public void transformCalendarToZonedDateTime() {
		Calendar value = Calendar.getInstance(TimeZone.getDefault());
		ZonedDateTime zonedDateTime = value.toInstant().atZone(ZoneId.systemDefault());
		
		ZonedDateTime transform = new DateTimeTransformer().transform(value, ZonedDateTime.class);
		
		assertNotNull("ZonedDateTime should not be null", transform);
		assertEquals("ZonedDateTimes should be equal", zonedDateTime, transform);
	}
	
	@Test
	public void transformCalendarToInstant() {
		Calendar value = Calendar.getInstance(TimeZone.getDefault());
		Instant instant = value.toInstant();
		
		Instant transform = new DateTimeTransformer().transform(value, Instant.class);
		
		assertNotNull("Instant should not be null", transform);
		assertEquals("Instants should be equal", instant, transform);
	}
	
	@Test
	public void transformNull() {
		assertNull(new DateTimeTransformer().transform(null, LocalDateTime.class));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void transformInvalidType() {
		new DateTimeTransformer().transform(Calendar.getInstance(), Character.class);
	}

}
