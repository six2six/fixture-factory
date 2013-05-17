package br.com.six2six.fixturefactory.model;

import java.io.Serializable;

import javax.xml.datatype.XMLGregorianCalendar;

public class XMLTransaction implements Serializable {

	private static final long serialVersionUID = -4627755454446905456L;

	private String origin;
	
	private XMLGregorianCalendar date;
	
	private XMLGregorianCalendar hour;

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public XMLGregorianCalendar getDate() {
		return date;
	}

	public void setDate(XMLGregorianCalendar date) {
		this.date = date;
	}

	public XMLGregorianCalendar getHour() {
		return hour;
	}

	public void setHour(XMLGregorianCalendar hour) {
		this.hour = hour;
	}
	
}
