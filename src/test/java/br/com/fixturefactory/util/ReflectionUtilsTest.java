package br.com.fixturefactory.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ReflectionUtilsTest {
	
	@Test
	public void shouldInstantiateWithConstructorParams() {
		Integer numberOne = ReflectionUtils.newInstance(Integer.class, "1");
		assertEquals(new Integer(1), numberOne);
	}
}
