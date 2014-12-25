package br.com.six2six.fixturefactory.function.impl;

import java.util.Random;

import br.com.six2six.fixturefactory.function.AtomicFunction;

public class UniqueRandomFunction implements AtomicFunction{

	private Object[] dataset;
	
	private int nextValueIndex;
	
	public UniqueRandomFunction(int minValue, int maxValue) {
		this.dataset = this.initIntegerDataset(minValue, maxValue);
		this.shuffleDataset();
		this.nextValueIndex = 0;
	}
	
	public UniqueRandomFunction(Object[] dataset) {
		this.dataset = dataset;
		this.shuffleDataset();
		this.nextValueIndex = 0;
	}
	
	public UniqueRandomFunction(Class<? extends Enum<?>> clazz) {
		this.dataset = clazz.getEnumConstants();
		this.shuffleDataset();
		this.nextValueIndex = 0;
	}
	
	private Object[] initIntegerDataset(int minValue, int maxValue) {
		Integer[] dataset = new Integer[maxValue - minValue + 1];
		int currValue = minValue;
		for(int i = 0; i < dataset.length; i++) {
			dataset[i] = currValue;
			currValue ++;
		}
		
		return dataset;
	}
	
	private void shuffleDataset() {
		Random random = new Random();
		int iterator, shufflePosition;
		Object temp;
		for(iterator = dataset.length - 1; iterator > 0; iterator--) {
			shufflePosition = random.nextInt(iterator);
			temp = dataset[iterator];
			dataset[iterator] = dataset[shufflePosition];
			dataset[shufflePosition] = temp;
		}
	}
	
	private Object getNext() {
		try {
			Object nextValue = this.dataset[this.nextValueIndex];
			this.nextValueIndex ++;
			return nextValue;
		} catch(ArrayIndexOutOfBoundsException e) {
			return null;
		}
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <T> T generateValue() {
		return (T) this.getNext();
	}
}