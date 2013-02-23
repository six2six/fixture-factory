package br.com.fixturefactory.model;

import java.io.Serializable;

public class City implements Serializable {
	
	private static final long serialVersionUID = -4759361048419836513L;
	
	private String name;

	public City(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
}
