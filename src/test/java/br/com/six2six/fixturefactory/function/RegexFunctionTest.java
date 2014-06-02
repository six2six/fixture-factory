package br.com.six2six.fixturefactory.function;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

import org.junit.Test;

import br.com.six2six.fixturefactory.function.impl.RegexFunction;

public class RegexFunctionTest {

	@Test
	public void regexString() {
		String pattern = "\\w{8}";
		String value = new RegexFunction(pattern).generateValue();
		assertNotNull("Generated string can not be null", value);
		assertTrue(String.format("Generated string no match with %s", pattern), value.matches(pattern));
	}
	
	@Test
	public void regexNumber() {
		String pattern = "\\d{3,8}";
		String value = new RegexFunction(pattern).generateValue();
		assertNotNull("Generated number can not be null", value);
		assertTrue(String.format("Generated number no match with %s", pattern), value.matches(pattern));
	}

	@Test
	public void regexPhoneNumber() {
		String pattern = "(\\d{2})-(\\d{4})-(\\d{4})";
		String value = new RegexFunction(pattern).generateValue();
		assertNotNull("Generated phone number can not be null", value);
		assertTrue(String.format("Generated phone number no match with %s", pattern), value.matches(pattern));
	}
	
}
