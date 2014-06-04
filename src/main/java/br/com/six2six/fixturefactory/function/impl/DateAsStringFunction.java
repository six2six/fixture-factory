package br.com.six2six.fixturefactory.function.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import br.com.six2six.fixturefactory.function.AtomicFunction;

public class DateAsStringFunction implements AtomicFunction {

	private final AtomicFunction dateFunction;
	private final SimpleDateFormat dateFormat;
	
	public DateAsStringFunction(AtomicFunction dateFunction, String pattern) {
		this.dateFunction = dateFunction;
		dateFormat = new SimpleDateFormat(pattern);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T generateValue() {
		Calendar date = dateFunction.generateValue();
		return (T) dateFormat.format(date.getTime());
	}
	
	public static DateAsStringFunction dateAsString(AtomicFunction dateFunction, String pattern) {
		return new DateAsStringFunction(dateFunction, pattern);
	}

}
