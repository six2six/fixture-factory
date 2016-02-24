package br.com.six2six.fixturefactory.model;

import java.io.Serializable;
import java.util.List;

public class Order implements Serializable {

	private static final long serialVersionUID = -3858669033991459168L;

	private Long id;
	
	private java.time.LocalDateTime registerDate;
	
	private java.time.LocalDate sendDate;
	
	private List<Item> items;

	private Payment payment;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	public java.time.LocalDateTime getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(java.time.LocalDateTime registerDate) {
		this.registerDate = registerDate;
	}

	public java.time.LocalDate getSendDate() {
		return sendDate;
	}

	public void setSendDate(java.time.LocalDate sendDate) {
		this.sendDate = sendDate;
	}
	
}