package br.com.six2six.fixturefactory.transformer;

import static org.apache.commons.lang.ArrayUtils.EMPTY_OBJECT_ARRAY;
import static org.apache.commons.lang.reflect.MethodUtils.invokeMethod;

import java.lang.reflect.InvocationTargetException;
import java.util.Calendar;

import org.apache.commons.lang.ClassUtils;

import br.com.six2six.fixturefactory.JavaVersion;
import br.com.six2six.fixturefactory.util.DateTimeUtils;

public class DateTimeTransformer implements Transformer {

	@Override
	public <T> T transform(Object value, Class<T> type)  {
		Object returnValue = null;
		
		if (value == null || !JavaVersion.current().gte(JavaVersion.JAVA_8)) {
			return null;
		}
		
		try {
			Object zonedDateTime = DateTimeUtils.toZonedDateTime((Calendar) value);
			if (ClassUtils.isAssignable(type, ClassUtils.getClass("java.time.LocalDateTime"))) {
				returnValue = invokeMethod(zonedDateTime, "toLocalDateTime", EMPTY_OBJECT_ARRAY);
				
			} else if (ClassUtils.isAssignable(type, ClassUtils.getClass("java.time.LocalDate"))) {
				returnValue = invokeMethod(zonedDateTime, "toLocalDate", EMPTY_OBJECT_ARRAY);
				
			} else if (ClassUtils.isAssignable(type, ClassUtils.getClass("java.time.LocalTime"))) {
				returnValue = invokeMethod(zonedDateTime, "toLocalTime", EMPTY_OBJECT_ARRAY);
				
			} else if (ClassUtils.isAssignable(type, ClassUtils.getClass("java.time.OffsetDateTime"))) {
				returnValue = invokeMethod(zonedDateTime, "toOffsetDateTime", EMPTY_OBJECT_ARRAY);
				
			} else if (ClassUtils.isAssignable(type, ClassUtils.getClass("java.time.ZonedDateTime"))) { 
				returnValue = zonedDateTime;
				
			} else if (ClassUtils.isAssignable(type, ClassUtils.getClass("java.time.OffsetTime"))) { 
				returnValue = invokeMethod(zonedDateTime, "toOffsetDateTime", EMPTY_OBJECT_ARRAY);
				returnValue = invokeMethod(returnValue, "toOffsetTime", EMPTY_OBJECT_ARRAY);
				
			} else if (ClassUtils.isAssignable(type, ClassUtils.getClass("java.time.Instant"))) { 
				returnValue = invokeMethod(zonedDateTime, "toInstant", EMPTY_OBJECT_ARRAY);
				
			} else {
				throw new IllegalArgumentException("Incorrect type for transformer: " + type.getCanonicalName());
			}
			
		} catch (NoSuchMethodException e) {
			throw new IllegalArgumentException("Problem to convert to type: " + type.getCanonicalName(), e);
		} catch (IllegalAccessException e) {
			throw new IllegalArgumentException("Problem to convert to type: " + type.getCanonicalName(), e);
		} catch (InvocationTargetException e) {
			throw new IllegalArgumentException("Problem to convert to type: " + type.getCanonicalName(), e);
		} catch (ClassNotFoundException e) {
			throw new IllegalArgumentException("Problem to convert to type: " + type.getCanonicalName(), e);
		}
		
		return type.cast(returnValue);
	}

    	@SuppressWarnings("unchecked")
	@Override
	public boolean accepts(Object value, Class<?> type) {
        	try {
			return value instanceof Calendar && ClassUtils.getClass("java.time.temporal.Temporal").isAssignableFrom(type);
		} catch (ClassNotFoundException e) {
			return false;
		}
    	}
}
