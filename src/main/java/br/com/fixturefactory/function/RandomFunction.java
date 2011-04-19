package br.com.fixturefactory.function;

import java.util.Random;

import br.com.fixturefactory.base.Range;

public class RandomFunction implements Function {

	private Class<?> type;
	
	private Object[] dataset;
	
	private Function[] functions;
	
	private Range range;
	
	public RandomFunction(Class<?> type) {
		this.type = type;
	}

	public RandomFunction(Object[] dataset) {
		this.dataset = dataset;
	}
	
	public RandomFunction(Function[] functions) {
		this.functions = functions;
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
		
		if (this.dataset != null && this.dataset.length > 0) {
			result = this.dataset[random.nextInt(this.dataset.length)];

		} else if (this.functions != null && this.functions.length > 0) { 
			result = this.functions[random.nextInt(this.functions.length)].generateValue();
				
		} else if (this.type.isEnum()) {
			result = this.type.getEnumConstants()[random.nextInt(this.type.getEnumConstants().length)];
			
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
