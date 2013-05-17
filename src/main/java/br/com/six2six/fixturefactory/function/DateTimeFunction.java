package br.com.six2six.fixturefactory.function;

import java.util.Calendar;

import br.com.six2six.fixturefactory.base.Range;

public class DateTimeFunction implements AtomicFunction {

	private RandomFunction random;
	
	public DateTimeFunction(Calendar referenceDate, DateType type) {
		long start, end; 
		
		if (type == DateType.BEFORE) {
			end = referenceDate.getTimeInMillis();
			Calendar startDate = (Calendar) referenceDate.clone();
			startDate.add(Calendar.YEAR, -28);
			start = startDate.getTimeInMillis();
		} else {
			start = referenceDate.getTimeInMillis();
			Calendar endDate = (Calendar) referenceDate.clone();
			endDate.add(Calendar.YEAR, 28);
			end = endDate.getTimeInMillis();
		}

		this.random = new RandomFunction(Long.class, new Range(start, end));
	}

	public DateTimeFunction(Calendar startDate, Calendar endDate) {
		this.random = new RandomFunction(Long.class, new Range(startDate.getTimeInMillis(), endDate.getTimeInMillis()));
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T generateValue() {
		Calendar result = Calendar.getInstance();
		result.setTimeInMillis((Long) this.random.generateValue());
		return (T) result;
	}
	
	public enum DateType {
		BEFORE, AFTER
	}

}
