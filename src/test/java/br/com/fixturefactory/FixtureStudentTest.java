package br.com.fixturefactory;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import br.com.bfgex.Gender;
import br.com.fixturefactory.model.Student;

public class FixtureStudentTest {

	@Before
	public void setUp() {
		Fixture.of(Student.class).addTemplate("valid", new Rule(){{
			add("firstName", firstName());
			add("lastName", lastName());
			add("gender", random(Gender.class));
		}}
		).addTemplate("validFemaleStudent", new Rule(){{
			add("firstName", firstName(Gender.FEMALE));
			add("lastName", lastName());
			add("gender", Gender.FEMALE);
		}});
	}

	@Test
	public void fixtureAnyStudent() {
		Student student = Fixture.of(Student.class).gimme("valid");
		Assert.assertNotNull("Student should not be null", student);
	}
	
	@Test
	public void fixtureFemaleStudent() {
		Student student = Fixture.of(Student.class).gimme("validFemaleStudent");
		Assert.assertNotNull("Female Student should not be null", student);
	}
	
}
