package br.com.fixturefactory.model;

import java.io.Serializable;

public class Attribute implements Serializable {
	
	private static final long serialVersionUID = 3296896251851972L;
	
	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
