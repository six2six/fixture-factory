package br.com.fixturefactory;

import br.com.fixturefactory.function.Function;

public class Property {

	private String name;
	
	private Object value;
	
	private Function function;

	public Property(String name, Function function) {
		this.name = name;
		this.function = function;
	}

	public Property(String name, Object value) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return this.name;
	}

	public Object getValue() {
		return this.value == null ? this.function.generateValue() : this.value;
	}
	
}
