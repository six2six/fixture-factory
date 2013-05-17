package br.com.six2six.fixturefactory.util;

import br.com.six2six.fixturefactory.function.Function;

public interface Chainable {

	Function of(Class<?> clazz, String label);
	
	Function of(Class<?> clazz, String label, String targetAttribute);

	Function of(Class<? extends Enum<?>> clazz);
	
}
