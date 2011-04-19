package br.com.fixturefactory.base;

import java.io.Serializable;

public class CalendarInterval implements Serializable {
	
	private static final long serialVersionUID = 2223542302515335884L;
	
	private int calendarField;
	private int value;

	public CalendarInterval(int interval, int calendarField) {
		this.calendarField = calendarField;
		this.value = interval;
	}

	public int getCalendarField() {
		return this.calendarField;
	}

	public int getValue() {
		return this.value;
	}
}
