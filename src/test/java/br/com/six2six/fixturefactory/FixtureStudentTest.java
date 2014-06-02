package br.com.six2six.fixturefactory;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import br.com.six2six.bfgex.Gender;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
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
		assertTrue("Tests taken should be greather than 0", student.getTestsTaken() > 0);
		assertTrue("Best score should be greather than 0", student.getTestsTaken() > 0);
	}
	
	@Test
	public void fixtureFemaleStudent() {
		Student student = Fixture.from(Student.class).gimme("validFemaleStudent");
		assertNotNull("Female Student should not be null", student);
		assertNotNull("Students id should not be null", student.getId());
	}
	
	@Test
	public void fixtureSharedSequence() {
		Student oneStudent = Fixture.from(Student.class).gimme("sharedSequence");
		Student otherStudent = Fixture.from(Student.class).gimme("otherSharedSequence");
		assertTrue(oneStudent.getId() < otherStudent.getId());
	}
	
	 @Test
	public void fixtureDefaultNumberSequence() {
		 Student firstStudent = Fixture.from(Student.class).gimme("defaultNumberSequence");
		 Student secoundStudent = Fixture.from(Student.class).gimme("defaultNumberSequence");
		 assertTrue(firstStudent.getId() < secoundStudent.getId());
	}
	 
	 @Test
	 public void fixtureMaleStudent() {
		 Student student = Fixture.from(Student.class).gimme("validMaleStudent");
		 assertNotNull("Male Student should not be null", student);
		 assertNotNull("Students id should not be null", student.getId());
	 }
	 
	 @Test
	 public void shouldGimmeOneMaleAndOneFemaleStudent() {
		 List<Student> students = Fixture.from(Student.class).gimme(2, "validFemaleStudent", "validMaleStudent");
		 assertEquals(Gender.FEMALE, students.get(0).getGender());
		 assertEquals(Gender.MALE, students.get(1).getGender());
	 }
}
