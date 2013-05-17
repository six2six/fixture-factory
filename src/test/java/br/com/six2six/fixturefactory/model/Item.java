package br.com.six2six.fixturefactory.model;

import java.io.Serializable;

public class Item implements Serializable {

	private static final long serialVersionUID = -8679257032342835407L;

	private Order order;
	
	private Integer productId;

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}
}
