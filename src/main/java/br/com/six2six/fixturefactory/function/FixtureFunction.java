package br.com.six2six.fixturefactory.function;

import org.hibernate.Session;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.ObjectFactory;
import br.com.six2six.fixturefactory.PersistentObjectFactory;

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
	public <T> T generateValue(Session session) {
		return (T) generate(Fixture.from(clazz, session));
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
	
	@SuppressWarnings("unchecked")
	private <T> T generatePersistent(PersistentObjectFactory objectFactory) {
		return (T) (quantity != null ? objectFactory.gimme(quantity, label) : objectFactory.gimme(label));
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T generateValue(Object owner, Session session) {
		return (T) generatePersistent(new PersistentObjectFactory(Fixture.of(clazz), owner, session));
	}
	
}
