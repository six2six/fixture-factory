package br.com.fixturefactory;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import br.com.bfgex.Gender;
import br.com.fixturefactory.base.Sequence;
import br.com.fixturefactory.function.NumberSequence;
import br.com.fixturefactory.model.Address;
import br.com.fixturefactory.model.Student;

public class FixtureStudentTest {

	@Before
	public void setUp() {
		Fixture.of(Student.class).addTemplate("valid", new Rule(){{
			add("id", sequence(1L, 1));
			add("firstName", firstName());
			add("lastName", lastName());
			add("gender", random(Gender.class));
			add("idCardNumber", "12345");
			add("addresses", set(has(2).of(Address.class, "valid")));
		}}
		).addTemplate("validFemaleStudent", new Rule(){{
			add("id", sequence(200L, 2));
			add("firstName", firstName(Gender.FEMALE));
			add("lastName", lastName());
			add("gender", Gender.FEMALE);
		}});
		
		Fixture.of(Address.class).addTemplate("valid", new Rule(){{
			add("id", random(Long.class, range(1L, 100L)));
			add("street", random("Paulista Avenue", "Ibirapuera Avenue"));
			add("city", "São Paulo");
			add("state", "${city}");
			add("country", "Brazil");
			add("zipCode", random("06608000", "17720000"));
		}});
		
		final Sequence<Number> numberSequence = new NumberSequence(1L, 1);
		
		Fixture.of(Student.class).addTemplate("sharedSequence", new Rule(){{ 
			add("id", sequence(numberSequence));
		}}
		).addTemplate("otherSharedSequence", new Rule(){{ 
			add("id", sequence(numberSequence));
		}});
	}

	@Test
	public void fixtureAnyStudent() {
		Student student = Fixture.from(Student.class).gimme("valid");
		assertNotNull("Student should not be null", student);
		assertNotNull("Students id should not be null", student.getId());
		assertNotNull("Students idCardNumber should not be null", student.getIdCardNumber());
		assertTrue("Students it should be 1", student.getId() == 1);
	}
	
	@Test
	public void fixtureFemaleStudent() {
		Student student = Fixture.from(Student.class).gimme("validFemaleStudent");
		assertNotNull("Female Student should not be null", student);
		assertNotNull("Students id should not be null", student.getId());
		assertTrue("Students it should be 1", student.getId() == 200);
	}
	
	@Test
	public void fixtureSharedSequence() {
		Student oneStudent = Fixture.from(Student.class).gimme("sharedSequence");
		Student otherStudent = Fixture.from(Student.class).gimme("otherSharedSequence");
		
		assertTrue("Students id should be 1", oneStudent.getId() == 1L);
		assertTrue("otherStudes id should be 2", otherStudent.getId() == 2L);
	}
}
