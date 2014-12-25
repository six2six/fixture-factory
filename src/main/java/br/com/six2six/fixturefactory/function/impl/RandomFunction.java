package br.com.six2six.fixturefactory.function.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Random;

import br.com.six2six.fixturefactory.base.Range;
import br.com.six2six.fixturefactory.function.AtomicFunction;

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
            result = this.range == null ? (byte) random.nextInt(Byte.MAX_VALUE + 1) : (byte) getRandomLong(this.range);

        } else if (this.type.isAssignableFrom(Short.class)) {
            result = this.range == null ? (short) random.nextInt(Short.MAX_VALUE + 1) : (short) getRandomLong(this.range);

        } else if (this.type.isAssignableFrom(Integer.class)) {
            result = this.range == null ? random.nextInt() : (int) getRandomLong(this.range);

        } else if (this.type.isAssignableFrom(Long.class)) {
            result = this.range == null ? random.nextLong() : getRandomLong(this.range);

        } else if (this.type.isAssignableFrom(Float.class)) {
            result = this.range == null ? random.nextFloat() : (float) getRandomDouble(this.range);

        } else if (this.type.isAssignableFrom(Double.class)) {
            result = this.range == null ? random.nextDouble() : getRandomDouble(this.range);

        } else if (this.type.isAssignableFrom(Boolean.class)) {
            result = random.nextBoolean();

        } else if (this.type.isAssignableFrom(BigDecimal.class)) {
            if (this.range == null) {
                result = new BigDecimal(random.nextDouble(), this.mathContext);

            } else {

                result = getRandomBigDecimalByRange();

            }

        } else if (this.type.isAssignableFrom(BigInteger.class)) {
            if (this.range == null) {
                result = new BigInteger(64, random);

            } else {

                result = getRandomBigDecimalByRange().toBigInteger();

            }
        }

        return (T) result;
    }

    private long getRandomLong(Range range) {
        return Math.round(getRandomDouble(range));
    }
    
    private double getRandomDouble(Range range) {
        return range.getStart().doubleValue() + (Math.random() * (range.getEnd().doubleValue() - range.getStart().doubleValue()));
    }
    
    private BigDecimal getRandomBigDecimal(BigDecimal start, BigDecimal end) {
        int scale = start.scale() > end.scale() ? start.scale() : end.scale();
        return start.add(new BigDecimal(Math.random()).multiply(end.subtract(start))).setScale(scale, RoundingMode.HALF_EVEN);
    }

    private BigDecimal getRandomBigDecimalByRange() {
        final BigDecimal start = new BigDecimal(this.range.getStart().toString());
        final BigDecimal end = new BigDecimal(this.range.getEnd().toString());
        return getRandomBigDecimal(start, end);
    }
}
