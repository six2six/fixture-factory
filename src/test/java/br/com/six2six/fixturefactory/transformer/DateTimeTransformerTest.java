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

import javax.xml.datatype.DatatypeConfigurationException;

import org.junit.Test;

public class DateTimeTransformerTest {

	@Test
	public void transformCalendarToLocalDateTime() {
		LocalDateTime date = LocalDateTime.now();
		Calendar value = Calendar.getInstance();
		value.setTimeInMillis(date.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
		
		LocalDateTime transform = new DateTimeTransformer().transform(value, LocalDateTime.class);

		assertNotNull("LocalDateTime should not be null", transform);
		assertEquals("LocalDateTimes should be equal", date, transform);
	}

	@Test
	public void transformCalendarToLocalDate() throws DatatypeConfigurationException {
		LocalDate date = LocalDate.now();
		Calendar value = Calendar.getInstance();

		LocalDate transform = new DateTimeTransformer().transform(value, LocalDate.class);
		
		assertNotNull("LocalDate should not be null", transform);
		assertEquals("LocalDates should be equal", date, transform);
	}
	
	@Test
	public void transformCalendarToLocalTime() {
		LocalTime time = LocalTime.now();
		Calendar value = Calendar.getInstance();

		LocalTime transform = new DateTimeTransformer().transform(value, LocalTime.class);

		assertNotNull("LocalTime should not be null", transform);
		assertEquals("LocalTimes should be equal", time, transform);
	}

	@Test
	public void transformCalendarToOffsetDateTime() {
		OffsetDateTime offsetDateTime = OffsetDateTime.now();
		Calendar value = Calendar.getInstance();

		OffsetDateTime transform = new DateTimeTransformer().transform(value, OffsetDateTime.class);

		assertNotNull("OffsetDateTime should not be null", transform);
		assertEquals("OffsetDateTimes should be equal", offsetDateTime, transform);
	}
	
	@Test
	public void transformCalendarToOffsetTime() {
		OffsetTime offsetTime = OffsetTime.now();
		Calendar value = Calendar.getInstance();
		
		OffsetTime transform = new DateTimeTransformer().transform(value, OffsetTime.class);
		
		assertNotNull("OffsetTime should not be null", transform);
		assertEquals("OffsetTimes should be equal", offsetTime, transform);
	}
	
	@Test
	public void transformCalendarToZonedDateTime() {
		ZonedDateTime zonedDateTime = ZonedDateTime.now();
		Calendar value = Calendar.getInstance();
		
		ZonedDateTime transform = new DateTimeTransformer().transform(value, ZonedDateTime.class);
		
		assertNotNull("ZonedDateTime should not be null", transform);
		assertEquals("ZonedDateTimes should be equal", zonedDateTime, transform);
	}
	
	@Test
	public void transformCalendarToInstant() {
		Instant instant = Instant.now();
		Calendar value = Calendar.getInstance();
		
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
