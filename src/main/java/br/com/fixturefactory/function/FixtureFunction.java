package br.com.fixturefactory.function;

import br.com.fixturefactory.Fixture;
import br.com.fixturefactory.TemplateHolder;

public class FixtureFunction implements Function {

	private Class<?> clazz;

	private String label;
	
	private Integer quantity;

	public FixtureFunction(Class<?> clazz, String label) {
		this.clazz = clazz;
		this.label = label;
	}

	public FixtureFunction(Class<?> clazz, String label, Integer quantity) {
		this(clazz, label);
		this.quantity = quantity;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T generateValue() {
		TemplateHolder templateHolder = Fixture.of(clazz);
		return (T) (quantity != null ? templateHolder.gimme(quantity, label) : templateHolder.gimme(label));
	}

}
