package br.com.fixturefactory.function;

import java.util.Calendar;

public class Interval {

	private int value;

	public Interval(int value) {
		this.value = value;
	}
	
	public CalendarInterval month() {
		return new CalendarInterval(this.value, Calendar.MONTH);
	}
	
	public CalendarInterval day() {
		return new CalendarInterval(this.value, Calendar.DAY_OF_MONTH);
	}
	
	public CalendarInterval year() {
		return new CalendarInterval(this.value, Calendar.YEAR);
	}
}
