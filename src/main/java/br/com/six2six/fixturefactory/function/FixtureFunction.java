package br.com.six2six.fixturefactory.function;

import java.util.Arrays;
import java.util.List;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.ObjectFactory;
import br.com.six2six.fixturefactory.processor.Processor;

public class FixtureFunction implements AtomicFunction, RelationFunction {

    private Class<?> clazz;

	private List<String> labels;
	
	private Integer quantity;

    private boolean unique;

	public FixtureFunction(Class<?> clazz, String label) {
		this.clazz = clazz;
		this.labels = Arrays.asList(label);
	}

	public FixtureFunction(Class<?> clazz, String label, Integer quantity) {
		this(clazz, Arrays.asList(label), quantity);
	}
	
	public FixtureFunction(Class<?> clazz, List<String> labels, Integer quantity) {
        this(clazz, labels, quantity, false);
	}

    public FixtureFunction(Class<?> clazz, List<String> labels, Integer quantity, boolean unique) {
        this.clazz = clazz;
        this.labels = labels;
        this.quantity = quantity;
        this.unique = unique;
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
		return generate(new ObjectFactory(Fixture.of(clazz), owner, unique));
	}

	@Override
	public <T> T generateValue(Object owner, Processor processor) {
	    return generate(Fixture.from(clazz).uses(processor));
	}

	@SuppressWarnings("unchecked")
	private <T> T generate(ObjectFactory objectFactory) {
		if(quantity != null) {
			return gimmeWithQuantity(objectFactory);
		} else {
			return (T) objectFactory.gimme(getLabel());
		}
	}

	@SuppressWarnings("unchecked")
	private <T> T gimmeWithQuantity(ObjectFactory objectFactory) {
		if(labels.size() == 1) {
			return (T) objectFactory.gimme(quantity, getLabel());
		} else {
			return (T) objectFactory.gimme(quantity, labels);
		}
	}
	
	private String getLabel() {
		return labels.get(0);
	}
}
