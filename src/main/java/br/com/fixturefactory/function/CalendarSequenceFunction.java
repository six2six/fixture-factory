package br.com.fixturefactory.function;

import java.util.Calendar;

public class CalendarSequenceFunction implements Function {

	private Calendar baseCalendar;
	private CalendarInterval interval;

	private int multiplier;
	
	public CalendarSequenceFunction(Calendar calendar, CalendarInterval interval) {
		super();
		this.baseCalendar = (Calendar) calendar.clone();
		this.interval = interval;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T generateValue() {
		Calendar result = (Calendar) this.baseCalendar.clone();
		result.add(this.interval.getField(), this.interval.getAmount() * this.multiplier);
		
		this.multiplier++;
		return (T) result;
	}
}
