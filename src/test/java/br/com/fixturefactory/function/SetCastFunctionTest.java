package br.com.fixturefactory.function;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import br.com.bfgex.Gender;
import br.com.fixturefactory.Fixture;
import br.com.fixturefactory.Rule;
import br.com.fixturefactory.model.Student;

public class SetCastFunctionTest {

	@Before
	public void setUp() {
		Fixture.of(Student.class).addTemplate("valid", new Rule(){{
			add("id", sequence(1L, 1));
			add("firstName", firstName());
			add("lastName", lastName());
			add("gender", random(Gender.class));
			add("idCardNumber", "12345");
		}});
	}
	
	@Test
	public void shouldCastRelationFunctionToSet() {
		RelationFunction relationFunction = new FixtureFunction(Student.class, "valid", 2);
		
		Set<String> set = new SetCastFunction(relationFunction).generateValue(null);
		assertNotNull(set);
		assertEquals(2, set.size());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldNotCastRelationFunctionWithSingleValueToSet() {
		RelationFunction relationFunction = new FixtureFunction(Student.class, "valid");
		
		new SetCastFunction(relationFunction).generateValue(null);
	}
}
