package br.com.six2six.fixturefactory.function;

public interface Chainable {

	AssociationFunction of(Class<?> clazz, String label);
	
	AssociationFunction of(Class<?> clazz, String... labels);

	Function of(Class<? extends Enum<?>> clazz);
}
