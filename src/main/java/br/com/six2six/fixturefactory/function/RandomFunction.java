package br.com.six2six.fixturefactory.function;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Random;

import br.com.six2six.fixturefactory.base.Range;

public class RandomFunction implements AtomicFunction {

	private Class<?> type;
	
	private Object[] dataset;
	
	private AtomicFunction[] functions;
	
	private Range range;
	
	private MathContext mathContext = MathContext.DECIMAL32;
	
	public RandomFunction(Class<?> type) {
		this.type = type;
	}

	public RandomFunction(Object[] dataset) {
		this.dataset = dataset;
	}
	
	public RandomFunction(AtomicFunction[] functions) {
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
	
	public RandomFunction(Class<? extends BigDecimal> type, MathContext mathContext) {
	    this.type = type;
	    this.mathContext = mathContext;
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
			
		} else if (this.type.isAssignableFrom(Byte.class)) {
            result = (byte) (this.range == null ? random.nextInt(Byte.MAX_VALUE + 1) : (this.range.getStart().byteValue() + (Math.random() * (this.range.getEnd().byteValue() - this.range.getStart().byteValue()) + 1)));
            
        } else if (this.type.isAssignableFrom(Short.class)) {
            result = (short) (this.range == null ? (short) random.nextInt(Short.MAX_VALUE + 1) : (this.range.getStart().shortValue() + (Math.random() * (this.range.getEnd().shortValue() - this.range.getStart().shortValue()) + 1)));
            
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
			
		} else if (this.type.isAssignableFrom(BigDecimal.class)) {
		    if (this.range == null) {
		        result = new BigDecimal(Double.valueOf(Math.random()), this.mathContext);
		    } else {
		        BigDecimal start = ((BigDecimal) range.getStart());
		        BigDecimal end = ((BigDecimal) range.getEnd());
		        int scale = start.scale() > end.scale() ? start.scale() : end.scale();
		        
		        result = start.add(new BigDecimal(Math.random()).multiply(end.subtract(start))).setScale(scale, RoundingMode.HALF_EVEN);
		    }
		} else if (this.type.isAssignableFrom(BigInteger.class)) {
		    if (this.range == null) {
		        result = new BigInteger(64, random);
		    } else {
		        BigInteger start = ((BigInteger) range.getStart());
		        BigInteger end = ((BigInteger) range.getEnd());
		        
		        result = start.add(new BigDecimal(Math.random()).toBigInteger().multiply(end.subtract(start)));
		    }
		}
		
		return (T) result;
	}
}
