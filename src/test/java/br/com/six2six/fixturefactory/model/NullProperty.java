package br.com.six2six.fixturefactory.model;

import java.io.Serializable;

public class NullProperty implements Serializable {

	private static final long serialVersionUID = -4298676883728406965L;

	private String name;
	
	private String otherName;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOtherName() {
		return otherName;
	}

	public void setOtherName(String otherName) {
		this.otherName = otherName;
	}
	
	
	
}
