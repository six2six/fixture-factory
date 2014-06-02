package br.com.six2six.fixturefactory.util;

import br.com.six2six.fixturefactory.function.AssociationFunction;
import br.com.six2six.fixturefactory.function.Function;

public interface Chainable {

	AssociationFunction of(Class<?> clazz, String label);
	
	AssociationFunction of(Class<?> clazz, String... labels);

	Function of(Class<? extends Enum<?>> clazz);
	
}
