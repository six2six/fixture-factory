package br.com.six2six.fixturefactory;

import br.com.six2six.fixturefactory.base.Range;
import br.com.six2six.fixturefactory.function.IdentityFunction;
import br.com.six2six.fixturefactory.function.RandomFunction;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PropertyTest {

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Test
	public void shoudNotAllowNullName() {
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage(org.junit.matchers.JUnitMatchers.containsString("name"));
		new Property(null, null);
	}

	@Test
	public void shoudNotAllowNullFunction() {
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage(org.junit.matchers.JUnitMatchers.containsString("function"));
		new Property("attr", null);
	}

	@Test
	public void shouldReturnValueFromIdentityFunction() {
		String value = "some name";
		Property property = new Property("attr", new IdentityFunction(value));
		assertEquals(value, property.getValue());
	}

	@Test
	public void shouldReturnValue() {
		String value = "some name";
		Property property = new Property("attr", value);
		assertEquals(value, property.getValue());
	}

	@Test
	public void shouldReturnNullFromIdentityFunction() {
		Property property = new Property("attr", new IdentityFunction(null));
		assertEquals(null, property.getValue());
	}

	@Test
	public void shouldReturnNull() {
		Property property = new Property("attr", (Object) null);
		assertEquals(null, property.getValue());
	}

	@Test
	public void shouldGenerateAValue() {
		Long start = 1L;
		Long end = 10L;
		Property property = new Property("someNumber", new RandomFunction(Long.class, new Range(start, end)));
		Long value = (Long) property.getValue();
		assertTrue(start <= value && value <= end);
	}
}
