package br.com.fixturefactory.function;

import br.com.fixturefactory.Fixture;
import br.com.fixturefactory.ObjectFactory;

public class FixtureFunction implements AtomicFunction, RelationFunction {

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
		return (T) generate(Fixture.from(clazz));
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <T> T generateValue(Object owner) {
		return (T) generate(new ObjectFactory(Fixture.of(clazz), owner));
	}
	
	@SuppressWarnings("unchecked")
	private <T> T generate(ObjectFactory objectFactory) {
		return (T) (quantity != null ? objectFactory.gimme(quantity, label) : objectFactory.gimme(label));
	}

	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	
}
