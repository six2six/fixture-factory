package br.com.fixturefactory.function;

import java.util.Random;

public class RandomFunction implements Function {

	private Class<?> type;
	
	private Object[] dataset;
	
	private Range range;
	
	public RandomFunction(Class<?> type) {
		this.type = type;
	}

	public RandomFunction(Object[] dataset) {
		this.dataset = dataset;
	}
	
	public RandomFunction(Class<?> type, Object[] dataset) {
		this.type = type;
		this.dataset = dataset;
	}

	public RandomFunction(Class<?> type, Range range) {
		this.type = type;
		this.range = range;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <T> T generateValue() {
		Object result = null;
		Random random = new Random();
		
		if (this.dataset != null) {
			result = this.dataset[random.nextInt(this.dataset.length)];

		} else if (this.type.isAssignableFrom(Integer.class)) {
			result = this.range == null ? random.nextInt() : (this.range.getStart().intValue() + (int)(Math.random() * (this.range.getEnd().intValue() - this.range.getStart().intValue()) + 1));
			
		} else if (this.type.isAssignableFrom(Long.class)) {
			result = this.range == null ? random.nextLong() : (this.range.getStart().longValue() + (long)(Math.random() * (this.range.getEnd().longValue() - this.range.getStart().longValue()) + 1));
			
		} else if (this.type.isAssignableFrom(Float.class)) {
			result = this.range == null ? random.nextFloat() : (this.range.getStart().floatValue() + (float)(Math.random() * (this.range.getEnd().floatValue() - this.range.getStart().floatValue()) + 1));
			
		} else if (this.type.isAssignableFrom(Double.class)) {
			result = this.range == null ? random.nextDouble() : (this.range.getStart().doubleValue() + (double)(Math.random() * (this.range.getEnd().doubleValue() - this.range.getStart().doubleValue()) + 1));
			
		} else if (this.type.isAssignableFrom(Boolean.class)) {
			result = random.nextBoolean();	
		}
		
		return (T) result;
	}
	
}
