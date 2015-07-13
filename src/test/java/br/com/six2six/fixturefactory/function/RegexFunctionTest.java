package br.com.six2six.fixturefactory.function;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import br.com.six2six.fixturefactory.function.impl.RegexFunction;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;

@RunWith(Parameterized.class)
public class RegexFunctionTest {

	@Parameters(name = "{index}: regex {0}={1}")
	public static Iterable<String[]> data() {
		return Arrays.asList(new String[][]{
				{"String", "\\w{8}"},
				{"Number", "\\d{3,8}"},
				{"Phone number", "(\\d{2})-(\\d{4})-(\\d{4})"},
				{"MD5", "[0-9a-f]{32}"}
		});
	}

	private String regexName;
	private String pattern;

	public RegexFunctionTest(String regexName, String pattern) {
		this.regexName = regexName;
		this.pattern = pattern;
	}

	@Test
	public void testAgainstRegex() {
		String value = new RegexFunction(pattern).generateValue();
		assertNotNull(String.format("Generated %s can not be null", regexName), regexName);
		assertTrue(String.format("Generated %s (%s) no match with %s", regexName, value, pattern), value.matches(pattern));
	}
	
}
