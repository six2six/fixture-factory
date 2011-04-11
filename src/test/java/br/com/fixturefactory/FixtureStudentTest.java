package br.com.fixturefactory;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import br.com.bfgex.Gender;
import br.com.fixturefactory.function.NumberSequence;
import br.com.fixturefactory.function.Sequence;
import br.com.fixturefactory.model.Student;

public class FixtureStudentTest {

	@Before
	public void setUp() {
		Fixture.of(Student.class).addTemplate("valid", new Rule(){{
			add("id", sequence(startWith(1L, incrementBy(1))));
			add("firstName", firstName());
			add("lastName", lastName());
			add("gender", random(Gender.class));

		}}).addTemplate("validFemaleStudent", new Rule(){{
			add("id", sequence(startWith(200L, incrementBy(2))));
			add("firstName", firstName(Gender.FEMALE));
			add("lastName", lastName());
			add("gender", Gender.FEMALE);

		}});
		
		final Sequence<Number> numberSequence = new NumberSequence(1L, 1);
		
		Fixture.of(Student.class).addTemplate("sharedSequence", new Rule() {{ 
			add("id", sequence(numberSequence));

		}}).addTemplate("otherSharedSequence", new Rule() {{ 
			add("id", sequence(numberSequence));

		}});
	}

	@Test
	public void fixtureAnyStudent() {
		Student student = Fixture.of(Student.class).gimme("valid");
		Assert.assertNotNull("Student should not be null", student);
		Assert.assertNotNull("Students id should not be null", student.getId());
		Assert.assertTrue("Students it should be 1", student.getId() == 1);
	}
	
	@Test
	public void fixtureFemaleStudent() {
		Student student = Fixture.of(Student.class).gimme("validFemaleStudent");
		Assert.assertNotNull("Female Student should not be null", student);
		Assert.assertNotNull("Students id should not be null", student.getId());
		Assert.assertTrue("Students it should be 1", student.getId() == 200);
	}
	
	@Test
	public void fixtureSharedSequence() {
		Student oneStudent = Fixture.of(Student.class).gimme("sharedSequence");
		Student otherStudent = Fixture.of(Student.class).gimme("otherSharedSequence");
		
		Assert.assertTrue("Students id should be 1", oneStudent.getId() == 1L);
		Assert.assertTrue("otherStudes id should be 2", otherStudent.getId() == 2L);
	}
}
