package br.com.fixturefactory.function;

import java.util.Calendar;

public class CalendarSequence implements Sequence<Calendar> {

	private Calendar baseCalendar;
	private CalendarInterval interval;
	private int multiplier;

	public CalendarSequence(Calendar baseCalendar, CalendarInterval interval) {
		super();
		this.baseCalendar = (Calendar) baseCalendar.clone();
		this.interval = interval;
	}

	@Override
	public Calendar nextValue() {
		Calendar result = (Calendar) this.baseCalendar.clone();
		result.add(this.interval.getCalendarField(), this.interval.getValue() * this.multiplier);
		
		this.multiplier++;
		return result;
	}
}
