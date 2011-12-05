package br.com.fixturefactory;

import br.com.fixturefactory.function.AtomicFunction;
import br.com.fixturefactory.function.Function;
import br.com.fixturefactory.function.RelationFunction;

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
		return this.value == null ? ((AtomicFunction) this.function).generateValue() : this.value;
	}
	
	public Object getValue(Object owner) {
		return ((RelationFunction) this.function).generateValue(owner);
	}
	
	
	public boolean hasRelationFunction() {
		return this.function instanceof RelationFunction;
	}
	
}
