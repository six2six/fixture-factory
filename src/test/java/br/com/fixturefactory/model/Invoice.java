package br.com.fixturefactory.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;

public class Invoice implements Serializable {

	private static final long serialVersionUID = -3858669033991459168L;

	private String id;
	private BigDecimal ammount;
	private Calendar dueDate;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public BigDecimal getAmmount() {
		return ammount;
	}
	public void setAmmount(BigDecimal ammount) {
		this.ammount = ammount;
	}
	public Calendar getDueDate() {
		return dueDate;
	}
	public void setDueDate(Calendar dueDate) {
		this.dueDate = dueDate;
	}
	
}
