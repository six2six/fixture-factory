package br.com.six2six.fixturefactory.model;

import java.io.Serializable;

public class Payment implements Serializable {

	private static final long serialVersionUID = 8822301130570871333L;

	private Long id;

	private Order order;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}
	
}
