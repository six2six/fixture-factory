package br.com.fixturefactory.function;

import java.io.Serializable;

public class CalendarInterval implements Serializable {
	
	private static final long serialVersionUID = 2223542302515335884L;
	
	private int field;
	private int amount;

	public CalendarInterval(int amount, int calendarField) {
		super();
		this.field = calendarField;
		this.amount = amount;
	}

	public int getField() {
		return field;
	}

	public int getAmount() {
		return amount;
	}
	
}
