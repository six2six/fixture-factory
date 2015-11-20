package br.com.six2six.fixturefactory.transformer;

import static br.com.six2six.fixturefactory.util.DateTimeUtils.toZonedDateTime;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.ZonedDateTime;
import java.time.temporal.Temporal;
import java.util.Calendar;

import org.apache.commons.lang.ClassUtils;

public class DateTimeTransformer implements Transformer {

	@Override
	public <T> T transform(Object value, Class<T> type)  {
		Object returnValue = null;
		
		if (value == null) {
			return null;
		}
		
		if (ClassUtils.isAssignable(type, LocalDateTime.class)) {
			returnValue = toZonedDateTime((Calendar) value).toLocalDateTime();
			
		} else if (ClassUtils.isAssignable(type, LocalDate.class)) {
			returnValue = toZonedDateTime((Calendar) value).toLocalDate();
			
		} else if (ClassUtils.isAssignable(type, LocalTime.class)) {
			returnValue = toZonedDateTime((Calendar) value).toLocalTime();
			
		} else if (ClassUtils.isAssignable(type, OffsetDateTime.class)) { 
			returnValue = toZonedDateTime((Calendar) value).toOffsetDateTime();
			
		} else if (ClassUtils.isAssignable(type, ZonedDateTime.class)) { 
			returnValue = toZonedDateTime((Calendar) value);
			
		} else if (ClassUtils.isAssignable(type, OffsetTime.class)) { 
			returnValue = toZonedDateTime((Calendar) value).toOffsetDateTime().toOffsetTime();
			
		} else if (ClassUtils.isAssignable(type, Instant.class)) { 
			returnValue = ((Calendar) value).toInstant();
			
		} else if (ClassUtils.isAssignable(type, Instant.class)) { 
			returnValue = ((Calendar) value).toInstant();
			
		} else {
			throw new IllegalArgumentException("Incorrect type for transformer: " + type.getCanonicalName());
		}
		
		return type.cast(returnValue);
	}

    @Override
	public boolean accepts(Object value, Class<?> type) {
        return value instanceof Calendar && Temporal.class.isAssignableFrom(type);
    }
}
