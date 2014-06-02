package br.com.six2six.fixturefactory.function;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

import java.util.regex.Pattern;

import org.junit.Test;

import br.com.six2six.fixturefactory.function.impl.CpfFunction;

public class CpfFunctionTest {

	@Test
	public void randomCpf() {
		String value = new CpfFunction().generateValue();
		assertNotNull("Generated CPF can not be null", value);
		assertTrue("Invalid match for CPF", Pattern.matches("\\d{11}", value));
	}

	@Test
	public void randomFormattedCpf() {
		String value = new CpfFunction(true).generateValue();
		assertNotNull("Generated formatted CPF can not be null", value);
		assertTrue("Invalid match for CPF", Pattern.matches("\\d{3}.\\d{3}.\\d{3}-\\d{2}", value));
	}

	
}
