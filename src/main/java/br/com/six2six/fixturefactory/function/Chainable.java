package br.com.six2six.fixturefactory.function;

public interface Chainable {
    AssociationFunction unique();

    AssociationFunction unique(int retryTimes);

	AssociationFunction of(Class<?> clazz, String label);
	
	AssociationFunction of(Class<?> clazz, String... labels);

	Function of(Class<? extends Enum<?>> clazz);
}
