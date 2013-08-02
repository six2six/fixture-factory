package br.com.six2six.fixturefactory;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

import br.com.fixturefactory.loader.FixtureFactoryLoader;
import br.com.six2six.fixturefactory.model.Student;

public class FixtureStudentTest {

	@BeforeClass
	public static void setUp() {
		FixtureFactoryLoader.loadTemplates("br.com.six2six.template");
	}

	@Test
	public void fixtureAnyStudent() {
		Student student = Fixture.from(Student.class).gimme("valid");
		assertNotNull("Student should not be null", student);
		assertNotNull("Students id should not be null", student.getId());
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
	
	 @Test
	public void fixtureDefaultNumberSequence() {
		 Student firstStudent = Fixture.from(Student.class).gimme("defaultNumberSequence");
		 Student secoundStudent = Fixture.from(Student.class).gimme("defaultNumberSequence");
		 
		 assertTrue("First Students id should be 1", firstStudent.getId() == 1L);
		 assertTrue("Secound Students id should be 2", secoundStudent.getId() == 2L);
	}
	 
	 @Test
	 public void fixtureMaleStudent() {
		 Student student = Fixture.from(Student.class).gimme("validMaleStudent");
		 assertNotNull("Male Student should not be null", student);
		 assertNotNull("Students id should not be null", student.getId());
	 }
}
