package br.com.fixturefactory.function;

import java.util.ArrayList;
import java.util.List;

public class EnumFixtureFunction implements AtomicFunction {
	public Class<? extends Enum<?>> clazz;
	public int quantity;
	
	public EnumFixtureFunction(Class<? extends Enum<?>> clazz, int quantity) {
		this.clazz = clazz;
		this.quantity = quantity;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T generateValue() {
		List<T> results = new ArrayList<T>();
		AtomicFunction function = new RandomFunction(clazz);
		for(int i = 0; i < quantity; i++) {
			results.add((T) function.generateValue());
		}
		
		return (T) results;
	}

}
