package br.com.six2six.fixturefactory.transformer;

import static br.com.six2six.fixturefactory.util.DateTimeUtils.toZonedDateTime;

import java.util.Calendar;

import org.apache.commons.lang.ClassUtils;

public class DateTimeTransformer implements Transformer {

	@Override
	public <T> T transform(Object value, Class<T> type)  {
		Object returnValue = null;
		
		if (value == null) {
			return null;
		}
		
		if (ClassUtils.isAssignable(type, java.time.LocalDateTime.class)) {
			returnValue = toZonedDateTime((Calendar) value).toLocalDateTime();
			
		} else if (ClassUtils.isAssignable(type, java.time.LocalDate.class)) {
			returnValue = toZonedDateTime((Calendar) value).toLocalDate();
			
		} else if (ClassUtils.isAssignable(type, java.time.LocalTime.class)) {
			returnValue = toZonedDateTime((Calendar) value).toLocalTime();
			
		} else if (ClassUtils.isAssignable(type, java.time.OffsetDateTime.class)) { 
			returnValue = toZonedDateTime((Calendar) value).toOffsetDateTime();
			
		} else if (ClassUtils.isAssignable(type, java.time.ZonedDateTime.class)) { 
			returnValue = toZonedDateTime((Calendar) value);
			
		} else if (ClassUtils.isAssignable(type, java.time.OffsetTime.class)) { 
			returnValue = toZonedDateTime((Calendar) value).toOffsetDateTime().toOffsetTime();
			
		} else if (ClassUtils.isAssignable(type, java.time.Instant.class)) { 
			returnValue = ((Calendar) value).toInstant();
			
		} else if (ClassUtils.isAssignable(type, java.time.Instant.class)) { 
			returnValue = ((Calendar) value).toInstant();
			
		} else {
			throw new IllegalArgumentException("Incorrect type for transformer: " + type.getCanonicalName());
		}
		
		return type.cast(returnValue);
	}

    @Override
	public boolean accepts(Object value, Class<?> type) {
        return value instanceof Calendar && java.time.temporal.Temporal.class.isAssignableFrom(type);
    }
}
