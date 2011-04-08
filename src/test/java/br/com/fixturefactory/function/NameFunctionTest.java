package br.com.fixturefactory.function;

import junit.framework.Assert;

import org.junit.Test;

import br.com.bfgex.Gender;
import br.com.fixturefactory.function.NameFunction.NameType;

public class NameFunctionTest {

	@Test
	public void generateAnyName() {
		String value = new NameFunction().generateValue();
		Assert.assertNotNull("Generated full name can not be null", value);
	}
	
	@Test
	public void generateMaleName() {
		String value = new NameFunction(Gender.MALE).generateValue();
		Assert.assertNotNull("Generated male name can not be null", value);
	}

	@Test
	public void generateAnyFirstName() {
		String value = new NameFunction(NameType.FIRST).generateValue();
		Assert.assertNotNull("Generated first name can not be null", value);
	}

	@Test
	public void generateFemaleFirstName() {
		String value = new NameFunction(NameType.FIRST, Gender.FEMALE).generateValue();
		Assert.assertNotNull("Generated female first name can not be null", value);
	}

	@Test
	public void generateLastName() {
		String value = new NameFunction(NameType.LAST).generateValue();
		Assert.assertNotNull("Generated last name can not be null", value);
	}
	
}
