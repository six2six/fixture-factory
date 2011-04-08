package br.com.fixturefactory.function;

import junit.framework.Assert;

import org.junit.Test;

public class RegexFunctionTest {

	@Test
	public void regexString() {
		String pattern = "\\w{8}";
		String value = new RegexFunction(pattern).generateValue();
		Assert.assertNotNull("Generated string can not be null", value);
		Assert.assertTrue(String.format("Generated string no match with %s", pattern), value.matches(pattern));
	}
	
	@Test
	public void regexNumber() {
		String pattern = "\\d{3,8}";
		String value = new RegexFunction(pattern).generateValue();
		Assert.assertNotNull("Generated number can not be null", value);
		Assert.assertTrue(String.format("Generated number no match with %s", pattern), value.matches(pattern));
	}

	@Test
	public void regexPhoneNumber() {
		String pattern = "(\\d{2})-(\\d{4})-(\\d{4})";
		String value = new RegexFunction(pattern).generateValue();
		Assert.assertNotNull("Generated phone number can not be null", value);
		Assert.assertTrue(String.format("Generated phone number no match with %s", pattern), value.matches(pattern));
	}
	
	
}
