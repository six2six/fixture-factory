package br.com.six2six.fixturefactory.function;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.ObjectFactory;
import br.com.six2six.fixturefactory.processor.Processor;

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
	public <T> T generateValue() {
		return generate(Fixture.from(clazz));
	}
	
	@Override
	public <T> T generateValue(Processor processor) {
		return generate(Fixture.from(clazz).uses(processor));
	}
	
	@Override
	public <T> T generateValue(Object owner) {
		return generate(new ObjectFactory(Fixture.of(clazz), owner));
	}

	@Override
	public <T> T generateValue(Object owner, Processor processor) {
	    return generate(Fixture.from(clazz).uses(processor));
	}

	@SuppressWarnings("unchecked")
	private <T> T generate(ObjectFactory objectFactory) {
		return (T) (quantity != null ? objectFactory.gimme(quantity, label) : objectFactory.gimme(label));
	}
}
