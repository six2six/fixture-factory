package br.com.fixturefactory.function;

import java.util.Arrays;

import junit.framework.Assert;

import org.junit.Test;

public class RandomFunctionTest {

	@Test
	public void randomLongTest() {
		Object value = new RandomFunction(Long.class).generateValue();
		Assert.assertNotNull("Generated value can not be null", value);
		Assert.assertTrue("Generated value is not a Long", value instanceof Long);
	}

	@Test
	public void randomIntegerTest() {
		Object value = new RandomFunction(Integer.class).generateValue();
		Assert.assertNotNull("Generated value can not be null", value);
		Assert.assertTrue("Generated value is not a Integer", value instanceof Integer);
	}

	@Test
	public void randomFloatTest() {
		Object value = new RandomFunction(Float.class).generateValue();
		Assert.assertNotNull("Generated value can not be null", value);
		Assert.assertTrue("Generated value is not a Float", value instanceof Float);
	}

	@Test
	public void randomDoubleTest() {
		Object value = new RandomFunction(Double.class).generateValue();
		Assert.assertNotNull("Generated value can not be null", value);
		Assert.assertTrue("Generated value is not a Double", value instanceof Double);
	}

	@Test
	public void randomBooleanTest() {
		Object value = new RandomFunction(Boolean.class).generateValue();
		Assert.assertNotNull("Generated value can not be null", value);
		Assert.assertTrue("Generated value is not a Boolean", value instanceof Boolean);
	}
	
	@Test
	public void randomDatasetTest() {
		String[] names = {"Anderson", "Arthur", "Douglas"};
		Object value = new RandomFunction(names).generateValue();
		Assert.assertNotNull("Generated value can not be null", value);
		Assert.assertTrue("Generated value does not exist in the dataset", Arrays.asList(value).contains(value));
	}

	@Test
	public void randomLongRangeTest() {
		Long start = 85L, end = 95L;
		Object value = new RandomFunction(Long.class, new Range(start, end)).generateValue();
		Assert.assertNotNull("Generated value can not be null", value);
		Assert.assertTrue("Generated value does not exist in the range", (start <= (Long) value && (Long) value <= end));
	}

	@Test(expected = IllegalArgumentException.class)
	public void randomLongRangeIncorrectTest() {
		Long start = 80L, end = 80L;
		new RandomFunction(Long.class, new Range(start, end)).generateValue();
	}

	@Test(expected = IllegalArgumentException.class)
	public void randomDoubleRangeIncorrectTest() {
		Double start = 80.0, end = 80.0;
		new RandomFunction(Long.class, new Range(start, end)).generateValue();
	}
	
}
