package br.com.fixturefactory.util;

import br.com.fixturefactory.function.Function;

public interface Chainable {

	Function of(Class<?> clazz, String label);
	
	Function of(Class<?> clazz, String label, String targetAttribute);

	Function of(Class<? extends Enum<?>> clazz);
	
}
