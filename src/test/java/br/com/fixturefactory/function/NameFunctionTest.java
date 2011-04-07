package br.com.fixturefactory.function;

import junit.framework.Assert;

import org.junit.Test;

import br.com.bfgex.Gender;
import br.com.fixturefactory.function.NameFunction.Type;

public class NameFunctionTest {

	@Test
	public void generateAnyName() {
		String value = new NameFunction().generateValue();
		Assert.assertNotNull("Generated value can not be null", value);
	}
	
	@Test
	public void generateMaleName() {
		String value = new NameFunction(Gender.MALE).generateValue();
		Assert.assertNotNull("Generated value can not be null", value);
	}

	@Test
	public void generateAnyFirstName() {
		String value = new NameFunction(Type.FIRST).generateValue();
		Assert.assertNotNull("Generated value can not be null", value);
	}

	@Test
	public void generateFemaleFirstName() {
		String value = new NameFunction(Type.FIRST, Gender.FEMALE).generateValue();
		Assert.assertNotNull("Generated value can not be null", value);
	}

	@Test
	public void generateLastName() {
		String value = new NameFunction(Type.LAST).generateValue();
		Assert.assertNotNull("Generated value can not be null", value);
	}
	
}
