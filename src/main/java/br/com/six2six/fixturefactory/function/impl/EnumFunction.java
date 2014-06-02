package br.com.six2six.fixturefactory.function.impl;

import java.util.ArrayList;
import java.util.List;

import br.com.six2six.fixturefactory.function.AtomicFunction;

public class EnumFunction implements AtomicFunction {
	
	private Class<? extends Enum<?>> clazz;
	private int quantity;
	
	public EnumFunction(Class<? extends Enum<?>> clazz, int quantity) {
		this.clazz = clazz;
		this.quantity = quantity;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T generateValue() {
		List<T> results = new ArrayList<T>();
		AtomicFunction function = new RandomFunction(clazz);
		for (int i = 0; i < quantity; i++) {
			results.add((T) function.generateValue());
		}
		
		return (T) results;
	}
}
