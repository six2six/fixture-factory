package br.com.six2six.fixturefactory.function.impl;

import java.text.SimpleDateFormat;

import br.com.six2six.fixturefactory.function.AtomicFunction;
import br.com.six2six.fixturefactory.function.DateFunction;

public class DateAsStringFunction implements AtomicFunction {

	private final DateFunction dateFunction;
	private final SimpleDateFormat dateFormat;
	
	public DateAsStringFunction(DateFunction dateFunction, String pattern) {
		this.dateFunction = dateFunction;
		dateFormat = new SimpleDateFormat(pattern);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T generateValue() {
		return (T) dateFormat.format(dateFunction.generateValue());
	}

}
