package br.com.six2six.fixturefactory.function;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.regex.Pattern;

import org.junit.Test;

import br.com.six2six.fixturefactory.function.impl.CnpjFunction;

public class CnpjFunctionTest {

	@Test
	public void randomCnpj() {
		String value = new CnpjFunction().generateValue();
		assertNotNull("Generated CNPJ can not be null", value);
		assertTrue("Invalid match for CNPJ", Pattern.matches("\\d{14}", value));
	}

	@Test
	public void randomFormattedCpf() {
		String value = new CnpjFunction(true).generateValue();
		assertNotNull("Generated formatted CNPJ can not be null", value);
		assertTrue("Invalid match for CNPJ", Pattern.matches("\\d{2}.\\d{3}.\\d{3}/\\d{4}-\\d{2}", value));
	}

	
}
