package br.com.fixturefactory.function;

import br.com.fixturefactory.Property;

public class ConcatFunction implements Function {

	private Property property;
	
	private String suffix;
	
	public ConcatFunction(Property property, String suffix) {
		this.property = property;
		this.suffix = suffix;
	}

	public ConcatFunction(String suffix) {
		this.suffix = suffix;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T generateValue() {
		return (T) (property.getValue().toString() + suffix);
	}

	public void setProperty(Property property) {
		this.property = property;
	}

}
