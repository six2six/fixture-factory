package br.com.fixturefactory.model;

import java.io.Serializable;

public class RouteId implements Serializable {

	private static final long serialVersionUID = 4124693687758213917L;
	
	private Long value;
	
	public RouteId(Long value) {
		this.value = value;
	}

	public Long getValue() {
		return value;
	}
}
