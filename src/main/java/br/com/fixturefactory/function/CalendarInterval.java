package br.com.fixturefactory.function;

import java.io.Serializable;

public class CalendarInterval implements Serializable {
	
	private static final long serialVersionUID = 2223542302515335884L;
	
	private int calendarField;
	private int value;

	public CalendarInterval(int interval, int calendarField) {
		super();
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
